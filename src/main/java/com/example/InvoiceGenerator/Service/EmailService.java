package com.example.InvoiceGenerator.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.*;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendInvoiceEmail(String emailid, byte[] pdfFile) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(emailid);
        helper.setSubject("Your Invoice");
        helper.setText("Please find your invoice attached.");
        helper.addAttachment("invoice.pdf", new ByteArrayResource(pdfFile));

        mailSender.send(message);
    }
}