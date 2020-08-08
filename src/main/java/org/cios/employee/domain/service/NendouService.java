package org.cios.employee.domain.service;

import org.cios.employee.domain.model.Nendou;

public interface NendouService {
	Nendou getNendou(Integer year);

	Nendou createNendou(Nendou nendou);

	Nendou updateNendou(Nendou nendou);
}
