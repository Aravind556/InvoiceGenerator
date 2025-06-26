package com.example.InvoiceGenerator.Repository;

import com.example.InvoiceGenerator.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Invoicerepo extends JpaRepository<Invoice, Long> {
    Optional<Invoice> getinvoicebyName(String name);

    // Custom query methods can be defined here if needed
    // For example, to find invoices by customer name or date range
    // List<Invoice> findByCustomerName(String customerName);
    // List<Invoice> findByDateBetween(Date startDate, Date endDate);
}
