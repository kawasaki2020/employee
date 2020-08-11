package org.cios.employee.app;

import java.io.IOException;

import javax.inject.Inject;

import org.cios.employee.app.form.FileUploadForm;
import org.cios.employee.domain.service.ArticleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;

@RequestMapping("article")
@Controller
public class ArticleController {

	@Inject
	ArticleService articleService;

	@Value("${upload.allowableFileSize}")
	private int uploadAllowableFileSize;

	@ModelAttribute
	public FileUploadForm setFileUploadForm() {
		return new FileUploadForm();
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET, params = "form")
	public String uploadForm() {
		return "article/uploadForm";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@Validated FileUploadForm form,
			BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "article/uploadForm";
		}

		MultipartFile uploadFile = form.getFile();
		String originalFilename = uploadFile.getOriginalFilename();
		if (!StringUtils.hasLength(originalFilename)) {
			result.rejectValue(uploadFile.getName(), "e.xx.at.6002");
			return "article/uploadForm";
		}

		if (uploadFile.isEmpty()) {
			result.rejectValue(uploadFile.getName(), "e.xx.at.6003");
			return "article/uploadForm";
		}

		if (uploadAllowableFileSize < uploadFile.getSize()) {
			result.rejectValue(uploadFile.getName(), "e.xx.at.6004",
					new Object[] { uploadAllowableFileSize }, null);
			return "article/uploadForm";
		}
		String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
		if (!".csv".equals(fileType.toLowerCase())) {
			result.rejectValue(uploadFile.getName(), "e.xx.at.6004", new Object[] { uploadAllowableFileSize }, null);
			return "article/uploadForm";
		}
		//  TODO
		String[] headers = new String[] { "memberId", "companyMail", "myMail", "basically", "membership",
				"employmentInsurance", "healthInsurance", "memberPension", "upperLmitTime", "minimumTime", "getPaid",
				"remainingPaid", "hourlyWagea", "joiningTime", "leaveTime", "status", "deletionCategory",
				"positionClassification", "departmentNumber" };
		try {
			articleService.readCSV(uploadFile.getInputStream(), headers);
		} catch (IOException e) {
			result.rejectValue(uploadFile.getName(), "e.xx.at.6004", new Object[] { uploadAllowableFileSize }, null);
			return "article/uploadForm";
		}

		redirectAttributes.addFlashAttribute(ResultMessages.success().add(
				"i.xx.at.0001"));

		return "redirect:/article/upload?complete";
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET, params = "complete")
	public String uploadComplete() {
		return "article/uploadComplete";
	}
}
