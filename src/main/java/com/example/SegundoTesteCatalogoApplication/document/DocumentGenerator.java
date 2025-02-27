package com.example.SegundoTesteCatalogoApplication.document;



import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import org.springframework.cglib.core.Converter;
import org.springframework.stereotype.Service;


import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.FileOutputStream;

@Service
public class DocumentGenerator {

    public String htmlToPdf(String processedHtml) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);

            DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);

            ConverterProperties converterProperties = new ConverterProperties();

            converterProperties.setFontProvider(defaultFont);

            HtmlConverter.convertToPdf(processedHtml, pdfWriter, converterProperties);

            FileOutputStream fount = new FileOutputStream("/Users/matheusruegg/Desktop/employee.pdf");

            byteArrayOutputStream.writeTo(fount);
            byteArrayOutputStream.close();

            byteArrayOutputStream.flush();
            fount.close();

            return null;



        }catch (Exception ex){
            //exception occured

        }

        return null;
    }
}
