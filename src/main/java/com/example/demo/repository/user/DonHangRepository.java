package com.example.demo.repository.user;


import com.example.demo.entity.HoaDon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface DonHangRepository extends JpaRepository<HoaDon, Integer> {
    @Query("SELECT hd FROM HoaDon hd WHERE (hd.soDienThoai LIKE %:keyword% OR hd.ma LIKE %:keyword%) AND hd.loaiHoaDon=0 AND hd.tongTien>0 ORDER BY hd.ngayTao DESC")
    List<HoaDon> listFilter(@Param("keyword") String keyword);

}
