package org.cios.employee.domain.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.cios.employee.domain.model.Member;
import org.cios.employee.domain.service.ArticleService;
import org.cios.employee.domain.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Inject
	MemberService memberService;

	@Override
	public List<CSVRecord> readCSV(InputStream inputStream, String[] headers) throws IOException {
		CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers).withSkipHeaderRecord().withIgnoreEmptyLines().withTrim();
		CSVParser parser = new CSVParser(new InputStreamReader(inputStream), formator);
		List<CSVRecord> records = parser.getRecords();
		parser.close();
		return records;
	}

	@Override
	public List<String[]> getCSVData()  {
		List<String[]> listArr = new ArrayList<String[]>();
		List<Member> members = memberService.findAll();
		for(Member member : members)
	       {
			String[] strArray = new String[Member.class.getDeclaredFields().length] ;
			strArray[0] = Optional.ofNullable(member.getMemberId()).map(memberId -> Integer.toString(memberId)).orElse("");
			strArray[1] = Optional.ofNullable(member.getCompanyMail()).orElse("");
			strArray[2] = Optional.ofNullable(member.getMyMail()).orElse("");
			strArray[3] = Optional.ofNullable(member.getBasically()).map(basically -> Double.toString(basically)).orElse("");
			strArray[4] = Optional.ofNullable(member.getMembership()).orElse("");
			strArray[5] = Optional.ofNullable(member.getEmploymentInsurance()).map(employmentInsurance -> Double.toString(employmentInsurance)).orElse("");
			strArray[6] = Optional.ofNullable(member.getHealthInsurance()).map(healthInsurance -> Double.toString(healthInsurance)).orElse("");
			strArray[7] = Optional.ofNullable(member.getMemberPension()).map(memberPension -> Double.toString(memberPension)).orElse("");
			strArray[8] = Optional.ofNullable(member.getUpperLmitTime()).map(upperLmitTime -> Integer.toString(upperLmitTime)).orElse("");
			strArray[8] = Optional.ofNullable(member.getMinimumTime()).map(minimumTime -> Integer.toString(minimumTime)).orElse("");
			strArray[8] = Optional.ofNullable(member.getGetPaid()).map(getPaid -> Boolean.toString(getPaid)).orElse("");
			strArray[7] = Optional.ofNullable(member.getRemainingPaid()).map(remainingPaid -> Double.toString(remainingPaid)).orElse("");
			strArray[7] = Optional.ofNullable(member.getHourlyWagea()).map(hourlyWagea -> Double.toString(hourlyWagea)).orElse("");
			// TODO
			strArray[7] = "";
			strArray[7] = "";
			strArray[2] = Optional.ofNullable(member.getStatus()).map(status -> Integer.toString(status)).orElse("");
			strArray[8] = Optional.ofNullable(member.getDeletionCategory()).map(deletionCategory -> Boolean.toString(deletionCategory)).orElse("");
			strArray[2] = Optional.ofNullable(member.getPositionClassification()).map(positionClassification -> Integer.toString(positionClassification)).orElse("");
			strArray[0] = Optional.ofNullable(member.getDepartmentNumber()).map(departmentNumber -> Integer.toString(departmentNumber)).orElse("");

			listArr.add(strArray);
	       }
		return listArr;
	}
}
