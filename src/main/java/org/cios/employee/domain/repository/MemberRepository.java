package org.cios.employee.domain.repository;

import java.util.List;

import org.cios.employee.domain.model.Member;

public interface MemberRepository {

	Member  findById(Integer memberId);

	List<Member> findAll();

	void create(Member member);

	boolean update(Member member);

	void delete(Member member);

}
