package io.github.danieljo.stockanalyzer.alphavantage;

/**
 * Alpha Vantage answers every request with HTTP 200, so application-level failures (bad symbol,
 * rate limit) surface as this exception instead of an HTTP client error - see
 * {@link AlphaVantageClient#fetchTimeSeries}.
 */
public class AlphaVantageException extends RuntimeException {

	public enum Reason {
		/** Bad request as reported via Alpha Vantage's "Error Message" field, e.g. unknown symbol. */
		INVALID_REQUEST,
		/** Daily/rate limit hit, reported via Alpha Vantage's "Note" or "Information" field. */
		RATE_LIMITED
	}

	private final Reason reason;

	public AlphaVantageException(Reason reason, String message) {
		super(message);
		this.reason = reason;
	}

	public Reason getReason() {
		return reason;
	}
}
