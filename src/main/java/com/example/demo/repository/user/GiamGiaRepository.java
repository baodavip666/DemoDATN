package com.example.demo.repository.user;

import com.example.demo.entity.DeGiay;
import com.example.demo.entity.PhieuGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GiamGiaRepository extends JpaRepository<PhieuGiamGia,Integer> {
    @Query(value = "select * from phieu_giam_gia gg where " +
            "(gg.ma like %:search1% " +
            "or gg.ten like %:search1%) and gg.hinh_thuc_su_dung=0 and gg.trang_thai like N'Đang diễn ra' ",nativeQuery = true)
    List<PhieuGiamGia> searchGG(@Param("search1") String search1);

    @Query( value = "select * from phieu_giam_gia gg where gg.hinh_thuc_su_dung=0 and gg.trang_thai like N'Đang diễn ra'", nativeQuery = true)
    List<PhieuGiamGia> findALLPGG();


}
