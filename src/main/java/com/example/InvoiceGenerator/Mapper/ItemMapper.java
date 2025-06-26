package com.example.InvoiceGenerator.Mapper;

import com.example.InvoiceGenerator.Dto.ItemDTO;
import com.example.InvoiceGenerator.Entity.Item;

public class ItemMapper {


    public static ItemDTO toDTO(Item item){

        if (item == null) return null;
        return new ItemDTO(
                item.getItemName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getDiscount()
        );
    }

    public static Item toEntity(ItemDTO itemdto){
        if(itemdto ==null)return null;
        Item item = new Item();
        item.setItemName(itemdto.getItemName());
        item.setQuantity(itemdto.getQuantity());
        item.setUnitPrice(itemdto.getUnitPrice());
        item.setDiscount(itemdto.getDiscount());

        return item;
    };
}
