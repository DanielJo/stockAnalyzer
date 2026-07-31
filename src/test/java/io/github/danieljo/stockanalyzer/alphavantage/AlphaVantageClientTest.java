package io.github.danieljo.stockanalyzer.alphavantage;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

/**
 * Runs against a WireMock stub instead of the real API - Alpha Vantage's free tier caps out at
 * 25 requests/day, far too tight to spend on a test suite.
 */
class AlphaVantageClientTest {

	private WireMockServer wireMock;
	private AlphaVantageClient client;

	@BeforeEach
	void setUp() {
		wireMock = new WireMockServer(WireMockConfiguration.options().dynamicPort());
		wireMock.start();

		AlphaVantageProperties properties = new AlphaVantageProperties();
		properties.setBaseUrl(wireMock.baseUrl());
		properties.setApiKey("test-key");
		client = new AlphaVantageClient(RestClient.builder(), properties);
	}

	@AfterEach
	void tearDown() {
		wireMock.stop();
	}

	@Test
	void fetchTimeSeries_dailyResponse_parsesBarsInAscendingDateOrder() {
		wireMock.stubFor(get(urlPathEqualTo("/query"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("""
						{
						  "Meta Data": { "2. Symbol": "IBM" },
						  "Time Series (Daily)": {
						    "2024-01-02": {"1. open": "101.0", "2. high": "102.0", "3. low": "100.0", "4. close": "101.5", "5. volume": "1000"},
						    "2024-01-01": {"1. open": "100.0", "2. high": "101.0", "3. low": "99.0", "4. close": "100.5", "5. volume": "900"}
						  }
						}
						""")));

		AlphaVantageTimeSeriesResponse response = client.fetchTimeSeries(AlphaVantageFunction.TIME_SERIES_DAILY, "IBM", "compact");

		assertThat(response.symbol()).isEqualTo("IBM");
		assertThat(response.bars()).hasSize(2);
		assertThat(response.bars().get(0).date()).isEqualTo(LocalDate.of(2024, 1, 1));
		assertThat(response.bars().get(0).close()).isEqualTo(100.5);
		assertThat(response.bars().get(0).adjustedClose()).isNull();
		assertThat(response.bars().get(1).date()).isEqualTo(LocalDate.of(2024, 1, 2));
	}

	@Test
	void fetchTimeSeries_adjustedResponse_parsesAdjustedCloseAndVolume() {
		wireMock.stubFor(get(urlPathEqualTo("/query"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("""
						{
						  "Time Series (Daily)": {
						    "2024-01-01": {"1. open": "100.0", "2. high": "101.0", "3. low": "99.0", "4. close": "100.5",
						                   "5. adjusted close": "100.4", "6. volume": "900", "7. dividend amount": "0.0", "8. split coefficient": "1.0"}
						  }
						}
						""")));

		AlphaVantageTimeSeriesResponse response = client.fetchTimeSeries(AlphaVantageFunction.TIME_SERIES_DAILY_ADJUSTED, "IBM",
				"compact");

		assertThat(response.bars()).hasSize(1);
		assertThat(response.bars().get(0).adjustedClose()).isEqualTo(100.4);
		assertThat(response.bars().get(0).volume()).isEqualTo(900L);
	}

	@Test
	void fetchTimeSeries_errorMessage_throwsInvalidRequest() {
		wireMock.stubFor(get(urlPathEqualTo("/query"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
						.withBody("{\"Error Message\": \"Invalid API call.\"}")));

		assertThatThrownBy(() -> client.fetchTimeSeries(AlphaVantageFunction.TIME_SERIES_DAILY, "BOGUS", "compact"))
				.isInstanceOfSatisfying(AlphaVantageException.class,
						e -> assertThat(e.getReason()).isEqualTo(AlphaVantageException.Reason.INVALID_REQUEST));
	}

	@Test
	void fetchTimeSeries_rateLimitNote_throwsRateLimited() {
		wireMock.stubFor(get(urlPathEqualTo("/query"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
						.withBody("{\"Note\": \"Thank you for using Alpha Vantage! Our standard API call frequency is 25 requests per day.\"}")));

		assertThatThrownBy(() -> client.fetchTimeSeries(AlphaVantageFunction.TIME_SERIES_DAILY, "IBM", "compact"))
				.isInstanceOfSatisfying(AlphaVantageException.class,
						e -> assertThat(e.getReason()).isEqualTo(AlphaVantageException.Reason.RATE_LIMITED));
	}

	@Test
	void fetchTimeSeries_informationRateLimit_throwsRateLimited() {
		wireMock.stubFor(get(urlPathEqualTo("/query"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
						.withBody("{\"Information\": \"We have detected your API key ... please subscribe to a premium plan.\"}")));

		assertThatThrownBy(() -> client.fetchTimeSeries(AlphaVantageFunction.TIME_SERIES_DAILY, "IBM", "compact"))
				.isInstanceOfSatisfying(AlphaVantageException.class,
						e -> assertThat(e.getReason()).isEqualTo(AlphaVantageException.Reason.RATE_LIMITED));
	}
}
