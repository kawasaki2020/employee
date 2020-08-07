package org.cios.employee.domain.repository;

import java.util.List;
import java.util.Optional;

import org.cios.employee.domain.model.Member;

public interface MemberRepository {

	Optional<Member> findById(String memberId);

	List<Member> findAll();

	void create(Member member);

    boolean update(Member member);

    void delete(Member member);

    long countByFinished(boolean finished);
}
