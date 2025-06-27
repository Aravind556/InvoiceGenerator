package com.example.InvoiceGenerator.Controller;


import com.example.InvoiceGenerator.Dto.InvoiceDTO;
import com.example.InvoiceGenerator.Entity.Invoice;
import com.example.InvoiceGenerator.Service.Implements.InvoiceServiceImp;
import com.example.InvoiceGenerator.Service.InvoiceService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/Invoice")
public class Invoicecontroller {


    private final InvoiceServiceImp invoiceService;

    public Invoicecontroller(InvoiceServiceImp invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    ResponseEntity<?> returnall() {
        Optional<List<InvoiceDTO>> invoices = invoiceService.getallinvoices();
        if(invoices.isPresent()) {
            return ResponseEntity.ok(invoices.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Invoices not found");
        }
    }

    @PostMapping
    ResponseEntity<?> createinvoice(@RequestBody InvoiceDTO invoiceDTO) {
        try {
            InvoiceDTO created = invoiceService.createinvoiceandmail(invoiceDTO);
            return ResponseEntity.ok(created);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating invoice: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/view")
public ResponseEntity<ByteArrayResource> viewPdf(@PathVariable int id) {
    byte[] pdfFile = invoiceService.downloadedpdf(id);
    if (pdfFile == null || pdfFile.length == 0) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    return ResponseEntity.ok()
            // Notice: NO Content-Disposition header here
            .contentType(MediaType.APPLICATION_PDF)
            .contentLength(pdfFile.length)
            .body(new ByteArrayResource(pdfFile));
}
    

    @GetMapping("/{id}/pdf")
    public ResponseEntity<ByteArrayResource> downloadpdf(@PathVariable int id)  {

        byte[] pdfFile = invoiceService.downloadedpdf(id);
        if (pdfFile == null || pdfFile.length==0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfFile.length)
                .body(new ByteArrayResource(pdfFile));
    }

}
