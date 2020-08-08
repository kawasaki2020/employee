package org.cios.employee.domain.repository;

import java.util.Optional;

import org.cios.employee.domain.model.Nendou;

public interface NendouRepository {

	Optional<Nendou> findByYear(Integer year);

	void create(Nendou nendou);

	boolean update(Nendou nendou);
}
