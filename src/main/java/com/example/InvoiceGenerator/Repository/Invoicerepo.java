package com.example.InvoiceGenerator.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.InvoiceGenerator.Entity.Invoice;


@Repository
public interface Invoicerepo extends JpaRepository<Invoice, Long>{

   
}