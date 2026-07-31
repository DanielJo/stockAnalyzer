package io.github.danieljo.stockanalyzer.alphavantage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Talks to the Alpha Vantage {@code TIME_SERIES_DAILY}/{@code TIME_SERIES_DAILY_ADJUSTED}
 * endpoints (see {@link AlphaVantageFunction}). Alpha Vantage answers every request with HTTP
 * 200, even for an invalid symbol or a hit rate limit - the only way to tell is the shape of the
 * JSON body, which {@link #requireTimeSeries} checks before parsing bars.
 */
@Component
public class AlphaVantageClient {

	private static final String TIME_SERIES_KEY = "Time Series (Daily)";

	private final RestClient restClient;
	private final AlphaVantageProperties properties;

	public AlphaVantageClient(RestClient.Builder builder, AlphaVantageProperties properties) {
		this.restClient = builder.baseUrl(properties.getBaseUrl()).build();
		this.properties = properties;
	}

	public AlphaVantageTimeSeriesResponse fetchTimeSeries(AlphaVantageFunction function, String symbol, String outputSize) {
		JsonNode root = restClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/query")
						.queryParam("function", function.name())
						.queryParam("symbol", symbol)
						.queryParam("outputsize", outputSize)
						.queryParam("datatype", "json")
						.queryParam("apikey", properties.getApiKey())
						.build())
				.retrieve()
				.body(JsonNode.class);

		JsonNode timeSeries = requireTimeSeries(root);

		List<AlphaVantageDailyBar> bars = new ArrayList<>();
		Iterator<Map.Entry<String, JsonNode>> fields = timeSeries.fields();
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> entry = fields.next();
			bars.add(parseBar(LocalDate.parse(entry.getKey()), entry.getValue()));
		}
		bars.sort(Comparator.comparing(AlphaVantageDailyBar::date));

		return new AlphaVantageTimeSeriesResponse(symbol, bars);
	}

	private JsonNode requireTimeSeries(JsonNode root) {
		JsonNode timeSeries = root.get(TIME_SERIES_KEY);
		if (timeSeries != null && !timeSeries.isMissingNode()) {
			return timeSeries;
		}

		JsonNode errorMessage = root.get("Error Message");
		if (errorMessage != null) {
			throw new AlphaVantageException(AlphaVantageException.Reason.INVALID_REQUEST, errorMessage.asText());
		}
		JsonNode note = root.get("Note");
		if (note != null) {
			throw new AlphaVantageException(AlphaVantageException.Reason.RATE_LIMITED, note.asText());
		}
		JsonNode information = root.get("Information");
		if (information != null) {
			throw new AlphaVantageException(AlphaVantageException.Reason.RATE_LIMITED, information.asText());
		}
		throw new AlphaVantageException(AlphaVantageException.Reason.INVALID_REQUEST,
				"Unexpected Alpha Vantage response: missing \"" + TIME_SERIES_KEY + "\"");
	}

	private AlphaVantageDailyBar parseBar(LocalDate date, JsonNode bar) {
		double open = bar.get("1. open").asDouble();
		double high = bar.get("2. high").asDouble();
		double low = bar.get("3. low").asDouble();
		double close = bar.get("4. close").asDouble();
		JsonNode adjustedCloseNode = bar.get("5. adjusted close");
		Double adjustedClose = adjustedCloseNode != null ? adjustedCloseNode.asDouble() : null;
		long volume = bar.get(adjustedCloseNode != null ? "6. volume" : "5. volume").asLong();
		return new AlphaVantageDailyBar(date, open, high, low, close, adjustedClose, volume);
	}
}
