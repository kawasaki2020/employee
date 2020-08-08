package org.cios.employee.domain.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.cios.employee.domain.model.Member;

public interface MemberService {
	Member getMember(Integer memberId);

	List<Member> findAll();

	Member createMember(Member creatingMember);

	Member updateMember(Member updatingMember);

	void deleteMember(Integer employeeId);

	void csvUpload(File file, String[] headers) throws IOException;
}
