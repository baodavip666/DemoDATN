package com.example.demo.api;

import com.example.demo.dto.SanPhamThanhToan;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.repository.admin.SanPhamChiTietRepository;
import com.example.demo.service.admin.impl.GhnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shipping")
public class ShippingApi {

    @Autowired
    GhnService ghnService;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @RequestMapping("/calculate")
    public Integer calculateShippingCost(@RequestParam Integer districtId, @RequestParam String wardCode, @RequestParam List<Integer> idspct, @RequestParam List<Integer> soluong) {
        List<SanPhamChiTiet> listspct = sanPhamChiTietRepository.findAllById(idspct);
        float khoiLuong = 0F;
        for(int i = 0; i< listspct.size(); i++){
            khoiLuong += listspct.get(i).getKhoiLuong() * soluong.get(i);
        }
        int canNang = 0;
        if(khoiLuong == (int) khoiLuong){
            canNang = (int)khoiLuong;
        }
        else{
            canNang = (int)khoiLuong + 1;
        }
        int phiShip = ghnService.getShippingFee(districtId, wardCode, canNang);
        return phiShip;
    }
}
