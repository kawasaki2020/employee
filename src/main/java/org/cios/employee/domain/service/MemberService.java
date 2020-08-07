package org.cios.employee.domain.service;

import java.util.List;

import org.cios.employee.domain.model.Member;

public interface MemberService {
	Member getMember(String memberId);

	List<Member> findAll();

	//	Page<Member> searchMembers(String name, Pageable pageable);

	Member createMember(Member creatingMember);
	//
	//	Member updateMember(String employeeId, Member updatingMember);
	//
	//	void deleteMember(String employeeId);

	Member finish(String memberId);
}
