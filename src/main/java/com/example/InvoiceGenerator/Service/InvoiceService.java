package com.example.InvoiceGenerator.Service;

import com.example.InvoiceGenerator.Dto.InvoiceDTO;
import com.example.InvoiceGenerator.Entity.Invoice;

import java.util.List;

public interface InvoiceService {

    Optional<List<InvoiceDTO>> getallinvoices();
    InvoiceDTO getinvoicebyname(String name);
    InvoiceDTO createinvoice(InvoiceDTO invoiceDTO);

}
