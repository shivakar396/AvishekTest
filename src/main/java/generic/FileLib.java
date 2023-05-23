package generic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.Test;
/**
 * 
 * @author Xuriti
 *
 *@FileLib This Class is use  to read the data and write the data in text file
 *This class contains getPropertyData method to read the data from file and setPropertydata method to write data into the file
 */
public class FileLib {
	Properties p=new Properties();
	/**
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 * 
	 */
	public String getPropertyData(String key) throws IOException
	{
		key=key.replace(" ", "");
		FileInputStream file=new FileInputStream("./src/test/resources/data/commondata.property");
		p.load(file);
		return p.getProperty(key);
	}
	/**
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public String getNBFCID(String key) throws IOException
	{
		key=key.replace(" ", "");
		key=key+"NBFCID";
		FileInputStream file=new FileInputStream("./src/test/resources/data/commondata.property");
		p.load(file);
		return p.getProperty(key);
	}
	/**
	 * 
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public void setNBFCID(String key,String value) throws IOException
	{
		key=key.replace(" ", "");
		key=key+"NBFCID";
		FileInputStream file=new FileInputStream("./src/test/resources/data/commondata.property");
		p.load(file);
		p.setProperty(key, value);
		FileOutputStream fos=new FileOutputStream("./src/test/resources/data/commondata.property");
		p.store(fos, "Updated");
	}
	/**
	 * 
	 * @param key
	 * @param value
	 * @throws IOException
	 * 
	 */
	public void setPropertyData(String key,String value) throws IOException
	{
		FileInputStream file=new FileInputStream("./src/test/resources/data/commondata.property");
		p.load(file);
		p.setProperty(key, value);
		FileOutputStream fos=new FileOutputStream("./src/test/resources/data/commondata.property");
		p.store(fos, "Updated");
	}
//	public int getExcelRowCount() throws EncryptedDocumentException, IOException
//	{
//		FileInputStream fis=new FileInputStream("./src/test/resources/data/DevInvoice1.xls");
//		Workbook wb=WorkbookFactory.create(fis);
//		return wb.getSheet("Sheet1").getLastRowNum();
//	}
	/**
	 * 
	 * @param FileName with file extension llike(.xls,.sxls)  in Excel 97- 2003 workbook compatible
	 * @param SheetName
	 * @param row
	 * @param col
	 * @return type double
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * 
	 */
	public double getExcelNumericCellData(String FileName, String SheetName, int row,int col) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis=new FileInputStream("./src/test/resources/data/DevInvoice1.xls");
		Workbook wb=WorkbookFactory.create(fis);

		//Row and column starts with zero(0).
		
		return wb.getSheet("Sheet1").getRow(row).getCell(col).getNumericCellValue();
	}
	/**
	 * 
	 * @param FileName with file extension llike(.xls,.sxls)  in Excel 97- 2003 workbook compatible
	 * @param SheetName
	 * @param row
	 * @param col
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public String getExcelStringCellData(String FileName,String SheetName,int row,int col) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis=new FileInputStream("./src/test/resources/data/"+FileName);
		Workbook wb=WorkbookFactory.create(fis);

		//Row and column starts with zero(0).
		
		return wb.getSheet(SheetName).getRow(row).getCell(col).getStringCellValue();
	}
	/**
	 * 
	 * @param FileName with file extension llike(.xls,.sxls)  in Excel 97- 2003 workbook compatible
	 * @param SheetName
	 * @param row
	 * @param col
	 * @param data
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public void setExcelPropertyData(String FileName,String SheetName,int row,int col,String data) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis=new FileInputStream("./src/test/resources/data/"+FileName);
		Workbook wb=WorkbookFactory.create(fis);
		wb.getSheet(SheetName).getRow(row).getCell(col).setCellValue(data);

		FileOutputStream fos=new FileOutputStream("./src/test/resources/data/"+FileName);
		wb.write(fos);
		wb.close();
	}
	/**
	 * 
	 * @param FileName with file extension llike(.xls,.sxls)  in Excel 97- 2003 workbook compatible
	 * @param SheetName
	 * @return  type int
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * 
	 */
	public int getExcelLastRowCount(String FileName,String SheetName) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis=new FileInputStream("./src/test/resources/data/"+FileName);
		Workbook wb=WorkbookFactory.create(fis);
		return wb.getSheet(SheetName).getLastRowNum();
	}
}