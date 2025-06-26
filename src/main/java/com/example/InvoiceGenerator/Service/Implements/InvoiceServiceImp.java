package com.example.InvoiceGenerator.Service.Implements;

import com.example.InvoiceGenerator.Dto.InvoiceDTO;
import com.example.InvoiceGenerator.Entity.Invoice;
import com.example.InvoiceGenerator.Mapper.InvoiceMapper;
import com.example.InvoiceGenerator.Repository.Invoicerepo;
import com.example.InvoiceGenerator.Service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImp implements InvoiceService {

    private final Invoicerepo repo;
    private final InvoiceMapper mapper;

    public InvoiceServiceImp(Invoicerepo repo, InvoiceMapper mapper) {
        this.repo=repo;
        this.mapper= mapper;
    }

    @Override
    public Optional<List<InvoiceDTO>> getallinvoices() {
        List<InvoiceDTO> dtos= repo.findAll().stream()
                .map(mapper::toDto)
                .toList();
        return Optional.of(dtos);
    }

    @Override
    public InvoiceDTO getinvoicebyname(String name) {
        return repo.getinvoicebyName(name)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Override
    public InvoiceDTO createinvoice(InvoiceDTO invoicedto){
        Invoice invoice = mapper.toEntity(invoicedto);
        Invoice saved= repo.save(invoice);
        InvoiceDTO savedDTO = mapper.toDto(saved);

        //call pdf generators service

        return savedDTO;

    }


}
