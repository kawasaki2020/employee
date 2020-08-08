package org.cios.employee.app.form;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class FileUploadForm implements Serializable {

	private MultipartFile file;

	private String description;
}
