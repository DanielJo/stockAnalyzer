# StockAnalyzer

> **Status: work in progress.** This is a documentation draft. There's no GUI yet, and the REST
> API has no authentication/access control — see [Known gaps](#known-gaps) below.

## What it does

StockAnalyzer is a command-line tool that:

1. **Loads historical price bars** (Open/High/Low/Close/Volume, "OHLCV") for a stock symbol
   from a CSV file, at whatever interval that file represents.
   - Optionally restricts the bars to a time-of-day window (e.g. only 08:30–16:30) and/or a
     date range.
2. **Computes technical-analysis indicators** over that series — via the
   [TA-Lib](https://ta-lib.org/) Java port (`com.tictactec.ta.lib`), plus three
   hand-written indicators (Pivot Points, RVI, ZigZag) that aren't part of TA-Lib.
   Almost the entire TA-Lib function catalogue is wired up (moving averages, oscillators,
   candlestick pattern recognition, etc.) - 137 indicators in total, each with a stable
   numeric id in [`IndicatorType`](src/main/java/io/github/danieljo/stockanalyzer/indicator/IndicatorType.java)
   so you can select several at once with default parameters (`indicators_1,3,5`) instead of
   spelling each one out individually.
3. **Writes the result to a CSV file** — one row per bar, with the OHLCV columns plus one
   column per requested indicator.

It originated as a Java 7, build-file-less CLI tool written for a bachelor's thesis, backed by
a MySQL database. It's since been rebuilt on Maven + Spring Boot, the database dependency has
been removed entirely in favor of CSV file input, and it's now usable both as a **CLI tool**
and as a **REST API** (see [REST API](#rest-api) below) — groundwork for a planned Angular
frontend that will let users submit either a CSV file or a URL (URL input not built yet).

## How it works (pipeline)

```
CLI args ──▶ ArgumentParser ──▶ AnalysisRequest (symbol, interval, time/date window, ...)
                             └─▶ csv_<path> resolved to a file
                                        │
                                        ▼
                        MarketDataService.loadBars(InputStream, request)
                 (CsvImportService parses the file, then date-range /
                  time-of-day filters are applied - same filters that
                  used to be pushed into the SQL query)
                                        │
                                        ▼
                          TALibCalculationService (+ Pivot/RVI/ZigZag)
                       (one calculation per requested indicator argument)
                                        │
                                        ▼
                               CsvExportService.writeCsv()
                                        │
                                        ▼
                                <symbol>_<interval>min.csv
```

`MarketDataService.loadBars` takes a plain `InputStream`, not a file path — the CLI is the only
part that knows about the filesystem (it opens `csv_<path>` into a stream). A future REST
endpoint could hand it a `MultipartFile`'s stream the same way, without changing this method.

## CLI usage

Arguments are `_`-delimited tokens, order-sensitive for `name_`/`time_` (name must come
before time). `csv_<path>` is the exception — everything after the `csv_` prefix is taken as
the literal file path as-is (not split further), since paths routinely contain underscores,
colons or slashes.

| Argument | Meaning |
|---|---|
| `name_<symbol>` | Stock symbol to analyze, e.g. `name_bmw` |
| `time_<interval>` | Bar interval in minutes, e.g. `time_30` |
| `time_<interval>_<start>_<end>` | Also restrict to a time-of-day window, e.g. `time_30_08:30_16:30` |
| `time_<interval>_<start>_<end>_<startdate>_<enddate>` | Also restrict to a date range |
| `csv_<path>` | **Required.** Path to the input CSV file |
| `ta_<indicator>_<params...>` | Compute a TA-Lib indicator with custom parameters, e.g. `ta_macd_12_26_9`, `ta_ema_9`, `ta_rsi_14_3` |
| `pivot` | Compute Pivot Points |
| `rvi_<period>` | Compute RVI (Relative Vigor Index) |
| `zz_<threshold>` | Compute ZigZag |
| `indicators_<id>,<id>,...` | Compute one or more indicators by numeric id, with default parameters - e.g. `indicators_1,3,5`. See [`IndicatorType`](src/main/java/io/github/danieljo/stockanalyzer/indicator/IndicatorType.java) for the full catalog (every indicator already wired up, ~137 in total) and its id/default-parameter table. |
| `delimiter_<char>` | Output CSV delimiter (default `;`) |
| `file_<suffix>` | Suffix appended to the output filename |

`indicators_<ids>` and `ta_<indicator>_<params>` can both compute the same indicator - `indicators_` is a compact way to get several indicators at once with sensible defaults, `ta_` is for when you need a specific, non-default parameter. Same underlying calculation either way; `IndicatorType` just forwards to the same dispatch the `ta_` argument uses. Designed so a REST endpoint can accept the same comma-separated ids later (e.g. `?indicators=1,3,5`) without needing a different selection mechanism.

### Input CSV format

Semicolon-delimited, first line treated as a header and skipped:

```
<ignored>;<dateTime dd.MM.yyyy HH:mm:ss>;<open>;<high>;<low>;<close>;<volume>
```

Note: the file is assumed to already be at the interval passed via `time_<interval>` — there's
no re-aggregation of, say, 1-minute data into 30-minute bars on the CSV path (that logic,
`AggregateService`, is currently unused; it only made sense against the old DB's "fetch 1-min,
aggregate locally" fallback).

Examples:

```
java -jar target/stock-analyzer.jar name_bmw time_30 csv_bmw_30min.csv ta_macd_12_26_9
java -jar target/stock-analyzer.jar name_bmw time_30 csv_bmw_30min.csv indicators_1,2,3,4
```

## Architecture

- **Maven / Spring Boot 3.5.x**, Java 17.
- No database, no JDBC/JPA — `MarketDataService` + `CsvImportService` read directly from a
  CSV file.
- **Two bar data sources**, selected by which REST endpoint you call (see
  [Raw bar lookup](#raw-bar-lookup-apistocks)) rather than a config switch: CSV upload
  (`CsvImportService`) and [Alpha Vantage](https://www.alphavantage.co/) (`AlphaVantageClient` +
  `AlphaVantageImportService`, package `io.github.danieljo.stockanalyzer.alphavantage`). Both
  produce the same `Stock` model, so the rest of the pipeline doesn't care which one supplied it.
- Same jar runs two ways, decided at startup by `StockAnalyzerApplication.main`: if any argument
  doesn't start with `--` (i.e. looks like one of our CLI tokens rather than a Spring property
  override), it runs as a one-shot `CommandLineRunner` with no web server involved at all; with
  no such argument (typically no arguments, or only `--server.port=...`-style ones), it starts
  as the REST server instead. This matters because adding `spring-boot-starter-web` would
  otherwise make Spring Boot try to bind a port on every plain CLI invocation too.

## REST API

No authentication/access control (see [Known gaps](#known-gaps)). Analysis runs **asynchronously**:
submitting one returns immediately with a job id, which you then poll for completion - because
indicator calculations can take a while, and holding an HTTP request open for that isn't great
(client/proxy timeouts, no progress feedback, ties up a server thread the whole time).

| Endpoint | Meaning |
|---|---|
| `GET /api/indicators` | List every available indicator (`{id, displayName}`, from `IndicatorType`) - e.g. for a picker in the UI. |
| `POST /api/analyses` | Submit an analysis. `multipart/form-data`: `file` (the CSV), `symbol`, `interval`, optionally `startTime`/`endTime`, `startDate`/`endDate`, `delimiter`, and `indicatorIds` (comma-separated, e.g. `1,3,5`). Returns `202 Accepted` with `{id, status}` and a `Location` header. |
| `GET /api/analyses/{id}` | Poll status: `{id, status, errorMessage}`, status one of `PENDING`/`RUNNING`/`DONE`/`FAILED`. |
| `GET /api/analyses/{id}/result` | Once `DONE`, returns the CSV result (`text/csv`, as an attachment). `409` if not ready yet, `404` if the id doesn't exist. |

Example:

```
curl -F "file=@bmw_30min.csv" -F "symbol=bmw" -F "interval=30" -F "indicatorIds=1,3,5" \
     http://localhost:8080/api/analyses
# -> 202 {"id":"...","status":"RUNNING",...}

curl http://localhost:8080/api/analyses/<id>
# -> {"id":"...","status":"DONE",...}

curl http://localhost:8080/api/analyses/<id>/result
# -> the CSV
```

**Jobs run strictly one at a time**, on a single-threaded executor (`AnalysisJobService`) - not
a scalability choice, a correctness one: `TALibCalculationService` and `CsvImportService` still
hold their working data in static fields shared by the whole JVM (a limitation carried over from
the CLI-only era, where only one run ever happened per process). Running two jobs concurrently
would let them silently overwrite each other's intermediate results. Serializing execution
sidesteps that without the much larger job of de-static-ing those classes - see
[Known gaps](#known-gaps).

### Raw bar lookup (`/api/stocks`)

Separate from the analysis/job workflow above: these two endpoints just fetch OHLCV bars and
return them as JSON, synchronously, with no indicator calculation. There's no global data-source
switch - which endpoint you call *is* the source selection.

| Endpoint | Meaning |
|---|---|
| `POST /api/stocks/csv/{symbol}` | Same CSV parsing as `/api/analyses` (`multipart/form-data`: `file`, `interval`, optionally `startTime`/`endTime`, `startDate`/`endDate`, `delimiter`), but returns the parsed bars directly instead of queuing an analysis. |
| `GET /api/stocks/alphavantage/{symbol}` | Fetches daily bars from [Alpha Vantage](https://www.alphavantage.co/documentation/). Query params: `function` (`TIME_SERIES_DAILY`, default, or `TIME_SERIES_DAILY_ADJUSTED`), `outputsize` (`compact`, default = latest 100 bars, or `full` = 20+ years). |

Both return the same shape, one entry per bar:

```json
[{"dateTime": "2024-01-15 00:00:00", "open": 100.0, "high": 101.0, "low": 99.0, "close": 100.5,
  "volume": 900, "adjustedClose": null}]
```

`adjustedClose` is only non-null for `TIME_SERIES_DAILY_ADJUSTED` bars. Alpha Vantage daily bars
carry no time-of-day, so `dateTime` is always stamped at midnight, and the bar's `interval` (not
shown in the response, but stored on the underlying `Stock`) is fixed at 1440 (minutes/day).

Example:

```
curl "http://localhost:8080/api/stocks/alphavantage/IBM?function=TIME_SERIES_DAILY_ADJUSTED&outputsize=compact"
```

**Configuring the Alpha Vantage API key**: get a free key at
[alphavantage.co/support/#api-key](https://www.alphavantage.co/support/#api-key), then either
- set the `ALPHAVANTAGE_API_KEY` environment variable, or
- copy `src/main/resources/application-local.yml.example` to `application-local.yml` (already
  gitignored) and fill in `alphavantage.api-key`, then run with `--spring.profiles.active=local`.

The key is never checked in - `application.yml` only references the env var, defaulting to empty.

**Alpha Vantage error handling**: the API answers every request with HTTP 200, even for an
invalid symbol or a hit rate limit - the actual error is a `"Error Message"`, `"Note"` or
`"Information"` field in the JSON body instead of a data payload. `AlphaVantageClient` detects
this and throws `AlphaVantageException`, which `StockDataController` maps to `400 Bad Request`
(invalid symbol/params) or `429 Too Many Requests` (rate limit). The free tier caps out at **25
requests/day**, so `AlphaVantageClientTest` runs against a WireMock stub rather than the real API.

## Known gaps

- **`TIME_SERIES_DAILY_ADJUSTED` may require a premium Alpha Vantage key** - Alpha Vantage has
  moved some "adjusted" endpoints behind paid tiers for newer API keys; a `403`/entitlement error
  from Alpha Vantage for that `function` value is an account limitation, not a bug here.
- **No authentication/access control on the REST API** - anyone who can reach it can submit
  analyses. Fine for local/first-step use, not for exposing it anywhere untrusted.
- **No GUI yet** - the REST API above is the backend half of the planned Angular frontend.
- **Jobs are serialized, not concurrent** (see [REST API](#rest-api)) - throughput is limited to
  one analysis at a time regardless of server resources, until `TALibCalculationService`'s
  static working state is addressed.
- **In-memory job store with no eviction** - `AnalysisJobService` keeps every submitted job
  forever (until restart). Fine short-term / low-traffic, but needs a TTL or a real store before
  running unattended for long.
- `AggregateService` (interval aggregation) is currently unused - it was only ever wired to the
  removed DB fallback path. Would need re-wiring if you want to support, say, always-1-minute
  CSV files aggregated to other intervals on demand.
- `start.txt`'s example (`ta_macd_12_26_9`) uses the `ta_` prefix required by the parser — an
  earlier draft of this doc used `macd_12_26_9` without it, which doesn't actually match the
  argument parser; that's now fixed here.
