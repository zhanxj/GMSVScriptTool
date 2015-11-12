package tool.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Lists;

public class GMSVGrammarReader {
	
	public List<Grammar> readGrammarFromFile(File src) {
		try {
			return readSheet(readFile(src));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Sheet readFile(File src) throws Exception {
		InputStream stream = new FileInputStream(src);
		Workbook workbook = src.getName().endsWith(".xlsx") ? new XSSFWorkbook(stream) : new HSSFWorkbook(stream);
		for (int i = 0, loop = workbook.getNumberOfSheets();i < loop;i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getSheetName().toLowerCase().equals("script")) {
				return sheet;
			}
		}
		return null;
	}
	
	private List<Grammar> readSheet(Sheet sheet) {
		int rowAmount = sheet.getLastRowNum();
		List<Grammar> grammars = Lists.newArrayListWithCapacity(rowAmount);
		for (int rowNumber = 1;rowNumber < rowAmount;rowNumber++) {
			Grammar grammar = new Grammar();
			Row row = sheet.getRow(rowNumber);
			grammar.setName(row.getCell(0).getStringCellValue());
			grammar.setDescript(row.getCell(1).getStringCellValue());
			grammar.setReturnValue(createAttribute(row, 2, 3));
			for (int i = 0;i < 5;i++) {
				grammar.addParam(createAttribute(row, 4 + (i << 1), 5 + (i << 1)));
			}
			grammars.add(grammar);
		}
		return grammars;
	}
	
	private Attribute createAttribute(Row row, int clazzIndex, int descriptIndex) {
		try {
			Attribute attribute = new Attribute();
			attribute.setClazz(row.getCell(clazzIndex).getStringCellValue());
			attribute.setDescript(row.getCell(descriptIndex).getStringCellValue());
			return attribute.getClazz().length() == 0 ? null : attribute;
		} catch (Exception e) {
			return null;
		}
	}

}
