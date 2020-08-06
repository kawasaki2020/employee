package org.cios.employee.domain.service;

import org.cios.employee.domain.model.Empolyee;

public interface EmpolyeeService {
//	List<Member> findAll();
//
//	Page<Member> searchMembers(String name, Pageable pageable);

	Empolyee getMember(String employeeId);
//
//	Member createMember(Member creatingMember);
//
//	Member updateMember(String employeeId, Member updatingMember);
//
//	void deleteMember(String employeeId);
}
