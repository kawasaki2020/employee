package org.cios.employee.domain.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface ArticleService {

	List<CSVRecord> readCSV(File file, String[] headers) throws IOException;
}
