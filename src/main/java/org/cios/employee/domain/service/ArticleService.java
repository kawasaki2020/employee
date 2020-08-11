package org.cios.employee.domain.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface ArticleService {

	List<CSVRecord> readCSV(InputStream inputStream, String[] headers) throws IOException;
}
