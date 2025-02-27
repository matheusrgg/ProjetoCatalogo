package com.example.SegundoTesteCatalogoApplication.generator;
import java.util.List;
import com.example.SegundoTesteCatalogoApplication.document.DocumentGenerator;
import com.example.SegundoTesteCatalogoApplication.mapper.DataMapper;
import com.example.SegundoTesteCatalogoApplication.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
public class DemoDocument {

    @Autowired
    private DocumentGenerator documentGenerator;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DataMapper dataMapper;

    @PostMapping(value = "/generate/document", produces = "application/pdf")
    public ResponseEntity<byte[]> generateDocument(@RequestBody List<Employee> employeeList) {
        // Step 1: Convert employee data into a Thymeleaf template
        Context dataContext = dataMapper.setData(employeeList);
        String finalHtml = springTemplateEngine.process("template", dataContext);

        // Step 2: Convert HTML to PDF and get the PDF bytes
        byte[] pdfBytes = documentGenerator.htmlToPdf(finalHtml);

        // Step 3: Return the PDF as an HTTP response
        if (pdfBytes == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=employee.pdf");
        headers.add("Content-Type", "application/pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
