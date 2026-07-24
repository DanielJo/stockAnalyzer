package io.github.danieljo.stockanalyzer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Smoke test: confirms the Spring context (incl. the JPA entities/repositories) wires up and
 * the DDL for SymbolEntity/IntervalEntity/StockDataEntity is valid, against an in-memory H2
 * database (src/test/resources/application.yml) instead of the real MySQL instance.
 */
@SpringBootTest
class StockAnalyzerApplicationTests {

	@Test
	void contextLoads() {
	}
}
