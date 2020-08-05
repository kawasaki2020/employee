package org.cios.employee.domain.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.RowBounds;
import org.cios.employee.domain.model.Member;
import org.cios.employee.domain.model.MemberCredential;
import org.cios.employee.domain.repository.MemberRepository;
import org.cios.employee.domain.service.MemberService;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;

@Transactional
@Service
public class MemberServiceImpl implements MemberService {
	@Inject
	MemberRepository memberRepository;

	@Inject
	JodaTimeDateFactory dateFactory;

	@Inject
	Mapper beanMapper;

	@Override
	@Transactional(readOnly = true)
	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Member> searchMembers(String name, Pageable pageable) {
		List<Member> members = null;
		// Count Members by search criteria
		long total = memberRepository.countByContainsName(name);
		if (0 < total) {
			RowBounds rowBounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
			members = memberRepository.findPageByContainsName(name, rowBounds);
		} else {
			members = new ArrayList<Member>();
		}
		return new PageImpl<Member>(members, pageable, total);
	}

	@Override
	@Transactional(readOnly = true)
	public Member getMember(String memberId) {
		// find member
		Member member = memberRepository.findOne(memberId);
		return member;
	}

	@Override
	public Member createMember(Member creatingMember) {
		MemberCredential creatingCredential = creatingMember
				.getCredential();

		// get processing current date time
		DateTime currentDateTime = dateFactory.newDateTime();

		creatingMember.setCreatedAt(currentDateTime);
		creatingMember.setLastModifiedAt(currentDateTime);

		// decide sign id(email-address)
		String signId = creatingCredential.getSignId();
		if (!StringUtils.hasLength(signId)) {
			signId = creatingMember.getEmailAddress();
			creatingCredential.setSignId(signId.toLowerCase());
		}

		// save member & member credential
		try {

			// Registering member details
			memberRepository.createMember(creatingMember);
			// //Registering credential details
			memberRepository.createCredential(creatingMember);
			return creatingMember;
		} catch (DuplicateKeyException e) {
			// TODO
		}
		return creatingMember;
	}

	@Override
	public Member updateMember(String memberId, Member updatingMember) {
		// get member
		Member member = getMember(memberId);

		// override updating member attributes
		beanMapper.map(updatingMember, member, "member.update");

		// get processing current date time
		DateTime currentDateTime = dateFactory.newDateTime();
		member.setLastModifiedAt(currentDateTime);

		// save updating member
		boolean updated = memberRepository.updateMember(member);
		if (!updated) {
			throw new ObjectOptimisticLockingFailureException(Member.class,
					member.getMemberId());
		}
		return member;
	}

	@Override
	public void deleteMember(String memberId) {

		// First Delete from credential (Child)
		memberRepository.deleteCredential(memberId);
		// Delete member
		memberRepository.deleteMember(memberId);
	}
}
