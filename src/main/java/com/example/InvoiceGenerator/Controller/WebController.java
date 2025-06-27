package com.example.InvoiceGenerator.Controller;

import com.example.InvoiceGenerator.Dto.InvoiceDTO;

import com.example.InvoiceGenerator.Service.Implements.InvoiceServiceImp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.InvoiceGenerator.Dto.ItemDTO;

@Controller
public class WebController {

    private final InvoiceServiceImp invoiceService;

    public WebController(InvoiceServiceImp invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Optional<List<InvoiceDTO>> invoicesOpt = invoiceService.getallinvoices();
        model.addAttribute("invoices", invoicesOpt.orElse(new ArrayList<>()));
        return "home";
    }

    @GetMapping("/invoices/create")
    public String showCreateInvoiceForm() {
        return "create-invoice";
    }

    @GetMapping("/invoices/{id}")
    public String viewInvoice(@PathVariable Long id, Model model) {
        InvoiceDTO invoice = invoiceService.getinvoicebyid(id);
        
        if (invoice == null) {
            // Add error message
            model.addAttribute("errorMessage", "Invoice not found");
            return "redirect:/"; // Redirect to homepage
        }
        
        // Calculate totals
        double grandTotal = 0.0;
        for (ItemDTO item : invoice.getItems()) {
            grandTotal += item.getQuantity() * item.getUnitPrice() * (1 - item.getDiscount() / 100.0);
        }
        
        model.addAttribute("invoice", invoice);
        model.addAttribute("grandTotal", String.format("%.2f", grandTotal));
        
        return "invoice-detail";
    }
}