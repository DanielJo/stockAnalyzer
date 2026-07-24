package io.github.danieljo.stockanalyzer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.danieljo.stockanalyzer.model.SymbolEntity;

public interface SymbolRepository extends JpaRepository<SymbolEntity, Long> {

	/**
	 * Replaces DBConnect.checkInterval(interval, symbol): true if pre-aggregated data
	 * for this symbol already exists at the requested interval.
	 */
	Optional<SymbolEntity> findByNameAndMinIntervall(String name, Integer minIntervall);
}
