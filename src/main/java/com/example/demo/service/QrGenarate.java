package com.example.demo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.dto.QrCodeRequest;
import com.example.demo.entity.SanPhamChiTiet;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Map;

import com.google.zxing.client.j2se.MatrixToImageWriter;

@Service
@EnableAsync

public class QrGenarate {

    @Autowired
    private Cloudinary cloudinary;

    public String generateQRCodeImage(String text, int width, int height) throws WriterException, IOException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return saveByty(pngOutputStream.toByteArray());
    }
    public static void generateQRCodeImageToFile(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
    @Async
    public String saveByty(byte[] imageBytes){
        try {
            // Upload lên Cloudinary
            File tempFile = File.createTempFile("qr_code_", ".png");
            tempFile.deleteOnExit(); // Đảm bảo file sẽ được xóa khi chương trình kết thúc

            // Ghi dữ liệu vào file
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(imageBytes);
            }
            Map<?, ?> uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap("resource_type", "image"));
            String imageUrl = (String) uploadResult.get("url");
            return imageUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
