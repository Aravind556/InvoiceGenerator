package com.example.InvoiceGenerator.Mapper;

import com.example.InvoiceGenerator.Dto.InvoiceDTO;
import com.example.InvoiceGenerator.Dto.ItemDTO;
import com.example.InvoiceGenerator.Entity.Invoice;
import com.example.InvoiceGenerator.Entity.Item;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {

    public InvoiceDTO toDto(Invoice invoice) {
        if(invoice == null) return null;
        List<ItemDTO> itemDTOS =null;
        if(invoice.getItems()!=null){
            itemDTOS=invoice.getItems().stream()
                    .map(ItemMapper::toDTO)
                    .toList();
        }
        return new InvoiceDTO(invoice.getName(), invoice.getEmail(), itemDTOS);
    }

    public Invoice toEntity(InvoiceDTO dto){
        if(dto ==null) return null;
        Invoice invoice = new Invoice();
        invoice.setName(dto.getName());
        invoice.setEmail(dto.getEmail());
        if(dto.getItems()!=null){
            List<Item> items = dto.getItems().stream()
                    .map(ItemMapper::toEntity)
                    .toList();
            items.forEach(item-> item.setInvoice(invoice));
            invoice.setItems(items);
        }
        return invoice;
    }
}