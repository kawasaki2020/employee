package org.cios.employee.domain.service;

import org.cios.employee.domain.model.Member;

public interface MemberService {
//	List<Member> findAll();
//
//	Page<Member> searchMembers(String name, Pageable pageable);

	Member getMember(String memberId);
//
//	Member createMember(Member creatingMember);
//
//	Member updateMember(String employeeId, Member updatingMember);
//
//	void deleteMember(String employeeId);
}
