package utils;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private static XSSFWorkbook wb;
	private static XSSFSheet sheet;
	private static XSSFCell cell;

	public ExcelUtils(String filePath, String sheetName) {
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			sheet = wb.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getData(int row, int col) {
		cell = sheet.getRow(row).getCell(col);
		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(cell);
	}

	public int getRowCount() {
		int rowNum = sheet.getLastRowNum();
		return rowNum;
	}

}
