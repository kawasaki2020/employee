package org.cios.employee.domain.repository;

import org.cios.employee.domain.model.Empolyee;

public interface EmpolyeeRepository {

	Empolyee findOne(String employeeId);
//
//	List<Member> findAll();
//
//	long countByContainsName(String name);
//
//	List<Member> findPageByContainsName(String name, RowBounds rowBounds);
//
//	void createMember(Member creatingMember);
//
//	void createCredential(Member creatingMember);
//
//	boolean updateMember(Member updatingMember);
//
//	void deleteMember(String employeeId);
//
//	void deleteCredential(String employeeId);
}
