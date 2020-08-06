package org.cios.employee.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;
	// 社員番号 年度+三桁番号
	private String memberId;
	//公司メール
	private String companyMail;
	//个人メール
	private String myMail;
	//基本給
	private Double basically;
	//社員区分
	private String membership;
	//雇用保険
	private Double employmentInsurance;
	//健康保険
	private Double healthInsurance;
	//厚生年金
	private Double memberPension;
	//上限時間
	private Integer upperLmitTime;
	//下限時間
	private Integer minimumTime;
	//有給取得
	private Boolean getPaid;
	//有給残
	private Double remainingPaid;
	//時給
	private Double hourlyWagea;
	//入社時間
	private LocalDate joiningTime;
	//退社時間
	private LocalDate leaveTime;
	//状態
	private String status;
	//削除区分
	private String deletionCategory;
	//職位区分
	private String positionClassification;
	//部門番号
	private Integer departmentNumber;
}
