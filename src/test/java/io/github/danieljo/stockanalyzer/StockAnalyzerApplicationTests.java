package io.github.danieljo.stockanalyzer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Smoke test: confirms the Spring context wires up cleanly (MarketDataService,
 * CommandLineRunner, etc.) - no database involved anymore.
 */
@SpringBootTest
class StockAnalyzerApplicationTests {

	@Test
	void contextLoads() {
	}
}
