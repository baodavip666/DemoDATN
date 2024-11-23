package com.example.demo.api;

import com.example.demo.dto.GioHangRequest;
import com.example.demo.dto.GioHangResponse;
import com.example.demo.entity.GioHang;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.repository.admin.SanPhamChiTietRepository;
import com.example.demo.repository.user.GioHangChiTietRepository;
import com.example.demo.repository.user.GioHangRepository;
import com.example.demo.service.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gio-hang")
public class GioHangApi {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    private UserUtils userUtils;

    @PostMapping("/public/get-gio-hang")
    public ResponseEntity<?> getSanPhamChiTiet(@RequestBody List<GioHangRequest> list){
        List<GioHangResponse> result = new ArrayList<>();
        for(GioHangRequest g : list){
            SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(g.getIdSpct()).get();
            GioHangResponse response = new GioHangResponse();
            response.setSoLuong(g.getSoLuong());
            response.setSanPhamChiTiet(sanPhamChiTiet);
            result.add(response);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/public/them-gio-hang")
    public ResponseEntity<?> addGioHang(@RequestBody List<GioHangRequest> list){
        KhachHang khachHang = userUtils.getKhachHangWithAuthority();
        if(khachHang == null){
            throw new RuntimeException("Chưa đăng nhập");
        }
        Optional<GioHang> gioHang = gioHangRepository.findByKhachHang(khachHang.getId());
        GioHang ex = null;
        if(gioHang.isEmpty()){
            GioHang g = new GioHang();
            g.setKhachHang(khachHang);
            ex = gioHangRepository.save(g);
        }
        else{
            ex = gioHang.get();
        }
        for(GioHangRequest g : list){
            GioHangChiTiet ghct = gioHangChiTietRepository.findByGioHangAndCtsp(ex.getId(), g.getIdSpct());
            SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(g.getIdSpct()).get();
            if(ghct != null){
                continue;
            }
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setGioHang(ex);
            gioHangChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
            gioHangChiTiet.setNgayTao(LocalDateTime.now());
            gioHangChiTiet.setSoLuong(g.getSoLuong());
            gioHangChiTietRepository.save(gioHangChiTiet);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
