package com.example.InvoiceGenerator.Entity;


import jakarta.persistence.*;

import java.util.List;

@Table(name = "Invoice")
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long InvoiceId;
    private String name;
    private String email;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;




    public long getInvoiceId() {
        return InvoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        InvoiceId = invoiceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
