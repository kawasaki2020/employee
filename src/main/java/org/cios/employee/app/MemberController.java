package org.cios.employee.app;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

/**
 * Handles requests for the application home page.
 */
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

	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "welcome/home";
	}

	@GetMapping("list")
	public String list(Model model) {
		Collection<Member> Members = memberService.findAll();
		model.addAttribute("Members", Members);
		return "Member/list";
	}

	@PostMapping("create")
	public String create(@Valid MemberForm MemberForm, BindingResult bindingResult,
			Model model, RedirectAttributes attributes) {

		if (bindingResult.hasErrors()) {
			return list(model);
		}

		Member Member = beanMapper.map(MemberForm, Member.class);

		try {
			memberService.createMember(Member);
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return list(model);
		}

		attributes.addFlashAttribute(ResultMessages.success().add(
				ResultMessage.fromText("Created successfully!")));
		return "redirect:/Member/list";
	}

}
