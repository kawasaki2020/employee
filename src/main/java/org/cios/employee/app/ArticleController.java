package org.cios.employee.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.csv.CSVRecord;
import org.cios.employee.app.form.FileUploadForm;
import org.cios.employee.domain.model.Member;
import org.cios.employee.domain.service.ArticleService;
import org.cios.employee.domain.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.opencsv.CSVWriter;


@RequestMapping("article")
@Controller
public class ArticleController {

	@Inject
	ArticleService articleService;

	@Inject
	MemberService memberService;

	private Long uploadAllowableFileSize = Long.valueOf(10 * 1024 * 1024);

	private static String[] headers = new String[] { "memberId", "companyMail", "myMail", "basically", "membership",
			"employmentInsurance", "healthInsurance", "memberPension", "upperLmitTime", "minimumTime", "getPaid",
			"remainingPaid", "hourlyWagea", "joiningTime", "leaveTime", "status", "deletionCategory",
			"positionClassification", "departmentNumber" };

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

		try {
			List<CSVRecord> records = articleService.readCSV(uploadFile.getInputStream(), headers);
			memberService.csvUpload(records, headers);
		} catch (IOException e) {
			result.rejectValue(uploadFile.getName(), "e.xx.at.6004", new Object[] { uploadAllowableFileSize }, null);
			return "article/uploadForm";
		}

		redirectAttributes.addFlashAttribute(ResultMessages.success().add(
				"i.xx.at.0001"));

		return "redirect:/article/upload?complete";
	}

	@RequestMapping(value = "uploadCSV", method = RequestMethod.GET, params = "complete")
	public String uploadComplete() {
		return "article/uploadComplete";
	}

	@RequestMapping(value = "downloadCSV", method = RequestMethod.GET)
	public ResponseEntity<byte[]> upload(BindingResult result, RedirectAttributes redirectAttributes)
			throws IOException {
		// TODO
		List<Member> members = memberService.findAll();
		String fileName = UUID.randomUUID().toString();;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(baos));
		csvWriter.writeNext(headers);
		for(Member member : members)
	       {
			String[] strArray = new String[Member.class.getDeclaredFields().length] ;
			strArray[0] = Optional.ofNullable(member.getMemberId()).map(memberId -> Integer.toString(memberId)).orElse("");
			strArray[1] = Optional.ofNullable(member.getCompanyMail()).orElse("");
			strArray[2] = Optional.ofNullable(member.getMyMail()).orElse("");
			strArray[3] = Optional.ofNullable(member.getBasically()).map(basically -> Double.toString(basically)).orElse("");
			strArray[4] = Optional.ofNullable(member.getMembership()).orElse("");
			strArray[5] = Optional.ofNullable(member.getEmploymentInsurance()).map(employmentInsurance -> Double.toString(employmentInsurance)).orElse("");
			strArray[6] = Optional.ofNullable(member.getHealthInsurance()).map(healthInsurance -> Double.toString(healthInsurance)).orElse("");
			strArray[7] = Optional.ofNullable(member.getMemberPension()).map(memberPension -> Double.toString(memberPension)).orElse("");
			strArray[8] = Optional.ofNullable(member.getUpperLmitTime()).map(upperLmitTime -> Integer.toString(upperLmitTime)).orElse("");
			strArray[8] = Optional.ofNullable(member.getMinimumTime()).map(minimumTime -> Integer.toString(minimumTime)).orElse("");
			strArray[8] = Optional.ofNullable(member.getGetPaid()).map(getPaid -> Boolean.toString(getPaid)).orElse("");
			strArray[7] = Optional.ofNullable(member.getRemainingPaid()).map(remainingPaid -> Double.toString(remainingPaid)).orElse("");
			strArray[7] = Optional.ofNullable(member.getHourlyWagea()).map(hourlyWagea -> Double.toString(hourlyWagea)).orElse("");
			// TODO
			strArray[7] = "";
			strArray[7] = "";
			strArray[2] = Optional.ofNullable(member.getStatus()).orElse("");
			strArray[8] = Optional.ofNullable(member.getDeletionCategory()).map(deletionCategory -> Boolean.toString(deletionCategory)).orElse("");
			strArray[2] = Optional.ofNullable(member.getPositionClassification()).orElse("");
			strArray[0] = Optional.ofNullable(member.getDepartmentNumber()).map(departmentNumber -> Integer.toString(departmentNumber)).orElse("");

			csvWriter.writeNext(strArray);
	       }
		csvWriter.close();

		HttpHeaders httpHeaders = new HttpHeaders();
		String downloadFielName = new String(fileName.getBytes("UTF-8"), "UTF-8");
		httpHeaders.setContentDispositionFormData("attachment", downloadFielName);
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		return new ResponseEntity<byte[]>(baos.toByteArray(), httpHeaders, HttpStatus.CREATED);
	}
}
