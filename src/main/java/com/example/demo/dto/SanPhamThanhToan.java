package com.example.demo.dto;

import com.example.demo.entity.SanPhamChiTiet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamThanhToan {

    private SanPhamChiTiet sanPhamChiTiet;

    private Integer soLuong;
}
