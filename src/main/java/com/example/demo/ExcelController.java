package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ExcelController {
	@Autowired
	private ExcelDataService excelDataService;
	
	@GetMapping("/generateExcel")
	
	public ResponseEntity<String> generateExcelFile() {
		try {
			excelDataService.generateExcel();
			return ResponseEntity.ok("Excel file created successfully.");
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while generating Excel file.");
		}
	}
	
	
	

}
