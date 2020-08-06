package org.cios.employee.app;

import java.util.Locale;

import javax.inject.Inject;

import org.cios.employee.app.form.EmpolyeeForm;
import org.cios.employee.domain.model.Empolyee;
import org.cios.employee.domain.service.EmpolyeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EmpolyeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmpolyeeController.class);

	@Inject
	EmpolyeeService empolyeeService;

	@ModelAttribute
	public EmpolyeeForm setUpForm() {
		EmpolyeeForm form = new EmpolyeeForm();
		return form;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getMember(Locale locale,  Model model) {
		Empolyee empolyee = empolyeeService.getMember("1");

		return "welcome/home";
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
	//	@PostMapping("create")
	//	public String create(@Valid MemberForm MemberForm, BindingResult bindingResult,
	//			Model model, RedirectAttributes attributes) {
	//
	//		if (bindingResult.hasErrors()) {
	//			return list(model);
	//		}
	//
	//		Member Member = beanMapper.map(MemberForm, Member.class);
	//
	//		try {
	//			memberService.createMember(Member);
	//		} catch (BusinessException e) {
	//			model.addAttribute(e.getResultMessages());
	//			return list(model);
	//		}
	//
	//		attributes.addFlashAttribute(ResultMessages.success().add(
	//				ResultMessage.fromText("Created successfully!")));
	//		return "redirect:/Member/list";
	//	}

}
