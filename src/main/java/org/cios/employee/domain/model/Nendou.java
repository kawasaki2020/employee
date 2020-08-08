package org.cios.employee.domain.model;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Nendou implements Serializable {

	private static final long serialVersionUID = 1L;

	// 年度
	Integer year;

	//社員番号
	Integer menberNum;
}
