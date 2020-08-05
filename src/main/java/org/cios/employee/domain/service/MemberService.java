package org.cios.employee.domain.service;

import java.util.List;

import org.cios.employee.domain.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {
	List<Member> findAll();

	Page<Member> searchMembers(String name, Pageable pageable);

	Member getMember(String memberId);

	Member createMember(Member creatingMember);

	Member updateMember(String memberId, Member updatingMember);

	void deleteMember(String memberId);
}
