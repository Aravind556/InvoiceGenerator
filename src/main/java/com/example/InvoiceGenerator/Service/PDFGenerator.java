package com.example.InvoiceGenerator.Service;

import com.example.InvoiceGenerator.Dto.InvoiceDTO;
import com.example.InvoiceGenerator.Entity.Invoice;
import com.example.InvoiceGenerator.Entity.Item;
import org.springframework.stereotype.Service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Stream;


@Service
public class PDFGenerator {

 public byte[] generatePDF(Invoice invoice) throws Exception {

         String dir ="Invoices";
         new File(dir).mkdirs();
         String filename = dir + File.separator + "invoice_" + invoice.getInvoiceId() + ".pdf";
         Document document = new Document(PageSize.A4, 50, 50, 50, 50);
         PdfWriter.getInstance(document, new FileOutputStream(filename));
         document.open();

         // Fonts
         Font titleFont = new Font(Font.HELVETICA, 24, Font.BOLD, new Color(45, 62, 80));  // Navy
         Font sectionFont = new Font(Font.HELVETICA, 12, Font.BOLD, new Color(52, 73, 94)); // Dark gray-blue
         Font textFont = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.DARK_GRAY);
         Font tableHeaderFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
         Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC, new Color(120, 120, 120));

         NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN"));
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm");
         String today = dateFormat.format(new Date());

         // Title
         Paragraph title = new Paragraph("INVOICE", titleFont);
         title.setAlignment(Element.ALIGN_CENTER);
         title.setSpacingAfter(20);
         document.add(title);

         // Invoice info
         Paragraph meta = new Paragraph();
         meta.setFont(textFont);
         meta.add("Invoice ID: " + invoice.getInvoiceId() + "\n");
         meta.add("Customer: " + invoice.getName() + "\n");
         meta.add("Email: " + invoice.getEmail() + "\n");
         meta.add("Date: " + today);
         document.add(meta);
         document.add(Chunk.NEWLINE);

         // Table
         PdfPTable table = new PdfPTable(5);
         table.setWidthPercentage(100);
         table.setWidths(new float[]{3, 1, 2, 2, 2});

         // Table headers
         Stream.of("Item", "Qty", "Unit Price", "Discount", "Total").forEach(column -> {
             PdfPCell headerCell = new PdfPCell(new Phrase(column, tableHeaderFont));
             headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
             headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
             headerCell.setBackgroundColor(new Color(52, 152, 219)); // soft blue
             headerCell.setPadding(8);
             table.addCell(headerCell);
         });

         double grandTotal = 0.0;

         for (Item item : invoice.getItems()) {
             double total = item.getQuantity() * item.getUnitPrice() * (1 - item.getDiscount() / 100.0);
             grandTotal += total;

             table.addCell(createCell(item.getItemName(), textFont));
             table.addCell(createCell(String.valueOf(item.getQuantity()), textFont));
             table.addCell(createCell(currencyFormat.format(item.getUnitPrice()), textFont));
             table.addCell(createCell(item.getDiscount() + "%", textFont));
             table.addCell(createCell(currencyFormat.format(total), textFont));
         }

         document.add(table);
         document.add(Chunk.NEWLINE);

         // Grand total
         Paragraph total = new Paragraph("Grand Total: " + currencyFormat.format(grandTotal), sectionFont);
         total.setAlignment(Element.ALIGN_RIGHT);
         document.add(total);
         document.add(Chunk.NEWLINE);

         // Footer note
         Paragraph footer = new Paragraph("Note: This invoice is system-generated. For queries, contact support@example.com", footerFont);
         footer.setAlignment(Element.ALIGN_CENTER);
         footer.setSpacingBefore(20);
         document.add(footer);

         document.close();
         // Read the generated PDF file into a byte array and return
         java.nio.file.Path pdfPath = java.nio.file.Paths.get(filename);
         return java.nio.file.Files.readAllBytes(pdfPath);
     }

     private PdfPCell createCell(String content, Font font) {
         PdfPCell cell = new PdfPCell(new Phrase(content, font));
         cell.setPadding(8);
         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         return cell;
     }
}
