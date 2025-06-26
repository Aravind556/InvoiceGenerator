package com.example.InvoiceGenerator.Dto;

import java.util.List;

public class InvoiceDTO {

    private String Name;
    private String Email;

    private List<ItemDTO> items;

    public InvoiceDTO() {}
    public InvoiceDTO(String name, String email, List<ItemDTO> items) {
        Name = name;
        Email = email;
        this.items = items;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
