package org.cios.employee.domain.service.Impl;

import javax.inject.Inject;

import org.cios.employee.domain.model.Member;
import org.cios.employee.domain.repository.MemberRepository;
import org.cios.employee.domain.service.MemberService;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

//	@Override
//	@Transactional(readOnly = true)
//	public List<Member> findAll() {
//		return memberRepository.findAll();
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public Page<Member> searchMembers(String name, Pageable pageable) {
//		List<Member> members = null;
//		// Count Members by search criteria
//		long total = memberRepository.countByContainsName(name);
//		if (0 < total) {
//			RowBounds rowBounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
//			members = memberRepository.findPageByContainsName(name, rowBounds);
//		} else {
//			members = new ArrayList<Member>();
//		}
//		return new PageImpl<Member>(members, pageable, total);
//	}

	@Override
	@Transactional(readOnly = true)
	public Member getMember(String memberId) {
		return memberRepository.findOne(memberId);
	}
//
//	@Override
//	public Member createMember(Member creatingMember) {
//		MemberCredential creatingCredential = creatingMember
//				.getCredential();
//
//		// get processing current date time
//		DateTime currentDateTime = dateFactory.newDateTime();
//
//		creatingMember.setCreatedAt(currentDateTime);
//		creatingMember.setLastModifiedAt(currentDateTime);
//
//		// decide sign id(email-address)
//		String signId = creatingCredential.getSignId();
//		if (!StringUtils.hasLength(signId)) {
//			signId = creatingMember.getEmailAddress();
//			creatingCredential.setSignId(signId.toLowerCase());
//		}
//
//		// save member & member credential
//		try {
//
//			// Registering member details
//			memberRepository.createMember(creatingMember);
//			// //Registering credential details
//			memberRepository.createCredential(creatingMember);
//			return creatingMember;
//		} catch (DuplicateKeyException e) {
//			// TODO
//		}
//		return creatingMember;
//	}
//
//	@Override
//	public Member updateMember(String employeeId, Member updatingMember) {
//		// get member
//		Member member = getMember(employeeId);
//
//		// override updating member attributes
//		beanMapper.map(updatingMember, member, "member.update");
//
//		// get processing current date time
//		DateTime currentDateTime = dateFactory.newDateTime();
//		member.setLastModifiedAt(currentDateTime);
//
//		// save updating member
//		boolean updated = memberRepository.updateMember(member);
//		if (!updated) {
//			throw new ObjectOptimisticLockingFailureException(Member.class,
//					member.getMemberId());
//		}
//		return member;
//	}
//
//	@Override
//	public void deleteMember(String employeeId) {
//
//		// First Delete from credential (Child)
//		memberRepository.deleteCredential(employeeId);
//		// Delete member
//		memberRepository.deleteMember(employeeId);
//	}
}
