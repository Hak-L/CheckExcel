package com.aniu.checkpayment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aniu.pojo.DateBaseEntity;
import com.aniu.pojo.PayMentEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class XlsxUtil {
	public final static String DATE_OUTPUT_PATTERNS = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";  
	
	public static List<DateBaseEntity> getXlsxList(String fName){
		List<DateBaseEntity> list_one = null;
		List<DateBaseEntity> list_two = null;
		List<DateBaseEntity> list_count = new ArrayList<>();
		JSONArray js_one = fromExcelToJson(fName,0);
		JSONArray js_two = fromExcelToJson(fName,1);
		
		if(js_one != null && !js_one.isEmpty()){
			list_one = (List<DateBaseEntity>)JSONArray.toCollection(js_one, DateBaseEntity.class);
			list_count.addAll(list_one);
		}
		if(js_two != null && !js_two.isEmpty()){
			list_two = (List<DateBaseEntity>)JSONArray.toCollection(js_two, DateBaseEntity.class);
			list_count.addAll(list_two);
		}
		return list_count;
	}
	
	
	/**
	 * Excel转Json
	 * type 0:问股票  1：订单
	 * @return
	 */
	private static JSONArray fromExcelToJson(String excel,int type){
		
		
		Sheet sheet;
        Workbook book;
        Cell cell1, cell2, cell3, cell4, cell5,cell6,cell7;
        JSONArray array = new JSONArray();
        try {
        	
        	book = ReadExcelUtils(excel);
            //获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
            sheet = book.getSheetAt(type);
            
	        // 得到总行数  
            int rowNum = sheet.getLastRowNum();  
            Row row = sheet.getRow(0);
            Row row_0 = sheet.getRow(0);
            //int colNum = row.getPhysicalNumberOfCells();  
            // 正文内容应该从第二行开始,第一行为表头的标题  
            for (int i = 1; i <= rowNum; i++) { 
                row = sheet.getRow(i);
                cell1 = row.getCell(0);
                cell2 = row.getCell(1);
                cell3 = row.getCell(2);
                cell4 = row.getCell(3);
                cell5 = row.getCell(4);
                cell6 = row.getCell(5);
                cell7 = row.getCell(6);
                
                JSONObject object = new JSONObject();
                String amount = getCellValue(cell2);
                if(amount.contains(".")){
                   object.put("amount",amount);
                }else{
                   object.put("amount",amount+".00");
                }
                
                object.put("order_no",getCellValue(cell5));
                object.put("payment_no",getCellValue(cell6));
                array.add(object);
            } 
            /*System.out.println(array.toString());*/
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return array;
	}
	
	private static Workbook ReadExcelUtils(String filepath) {
        
		Workbook wb = null;
		
		if(filepath==null){  
            return null;  
        }
		
        String ext = filepath.substring(filepath.lastIndexOf("."));  
       
        try {  
        	
        	
        	/*System.out.println(filepath);*/
        	
            InputStream is = new FileInputStream(filepath);  
            if(".xls".equals(ext)){
            	wb = new HSSFWorkbook(is);  
            }else if(".xlsx".equals(ext)){  
                wb = new XSSFWorkbook(is);  
            } 
        } catch (FileNotFoundException e) {  
        	System.out.println(e);
        } catch (IOException e) {  
        	System.out.println(e);
        }  
        return wb;
    }  
	
    private static String getCellValue(Cell cell) {  
            String ret;  
            switch (cell.getCellType()) {  
            case Cell.CELL_TYPE_BLANK:  
                ret = "";  
                break;  
            case Cell.CELL_TYPE_BOOLEAN:  
                ret = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case Cell.CELL_TYPE_ERROR:  
                ret = null;  
                break;  
            case Cell.CELL_TYPE_FORMULA:  
                Workbook wb = cell.getSheet().getWorkbook();  
                CreationHelper crateHelper = wb.getCreationHelper();  
                FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();  
                ret = getCellValue(evaluator.evaluateInCell(cell));  
                break;  
            case Cell.CELL_TYPE_NUMERIC:  
                if (DateUtil.isCellDateFormatted(cell)) {   
                    Date theDate = cell.getDateCellValue();
                    SimpleDateFormat sd = new SimpleDateFormat(DATE_OUTPUT_PATTERNS);
                    ret = sd.format(theDate);
                } else {   
                    ret = NumberToTextConverter.toText(cell.getNumericCellValue());  
                }  
                break;  
            case Cell.CELL_TYPE_STRING:  
                ret = cell.getRichStringCellValue().getString();  
                break;  
            default:  
                ret = null;  
            }  
              
            return ret; //有必要自行trim  
        }  
}
