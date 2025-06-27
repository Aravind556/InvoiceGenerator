package com.example.InvoiceGenerator.Service;

import com.example.InvoiceGenerator.Dto.InvoiceDTO;
import com.example.InvoiceGenerator.Entity.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    Optional<List<InvoiceDTO>> getallinvoices();
    InvoiceDTO createinvoiceandmail(InvoiceDTO invoiceDTO);

}
