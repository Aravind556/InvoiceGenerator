package com.example.InvoiceGenerator.Repository;

import com.example.InvoiceGenerator.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Itemrepo extends JpaRepository<Item,Long>{


}
