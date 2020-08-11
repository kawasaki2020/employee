package org.cios.employee.domain.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.cios.employee.domain.model.Member;
import org.cios.employee.domain.model.Nendou;
import org.cios.employee.domain.repository.MemberRepository;
import org.cios.employee.domain.service.MemberService;
import org.cios.employee.domain.service.NendouService;
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

	@Inject
	NendouService nendouService;

	@Override
	@Transactional(readOnly = true)
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
		Nendou nendou = nendouService.getNendou(LocalDate.now().getYear());

		String memberIdstr = Integer.toString(nendou.getYear())
				+ StringUtils.leftPad(Integer.toString(nendou.getMenberNum()), 3, "0");

		creatingMember.setMemberId(new Integer(memberIdstr));
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

	@Override
	public void csvUpload(List<CSVRecord> records, String[] headers) throws IOException {
		for (CSVRecord record : records) {
			Member member = new Member();

			Optional.ofNullable(record.get("memberId"))
					.ifPresent(memberId -> member.setMemberId(Integer.parseInt(memberId)));
			Optional.ofNullable(record.get("companyMail")).ifPresent(companyMail -> member.setCompanyMail(companyMail));
			Optional.ofNullable(record.get("myMail")).ifPresent(myMail -> member.setMyMail(myMail));
			Optional.ofNullable(record.get("basically"))
					.ifPresent(basically -> member.setBasically(Double.parseDouble(basically)));
			Optional.ofNullable(record.get("membership")).ifPresent(membership -> member.setMembership(membership));
			Optional.ofNullable(record.get("employmentInsurance")).ifPresent(
					employmentInsurance -> member.setEmploymentInsurance(Double.parseDouble(employmentInsurance)));
			Optional.ofNullable(record.get("healthInsurance"))
					.ifPresent(healthInsurance -> member.setHealthInsurance(Double.parseDouble(healthInsurance)));
			Optional.ofNullable(record.get("memberPension"))
					.ifPresent(memberPension -> member.setMemberPension(Double.parseDouble(memberPension)));
			Optional.ofNullable(record.get("upperLmitTime"))
					.ifPresent(upperLmitTime -> member.setUpperLmitTime(Integer.parseInt(upperLmitTime)));
			Optional.ofNullable(record.get("minimumTime"))
					.ifPresent(minimumTime -> member.setMinimumTime(Integer.parseInt(minimumTime)));
			Optional.ofNullable(record.get("getPaid"))
					.ifPresent(getPaid -> member.setGetPaid(Boolean.parseBoolean(getPaid)));
			Optional.ofNullable(record.get("remainingPaid"))
					.ifPresent(remainingPaid -> member.setRemainingPaid(Double.parseDouble(remainingPaid)));
			Optional.ofNullable(record.get("hourlyWagea"))
					.ifPresent(hourlyWagea -> member.setHourlyWagea(Double.parseDouble(hourlyWagea)));

			// TODO
			//member.setJoiningTime(record.get("joiningTime"));
			Optional.ofNullable(record.get("joiningTime"))
					.ifPresent(joiningTime -> member.setJoiningTime(LocalDate.now()));
			// TODO
			//member.setLeaveTime(record.get("leaveTime"));
			Optional.ofNullable(record.get("leaveTime")).ifPresent(leaveTime -> member.setLeaveTime(LocalDate.now()));

			Optional.ofNullable(record.get("status")).ifPresent(status -> member.setStatus(status));
			Optional.ofNullable(record.get("deletionCategory"))
					.ifPresent(deletionCategory -> member.setDeletionCategory(Boolean.parseBoolean(deletionCategory)));
			Optional.ofNullable(record.get("positionClassification"))
					.ifPresent(positionClassification -> member.setPositionClassification(positionClassification));
			Optional.ofNullable(record.get("departmentNumber"))
					.ifPresent(departmentNumber -> member.setDepartmentNumber(Integer.parseInt(departmentNumber)));

			createMember(member);
		}
	}

}
