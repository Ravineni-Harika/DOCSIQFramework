package com.Automation.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



public class Excelutil
{
	public static Workbook book;
	public static Sheet sheet;
	
	
	
	 public static Object[][] getTestData(String excelPath, String sheetName){
	        FileInputStream file = null;
	        try {
	             file = new FileInputStream(excelPath);
	            } 
	        
	        catch (FileNotFoundException e) {
	            System.out.println("File not found:: It seems you have given wrong file path");
	            e.printStackTrace();
	            }
	        
	            try {
	                book = WorkbookFactory.create(file);
	            } catch (InvalidFormatException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            
	            sheet = book.getSheet(sheetName);
	            
	            
	            //Two dimensional Array to store row and column values
	            
	            Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
	            
	            for(int i=0; i<sheet.getLastRowNum(); i++){
	                
	                for(int j=0; j<sheet.getRow(0).getLastCellNum(); j++){
	                    sheet.getRow(i+1).getCell(j).setCellType(Cell.CELL_TYPE_STRING);
	                    data[i][j] = sheet.getRow(i+1).getCell(j).getStringCellValue().toString().trim();
	                    
	                    
	                    
	                }
	            }
	            
	            return data;
	            
	 }}
	        
	        
	        
	        
	        
	        
	        
	        
	