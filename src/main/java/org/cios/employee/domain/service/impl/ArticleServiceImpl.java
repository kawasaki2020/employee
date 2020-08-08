package org.cios.employee.domain.service.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.cios.employee.domain.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Override
	public List<CSVRecord> readCSV(File file, String[] headers) throws IOException {
		CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers);
		FileReader fileReader = new FileReader(file);
		CSVParser parser = new CSVParser(fileReader, formator);
		List<CSVRecord> records = parser.getRecords();
		parser.close();
		fileReader.close();
		return records;
	}
}
