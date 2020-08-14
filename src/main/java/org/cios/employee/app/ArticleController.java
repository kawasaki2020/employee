package org.cios.employee.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.csv.CSVRecord;
import org.cios.employee.app.form.FileUploadForm;
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
import com.opencsv.ICSVWriter;

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

	// TODO http://localhost:8080/employee/uploadCSV
	@RequestMapping(value = "uploadCSV", method = RequestMethod.GET)
	public String uploadForm() {
		return "article/uploadForm";
	}

	@RequestMapping(value = "uploadCSV", method = RequestMethod.POST)
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

		try {
			List<CSVRecord> records = articleService.readCSV(uploadFile.getInputStream(), headers);
			memberService.csvUpload(records, headers);
		} catch (Exception e) {
			result.rejectValue(uploadFile.getName(), null, null, e.getMessage());
			return "article/uploadForm";
		}

		redirectAttributes.addFlashAttribute(ResultMessages.success().add(
				"i.xx.at.0001"));

		return "redirect:/uploadComplete";
	}

	@RequestMapping(value = "uploadComplete", method = RequestMethod.GET)
	public String uploadComplete() {
		return "article/uploadComplete";
	}
	// TODO http://localhost:8080/employee/downloadCSV
	@RequestMapping(value = "downloadCSV", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadCSV()
			throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(baos), ICSVWriter.DEFAULT_SEPARATOR,
				ICSVWriter.NO_QUOTE_CHARACTER, ICSVWriter.NO_ESCAPE_CHARACTER, ICSVWriter.DEFAULT_LINE_END);
		csvWriter.writeNext(headers);

		List<String[]> members = articleService.getCSVData();
		for (String[] member : members) {
			csvWriter.writeNext(member);
		}
		csvWriter.close();

		DateTimeFormatter dateFormatter  = DateTimeFormatter.ofPattern("yyyyMMdd_HH-mm-ss");
		LocalDateTime sysDatetime = LocalDateTime.now();
		String fileName = "employee_"+ sysDatetime.format(dateFormatter) + ".csv";
		String downloadFielName = new String(fileName.getBytes("UTF-8"), "UTF-8");

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentDispositionFormData("attachment", downloadFielName);
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		return new ResponseEntity<byte[]>(baos.toByteArray(), httpHeaders, HttpStatus.CREATED);
	}
}
