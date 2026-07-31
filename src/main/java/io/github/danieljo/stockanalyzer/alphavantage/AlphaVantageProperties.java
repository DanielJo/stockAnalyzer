package io.github.danieljo.stockanalyzer.alphavantage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Bound from the {@code alphavantage.*} keys in application.yml (see
 * {@code application-local.yml.example} for how to supply a real, non-checked-in api-key).
 */
@ConfigurationProperties(prefix = "alphavantage")
public class AlphaVantageProperties {

	private String baseUrl = "https://www.alphavantage.co";
	private String apiKey;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
