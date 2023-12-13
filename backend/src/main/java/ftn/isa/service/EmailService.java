package ftn.isa.service;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    public void send(String to, String body, String subject) throws MessagingException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setText(body, true);
            helper.setSubject(subject);

            mailSender.send(mimeMessage);
            System.out.println("Mail was sent successfully");
        }catch (Exception e){
            System.out.println("Exception: " + e.toString());
        }
    }

    public void sendWithQRCode(String to, String body, String subject, String qrCodeText) throws MessagingException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setText(body, true);
            helper.setSubject(subject);

            // Generate QR Code and attach to email
            byte[] qrCodeImage = generateQRCodeImage(qrCodeText, 300, 300);
            helper.addAttachment("qrcode.png", new ByteArrayResource(qrCodeImage));

            mailSender.send(mimeMessage);
            System.out.println("Mail with QR code was sent successfully!");
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
        }
    }
    public static byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Convert BufferedImage to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
