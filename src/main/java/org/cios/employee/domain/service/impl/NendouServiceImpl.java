package org.cios.employee.domain.service.impl;

import java.time.LocalDate;
import java.util.Objects;

import javax.inject.Inject;

import org.cios.employee.domain.model.Nendou;
import org.cios.employee.domain.repository.NendouRepository;
import org.cios.employee.domain.service.NendouService;
import org.springframework.stereotype.Service;

@Service
public class NendouServiceImpl implements NendouService {

	@Inject
	NendouRepository nendouRepository;

	@Override
	public Nendou getNendou(Integer year) {
		Nendou nendou = nendouRepository.findByYear(year);
		if (Objects.isNull(nendou)) {
			Nendou nendouDefault = new Nendou();
			nendouDefault.setYear(LocalDate.now().getYear());
			nendouDefault.setMenberNum(1);

			this.createNendou(nendouDefault);

			return nendouDefault;
		}
		nendou.setMenberNum(nendou.getMenberNum() + 1);
		this.updateNendou(nendou);

		return nendou;
	}

	@Override
	public Nendou createNendou(Nendou nendou) {
		nendouRepository.create(nendou);
		return nendou;
	}

	@Override
	public Nendou updateNendou(Nendou nendou) {
		nendouRepository.update(nendou);
		return nendou;
	}
}
