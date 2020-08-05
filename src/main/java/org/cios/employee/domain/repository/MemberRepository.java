package org.cios.employee.domain.repository;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.cios.employee.domain.model.Member;

public interface MemberRepository {
	Member findOne(String memberId);

	List<Member> findAll();

	long countByContainsName(String name);

	List<Member> findPageByContainsName(String name, RowBounds rowBounds);

	void createMember(Member creatingMember);

	void createCredential(Member creatingMember);

	boolean updateMember(Member updatingMember);

	void deleteMember(String memberId);

	void deleteCredential(String memberId);
}
