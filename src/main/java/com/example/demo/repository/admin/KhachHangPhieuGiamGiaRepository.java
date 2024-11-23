package com.example.demo.repository.admin;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.KhachHangPhieuGiamGia;
import com.example.demo.entity.PhieuGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface KhachHangPhieuGiamGiaRepository extends JpaRepository<KhachHangPhieuGiamGia,Integer> {
    @Query("select khpgg.giamGia from KhachHangPhieuGiamGia khpgg where khpgg.khachHang.id = :id")
    List<PhieuGiamGia> lstpggkh(Integer id);

    @Query("select khpgg.khachHang from KhachHangPhieuGiamGia khpgg where khpgg.giamGia.id = ?1")
    List<KhachHang> getKhachHangPhieuGiamGiaByid(Integer idpgg);

    @Query("select p.giamGia from KhachHangPhieuGiamGia p where p.khachHang.id = ?3 and p.giamGia.ngayKetThuc > ?1 and p.giamGia.ngayBatDau <= ?1 and p.giamGia.giaTriDonToiThieu <= ?2 and p.giamGia.hinhThucSuDung = 1 and p.giamGia.soLuong > 0")
    List<PhieuGiamGia> findByDateAndTongTien(LocalDateTime now, Double tongTien, Integer id);
}
