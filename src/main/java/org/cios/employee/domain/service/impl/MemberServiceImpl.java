package org.cios.employee.domain.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cios.employee.domain.model.Member;
import org.cios.employee.domain.repository.MemberRepository;
import org.cios.employee.domain.service.MemberService;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Inject
	MemberRepository memberRepository;

	@Override
	public Member getMember(Integer memberId) {
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
		Integer memberId = memberRepository.findMaxMemberId();
		String memberIdstrs = Integer.toString(LocalDate.now().getYear())
				+ StringUtils.leftPad(Integer.toString(memberId), 3, "0");
		creatingMember.setMemberId(new Integer(memberIdstrs));
		memberRepository.create(creatingMember);

		return creatingMember;
	}

	@Override
	public Member updateMember(Member updatingMember) {
		this.getMember(updatingMember.getMemberId());

		boolean updated = memberRepository.update(updatingMember);
		if (!updated) {
			throw new ObjectOptimisticLockingFailureException(Member.class,  updatingMember.getMemberId());
		}
		return updatingMember;
	}

	@Override
	public void deleteMember(Integer employeeId) {
		Member member = this.getMember(employeeId);
		memberRepository.delete(member);
	}
}
