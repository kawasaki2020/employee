package org.cios.employee.domain.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.cios.employee.domain.model.Member;

public interface MemberService {
	Member getMember(Integer memberId);

	List<Member> findAll();

	Member createMember(Member creatingMember);

	Member updateMember(Member updatingMember);

	void deleteMember(Integer employeeId);

	void csvUpload(List<CSVRecord> records, String[] headers) throws IOException;
}
