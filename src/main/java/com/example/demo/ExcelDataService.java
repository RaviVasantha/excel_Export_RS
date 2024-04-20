package com.example.demo;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExcelDataService {

    @Autowired
    private RestTemplate restTemplate;

    public void generateExcel() {
    	String url = "http://localhost:8080/customers";
        ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Customer>>() {}
        );
        List<Customer> customerList = responseEntity.getBody();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Customers");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ACNO");
            headerRow.createCell(1).setCellValue("REMARK1");
            headerRow.createCell(2).setCellValue("FLAG");
            headerRow.createCell(3).setCellValue("CIFNO");
            headerRow.createCell(4).setCellValue("NAME");
            headerRow.createCell(5).setCellValue("LIMITAMT");
            headerRow.createCell(6).setCellValue("OUTAMT");
            headerRow.createCell(7).setCellValue("SANDT");
            headerRow.createCell(8).setCellValue("MOB2");
            headerRow.createCell(9).setCellValue("ADDRESS");

            int rowNum = 1;
            for (Customer customer : customerList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(customer.getACNO());
                row.createCell(1).setCellValue(customer.getREMARK1());
                row.createCell(2).setCellValue(customer.getFLAG());
                row.createCell(3).setCellValue(customer.getCIFNO());
                row.createCell(4).setCellValue(customer.getNAME());
                row.createCell(5).setCellValue(customer.getLIMITAMT());
                row.createCell(6).setCellValue(customer.getOUTAMT());
                row.createCell(7).setCellValue(customer.getSANDT());
                row.createCell(8).setCellValue(customer.getMOB2());
                row.createCell(9).setCellValue(customer.getAddress());
            }

            String excelFilePath = "data.xlsx";
            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }

            System.out.println("Excel file created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
