package org.cios.employee.domain.repository;

import org.cios.employee.domain.model.Nendou;

public interface NendouRepository {

	Nendou findByYear(Integer year);

	void create(Nendou nendou);

	boolean update(Nendou nendou);
}
