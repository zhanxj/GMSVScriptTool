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

/**
 * Grammar rules reader of GMSV.
 * @author 	fuhuiyuan
 */
public class GMSVGrammarReader {
	
	/**
	 * Read all grammars from a file.
	 * @param 	src
	 * 			GMSV rule file.
	 * @return	List of grammars.
	 */
	public List<Grammar> readGrammarFromFile(File src) {
		try {
			return readSheet(readFile(src));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Read an excel file.
	 * @param 	src
	 * 			excel file
	 * @return	{@link Sheet}
	 * @throws 	Exception
	 */
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
	
	/**
	 * Read from sheet.
	 * @param 	sheet
	 * 			{@link Sheet}
	 * @return	List of grammars.
	 */
	private List<Grammar> readSheet(Sheet sheet) {
		int rowAmount = sheet.getLastRowNum();
		List<Grammar> grammars = Lists.newArrayListWithCapacity(rowAmount);
		for (int rowNumber = 1;rowNumber < rowAmount;rowNumber++) {
			Grammar grammar = new Grammar();
			Row row = sheet.getRow(rowNumber);
			grammar.setName(row.getCell(0).getStringCellValue());
			grammar.setDescript(row.getCell(1).getStringCellValue());
			grammar.setPattern(row.getCell(2).getStringCellValue());
			grammar.setReturnValue(createAttribute(row, 3, 4));
			for (int i = 0;i < 5;i++) {
				grammar.addParam(createAttribute(row, 5 + (i << 1), 6 + (i << 1)));
			}
			grammars.add(grammar);
		}
		return grammars;
	}
	
	/**
	 * Create synax attribute from a row of sheet.
	 * @param 	row
	 * 			Row of sheet.
	 * @param 	clazzIndex
	 * 			Class cell index of row.
	 * @param 	descriptIndex
	 * 			Descript cell index of row.
	 * @return	synax attribute
	 */
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
