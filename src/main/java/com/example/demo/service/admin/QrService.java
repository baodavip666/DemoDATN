package com.example.demo.service.admin;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.dto.QrCodeRequest;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.repository.admin.HinhAnhRepository;
import com.example.demo.repository.admin.SanPhamChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@EnableAsync
@Service
public class QrService {

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;


    @Autowired
    private Cloudinary cloudinary;

    @Async
    public void updateQrCode(QrCodeRequest q){
        String base64Image = q.getImage().split(",")[1]; // Tách dữ liệu Base64
        try {
            // Chuyển đổi từ Base64 sang byte array
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // Upload lên Cloudinary
            File tempFile = File.createTempFile("qr_code_", ".png");
            tempFile.deleteOnExit(); // Đảm bảo file sẽ được xóa khi chương trình kết thúc

            // Ghi dữ liệu vào file
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(imageBytes);
            }
            Map<?, ?> uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap("resource_type", "image"));
            String imageUrl = (String) uploadResult.get("url");
            SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(q.getIdSpct()).get();
            sanPhamChiTiet.setQrImage(imageUrl);
            sanPhamChiTietRepository.save(sanPhamChiTiet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
