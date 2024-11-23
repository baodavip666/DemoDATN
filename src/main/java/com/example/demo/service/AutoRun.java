package com.example.demo.service;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.PhieuGiamGia;
import com.example.demo.repository.admin.KhachHangRepository;
import com.example.demo.repository.admin.PhieuGiamGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableAsync
@EnableScheduling
public class AutoRun {

    @Autowired
    PhieuGiamGiaRepository phieuGiamGiaRepository;
    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Async
    @Scheduled(fixedDelay = 1000L*600L)
    public void updateTrangThaiPhieuGiamGia(){

        System.out.println("Kiểm tra cập nhật phiếu giảm giá");
        List<PhieuGiamGia> lstpgg = phieuGiamGiaRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (PhieuGiamGia pgg: lstpgg
        ) {
            if(pgg.getBlock() != null){
                if(pgg.getBlock() == true){
                    continue;
                }
            }
            if (now.isBefore(pgg.getNgayBatDau())) {
                pgg.setTrangThai("Sắp diễn ra");
                phieuGiamGiaRepository.save(pgg);
                continue;
            } else if (now.isAfter(pgg.getNgayBatDau()) && now.isBefore(pgg.getNgayKetThuc())) {
                pgg.setTrangThai("Đang diễn ra");
                phieuGiamGiaRepository.save(pgg);
                continue;
            } else if (now.isAfter(pgg.getNgayKetThuc())) {
                pgg.setTrangThai("Kết thúc");
                phieuGiamGiaRepository.save(pgg);
                continue;
            }
        }
    }
}
