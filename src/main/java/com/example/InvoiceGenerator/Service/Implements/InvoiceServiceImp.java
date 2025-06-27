package com.example.InvoiceGenerator.Service.Implements;

import com.example.InvoiceGenerator.Dto.InvoiceDTO;
import com.example.InvoiceGenerator.Entity.Invoice;
import com.example.InvoiceGenerator.Mapper.InvoiceMapper;
import com.example.InvoiceGenerator.Repository.Invoicerepo;
import com.example.InvoiceGenerator.Service.EmailService;
import com.example.InvoiceGenerator.Service.InvoiceService;
import com.example.InvoiceGenerator.Service.PDFGenerator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InvoiceServiceImp implements InvoiceService {

    private final Invoicerepo repo;
    private final InvoiceMapper mapper;
    private final EmailService emailService;

    private final PDFGenerator PDFGenerator;

    private final Map<Long, byte[]> pdfCache = new ConcurrentHashMap<>();


    public InvoiceServiceImp(Invoicerepo repo, InvoiceMapper mapper, EmailService emailService, PDFGenerator PDFGenerator) {
        this.repo=repo;
        this.mapper= mapper;
        this.emailService = emailService;
        this.PDFGenerator = new PDFGenerator();
    }

    @Override
    public Optional<List<InvoiceDTO>> getallinvoices() {
        List<InvoiceDTO> dtos= repo.findAll().stream()
                .map(mapper::toDto)
                .toList();
        return Optional.of(dtos);
    }

    public InvoiceDTO getinvoicebyid(long id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Override
    public InvoiceDTO createinvoiceandmail(InvoiceDTO invoicedto)  {
        try{Invoice invoice = mapper.toEntity(invoicedto);
        Invoice saved = repo.save(invoice);
        InvoiceDTO savedDTO = mapper.toDto(saved);

        //call pdf generators service
        byte[]  pdf;
        try {
            pdf = PDFGenerator.generatePDF(saved);
            // caching the generated PDF
            pdfCache.put(saved.getInvoiceId(),pdf);
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF" + e.getMessage());
        }
        emailService.sendInvoiceEmail(savedDTO.getEmail(), pdf);
        return savedDTO;}
        catch (Exception e) {
            throw new RuntimeException("Error creating invoice: " + e.getMessage());
        }
    }
    public byte[] downloadedpdf(int id) {
        if(pdfCache.containsKey(id)){
            return (pdfCache.get(id));
        }
        else{

            InvoiceDTO invoice = getinvoicebyid(id);
            if (invoice == null) {
                throw new RuntimeException("Invoice not found");
            }
            byte[] pdf;
            try {
                pdf = PDFGenerator.generatePDF(mapper.toEntity(invoice));
                // caching the generated PDF
                pdfCache.put((long) id, pdf);
            } catch (Exception e) {
                throw new RuntimeException("Error generating PDF: " + e.getMessage());
            }
            return pdf;
        }
    }





}
