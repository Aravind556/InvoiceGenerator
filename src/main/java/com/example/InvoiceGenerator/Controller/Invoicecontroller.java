package com.example.InvoiceGenerator.Controller;


import com.example.InvoiceGenerator.Dto.InvoiceDTO;
import com.example.InvoiceGenerator.Entity.Invoice;
import com.example.InvoiceGenerator.Service.Implements.InvoiceServiceImp;
import com.example.InvoiceGenerator.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController("/Invoice")
public class Invoicecontroller {

    @Autowired
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

}
