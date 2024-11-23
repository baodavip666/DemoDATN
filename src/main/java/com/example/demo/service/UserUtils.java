package com.example.demo.service;

import com.example.demo.entity.KhachHang;
import com.example.demo.repository.admin.KhachHangRepository;
import com.example.demo.repository.admin.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    public KhachHang getKhachHangWithAuthority() {
        // Lấy Authentication từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            System.out.println("usernaem: "+username);
            if(username.equalsIgnoreCase("anonymousUser")){
                return null;
            }
            KhachHang khachHang = khachHangRepository.findByEmail(username).get();
            return khachHang;
        }
        return null;
    }
}
