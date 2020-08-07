package org.cios.employee.app;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.cios.employee.app.form.MemberForm;
import org.cios.employee.domain.model.Member;
import org.cios.employee.domain.service.MemberService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

@Controller
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Inject
	MemberService memberService;

	@Inject
	Mapper beanMapper;

	@ModelAttribute
	public MemberForm setUpForm() {
		MemberForm form = new MemberForm();
		return form;
	}

	//http://localhost:8080/employee/getMember?memberId=1
	@RequestMapping(value = "/getMember", method = RequestMethod.GET)
	public String getMember(@RequestParam String memberId, Model model) {
		Member member = memberService.getMember(memberId);
		model.addAttribute("member", member);
		// TODO
		return "member";
	}

	//http://localhost:8080/employee/getMemberAll?
	@RequestMapping(value = "/getMemberAll", method = RequestMethod.GET)
	public String getMemberAll(Model model) {
		List<Member> members = memberService.findAll();
		model.addAttribute("members", members);
		// TODO
		return "member/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid MemberForm MemberForm, BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {

		if (bindingResult.hasErrors()) {
			return getMemberAll(model);
		}

		Member member = beanMapper.map(MemberForm, Member.class);
		memberService.createMember(member);
		attributes.addFlashAttribute(ResultMessages.success().add(ResultMessage.fromText("Created successfully!")));
		return "redirect:/member/list";
	}

	//	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	//	public String home(Locale locale, Model model) {
	//		logger.info("Welcome home! The client locale is {}.", locale);
	//
	//		Date date = new Date();
	//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
	//				DateFormat.LONG, locale);
	//
	//		String formattedDate = dateFormat.format(date);
	//
	//		model.addAttribute("serverTime", formattedDate);
	//
	//		return "welcome/home";
	//	}
	//
	//	@GetMapping("list")
	//	public String list(Model model) {
	//		Collection<Member> Members = memberService.findAll();
	//		model.addAttribute("Members", Members);
	//		return "Member/list";
	//	}
	//

}
