# StockAnalyzer

> **Status: work in progress.** This is a first documentation draft. The app does not run
> end-to-end yet — see [Known gaps](#known-gaps-not-yet-runnable) below.

## What it does

StockAnalyzer is a command-line tool that:

1. **Loads historical price bars** (Open/High/Low/Close/Volume, "OHLCV") for a stock symbol
   from a MySQL database, at a requested time interval (e.g. 1-minute, 30-minute bars).
   - If bars at exactly the requested interval aren't already stored, it falls back to
     loading 1-minute bars and aggregating them locally into the requested interval.
   - Optionally restricts the bars to a time-of-day window (e.g. only 08:30–16:30) and/or a
     date range.
2. **Computes technical-analysis indicators** over that series — via the
   [TA-Lib](https://ta-lib.org/) Java port (`com.tictactec.ta.lib`), plus three
   hand-written indicators (Pivot Points, RVI, ZigZag) that aren't part of TA-Lib.
   You pick which indicators to compute via CLI arguments; almost the entire TA-Lib
   function catalogue is wired up (moving averages, oscillators, candlestick pattern
   recognition, etc.).
3. **Writes the result to a CSV file** — one row per bar, with the OHLCV columns plus one
   column per requested indicator.

It originated as a Java 7, build-file-less CLI tool written for a bachelor's thesis. It's
currently being modernized onto Maven + Spring Boot as a first step toward eventually adding
a GUI on top.

## How it works (pipeline)

```
CLI args ──▶ ArgumentParser ──▶ AnalysisRequest (symbol, interval, time/date window, ...)
                                        │
                                        ▼
                              StockDataService.loadBars()
                    (Spring Data JPA: check precomputed interval,
                     else fetch 1-min bars + AggregateService)
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

## CLI usage

Arguments are `_`-delimited tokens, order-sensitive for `name_`/`time_` (name must come
before time):

| Argument | Meaning |
|---|---|
| `name_<symbol>` | Stock symbol to analyze, e.g. `name_bmw` |
| `time_<interval>` | Bar interval in minutes, e.g. `time_30` |
| `time_<interval>_<start>_<end>` | Also restrict to a time-of-day window, e.g. `time_30_08:30_16:30` |
| `time_<interval>_<start>_<end>_<startdate>_<enddate>` | Also restrict to a date range |
| `ta_<indicator>_<params...>` | Compute a TA-Lib indicator, e.g. `ta_macd_12_26_9`, `ta_ema_9`, `ta_rsi_14_3` |
| `pivot` | Compute Pivot Points |
| `rvi_<period>` | Compute RVI (Relative Vigor Index) |
| `zz_<threshold>` | Compute ZigZag |
| `delimiter_<char>` | CSV delimiter (default `;`) |
| `file_<suffix>` | Suffix appended to the output filename |

Example:

```
java -jar target/stock-analyzer.jar name_bmw time_30 ta_macd_12_26_9
```

> **Note:** `start.txt`'s existing example (`macd_12_26_9`, without the `ta_` prefix) doesn't
> actually match the argument parser — it's a pre-existing inconsistency in the original
> project's docs, not something introduced by the Spring Boot migration. Worth fixing
> alongside this README.

## Architecture (post-migration)

- **Maven / Spring Boot 3.5.x**, Java 17. Runs as a `CommandLineRunner` — still a CLI tool,
  no web layer.
- **Spring Data JPA** (`SymbolEntity`/`IntervalEntity`/`StockDataEntity` +
  `SymbolRepository`/`StockDataRepository`) instead of the original raw JDBC.
- DB connection comes from `DB_URL`/`DB_USERNAME`/`DB_PASSWORD` environment variables —
  no credentials are hardcoded or defaulted.

## Known gaps (not yet runnable)

- **Needs a real MySQL database.** There's no working fallback to load bars from CSV instead
  (the code path exists in `CsvImportService` but nothing calls it yet). Without
  `DB_URL`/`DB_USERNAME`/`DB_PASSWORD` pointing at a real instance, the app fails fast at
  startup.
- **JPA entity/column mapping is inferred, not verified.** `SymbolEntity`, `IntervalEntity`,
  and `StockDataEntity` map to table/column names taken from the original raw SQL strings
  (including the space in the `Min Intervall` column), but this hasn't been run against a
  real schema yet.
- **No GUI yet** — this migration only prepares the ground (Spring context, DI-friendly
  service layer, externalized config) for one.
- Some internal state is still static/shared across the whole JVM (see code comments in
  `TALibCalculationService` / `CsvImportService`) — fine for one CLI run per process, would
  need addressing before the app can handle concurrent requests (relevant once a GUI/web
  layer exists).
