package io.github.danieljo.stockanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bootstrap only - the actual CLI logic lives in {@link io.github.danieljo.stockanalyzer.cli.CliRunner}.
 * Keeping this class free of its own dependencies means Spring Boot test slices
 * (e.g. {@code @WebMvcTest}) don't need to satisfy a constructor for the primary source class
 * they otherwise still pick up.
 * <p>
 * Now that {@code spring-boot-starter-web} is on the classpath (for the REST API), Spring Boot
 * would otherwise try to start an embedded web server on every run, including plain one-shot CLI
 * invocations - which don't need a listening port and would fail if one isn't free. {@link #main}
 * disables the web server whenever any argument looks like one of our CLI tokens (i.e. doesn't
 * start with {@code --}, which is how Spring's own property-override args are always written) -
 * so `java -jar stock-analyzer.jar` alone (or with only `--server.port=...`-style args) still
 * starts the REST server, while `java -jar stock-analyzer.jar name_bmw time_30 ...` runs as a
 * plain CLI command with no port involved at all.
 */
@SpringBootApplication
public class StockAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(StockAnalyzerApplication.class);
		if (isCliInvocation(args)) {
			app.setWebApplicationType(WebApplicationType.NONE);
		}
		app.run(args);
	}

	public static boolean isCliInvocation(String[] args) {
		for (String arg : args) {
			if (!arg.startsWith("--")) {
				return true;
			}
		}
		return false;
	}
}
