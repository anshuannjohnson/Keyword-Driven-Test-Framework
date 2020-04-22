package kdt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.TestException;

public class KeyWordEngine {
	public WebDriver driver;
	public Base basePage;

	public KeyWordElementActions keyWordEleActions;
	public static Workbook book;
	public static Sheet sheet;
	public static ThreadLocal<Workbook> testBook = new ThreadLocal<Workbook>();
	public static ThreadLocal<Sheet> testSheet = new ThreadLocal<Sheet>();

	public final String TESTDATA_SHEET_PATH = "C:\\Users\\anshu\\workspace\\KDT\\src\\main\\resources\\keywords.xlsx";

	public void startExecution(String sheetName) throws TestException, FileNotFoundException, IOException {
        
		
		//Creating Report
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet reportSheet = workbook.createSheet(sheetName);
        Row row = reportSheet.createRow(0);
        Cell cellId = row.createCell(0);
        cellId.setCellValue("Test ID");
        Cell cellName = row.createCell(1);
        cellName.setCellValue("Test Name");
		Cell cellResult = row.createCell(2);
		cellResult.setCellValue("Result");
        //Create report first row complete
		
        int rowCount = 1;
		By locator;
		String locatorValue = null;
		String locatorName = null;
		FileInputStream file = null;
		
		//Reading Excel file start
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			book = WorkbookFactory.create(file);
			testBook.set(book);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sheet = book.getSheet(sheetName);
		testSheet.set(sheet);
		//Excel sheet read complete
		
		
		//Sheet read values start
		int k = 0;
		for (int i = 0; i < testSheet.get().getLastRowNum(); i++) {
			locatorValue = null;
			locatorName = null;
			
			try {
				String testId = testSheet.get().getRow(i + 1).getCell(k).toString().trim();
				String testName = testSheet.get().getRow(i + 1).getCell(k + 1).toString().trim();
				String locatorColValue = testSheet.get().getRow(i + 1).getCell(k + 2).toString().trim();
				if (!locatorColValue.equalsIgnoreCase("NA")) {
					locatorName = locatorColValue.split("=")[0].trim();
					locatorValue = locatorColValue.split("=")[1].trim();
				}
				
				String action = testSheet.get().getRow(i + 1).getCell(k + 3).toString().trim();
				String value = testSheet.get().getRow(i + 1).getCell(k + 4).toString().trim();
				String fuzz = testSheet.get().getRow(i + 1).getCell(k + 5).toString().trim();
				row = reportSheet.createRow(rowCount++);
				
				Cell cell = row.createCell(0);
				cell.setCellValue(testId);
				
				Cell cell2 = row.createCell(1);
				cell2.setCellValue(testName);
				
				switch (action) {
					case "open browser":
						try {
							
							basePage = new Base();		
							driver = basePage.init_driver(value);
							keyWordEleActions = new KeyWordElementActions(driver);
							Cell cell3 = row.createCell(2);
							cell3.setCellValue("Pass");
						} catch (Exception e) {
							Cell cell3 = row.createCell(2);
							cell3.setCellValue("Fail");
						}
						break;
					
					case "enter url":
						try {
							
							keyWordEleActions.launchUrl(value);
							Cell cell3 = row.createCell(2);
							cell3.setCellValue("Pass");
						} catch (Exception e) {
							Cell cell3 = row.createCell(2);
							cell3.setCellValue("Fail");
						}
						break;
					
					case "compare text":
						try {
							
							locator = keyWordEleActions.getClassName(locatorValue);
							String actual = keyWordEleActions.getElementText(locator);
							TestHelper helper = new TestHelper();
							System.out.println(helper.compareString(value, actual));
							if (!helper.compareString(value, actual)) {
								Cell cell3 = row.createCell(2);
								cell3.setCellValue("Fail");
								System.out.println("compare fail");
							} else {
								Cell cell3 = row.createCell(2);
								cell3.setCellValue("Pass");
							}
						} catch (Exception e) {
							Cell cell3 = row.createCell(2);
							cell3.setCellValue("Fail");
						}
						
						break;
						
					case "quit":
						
						try {
							
							keyWordEleActions.quitBrowser();
							Cell cell3 = row.createCell(2);
							cell3.setCellValue("Pass");
						} catch (Exception e) {
							Cell cell3 = row.createCell(2);
							cell3.setCellValue("Fail");
						}
						
						break;
						
					default:
						System.out.println("No user keyword defined");
						break;
				}
				
				if (!action.equals("compare text")) {
					switch (locatorName) {
						case "id":
							try {
								
								if (fuzz.equalsIgnoreCase("true")) {
									TestHelper helper = new TestHelper();
									value = helper.getFuzzedString();
								}
								locator = keyWordEleActions.getId(locatorValue);
								if (action.equalsIgnoreCase("sendkeys")) {
									keyWordEleActions.sendKeys(locator, value);
								} else if (action.equalsIgnoreCase("click")) {
									keyWordEleActions.click(locator);
								}
								locatorName = null;
								System.out.println("id pass");
								Cell cell3 = row.createCell(2);
								cell3.setCellValue("Pass");
								
							} catch (Exception e) {
								Cell cell3 = row.createCell(2);
								cell3.setCellValue("Fail");
								locatorName = null;
								System.out.println("exception id");
							}
							
							break;
							
						case "linkText":
							locator = keyWordEleActions.getLinkText(locatorValue);
							keyWordEleActions.click(locator);
							locatorName = null;
							break;
							
						case "className":
							try {
								
								locator = keyWordEleActions.getClassName(locatorValue);
								keyWordEleActions.click(locator);
								Cell cell3 = row.createCell(2);
								cell3.setCellValue("Pass");
								System.out.println("class pass");
								locatorName = null;
							} catch (Exception e) {
								Cell cell3 = row.createCell(2);
								cell3.setCellValue("Fail");
								locatorName = null;
								System.out.println("exception id");
							}
							
							
							break;
							
						default:
							break;
					}
				}
			} catch (Exception e) {
			}
		}
		try (FileOutputStream outputStream = new FileOutputStream("Report_"+System.currentTimeMillis()+".xlsx")) {
            workbook.write(outputStream);
        }
	}
	

}
