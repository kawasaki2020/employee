package org.cios.employee.domain.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.cios.employee.domain.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Override
	public List<CSVRecord> readCSV(InputStream inputStream, String[] headers) throws IOException {
		CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers);
		CSVParser parser = new CSVParser(new InputStreamReader(inputStream), formator);
		List<CSVRecord> records = parser.getRecords();
		parser.close();
		return records;
	}
}
