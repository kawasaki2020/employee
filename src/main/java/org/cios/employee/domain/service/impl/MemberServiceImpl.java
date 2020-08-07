package org.cios.employee.domain.service.impl;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.cios.employee.domain.model.Member;
import org.cios.employee.domain.repository.MemberRepository;
import org.cios.employee.domain.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
	private static final long MAX_UNFINISHED_COUNT = 5;

	@Inject
	MemberRepository memberRepository;

	@Inject
	JodaTimeDateFactory dateFactory;

	@Override
	public Member getMember(String memberId) {
		Member member = memberRepository.findById(memberId).orElse(null);
		if (Objects.isNull(member)) {
            ResultMessages messages = ResultMessages.error();
            messages.add(ResultMessage.fromText("[E404] The requested Member is not found. (id=" + memberId + ")"));
            throw new ResourceNotFoundException(messages);
        }
		return member;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	@Override
	public Member createMember(Member creatingMember) {
		//TODO
		creatingMember.setMemberId("2020001");

		memberRepository.create(creatingMember);

		return creatingMember;
	}

    @Override
	public Member finish(String memberId) {
		Member member = getMember(memberId);
		if (member.isFinished()) {
			ResultMessages messages = ResultMessages.error();
			messages.add(ResultMessage
					.fromText("[E002] The requested Todo is already finished. (id="
							+ memberId + ")"));
			throw new BusinessException(messages);
		}
		member.setFinished(true);
		memberRepository.update(member);
		return member;
	}
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
