package com.example.InvoiceGenerator.Entity;


import jakarta.persistence.*;

@Entity
@Table(name="Items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemId;

    private String ItemName;
    private long Quantity;
    private double UnitPrice;
    private double TotalPrice;

    private int discount;

    @ManyToOne
    @JoinColumn(name="invoice_id")
    private Invoice invoice;

    public Item() {

    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Item(long itemId, String itemName, long quantity, double unitPrice, int discount, Invoice invoice) {
        this.itemId = itemId;
        this.ItemName = itemName;
        Quantity = quantity;
        UnitPrice = unitPrice;
        this.discount = discount;
        this.TotalPrice = (Quantity * UnitPrice) - (Quantity * UnitPrice * (discount) / 100);
        this.invoice = invoice;
    }



    public int getDiscount() {
        return discount;
    }
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public long getQuantity() {
        return Quantity;
    }

    public void setQuantity(long quantity) {
        Quantity = quantity;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public long getItemId() {
        return itemId;
    }

}

