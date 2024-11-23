package com.example.demo.dto;

import com.example.demo.entity.SanPhamChiTiet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GioHangResponse {

    private Integer soLuong;

    private SanPhamChiTiet sanPhamChiTiet;
}
