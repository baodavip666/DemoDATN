package com.example.demo.repository.admin;

import com.example.demo.entity.CoGiay;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Integer> {

    @Query("SELECT th FROM ThuongHieu th WHERE th.deleted = 0 ORDER BY th.ngayTao DESC")
    Page<ThuongHieu> findAllActiveThuongHieu(Pageable pageable);

    @Query("SELECT th FROM MauSac th WHERE (th.ma LIKE %:ma% OR th.ten LIKE %:ma%) AND (th.trangThai = :trangThai1 OR th.trangThai = :trangThai2) AND th.deleted = 0 ORDER BY th.ngayTao DESC")
    Page<MauSac> listFilter(String ma, String trangThai1, String trangThai2, Pageable pageable);

    @Query("SELECT cg FROM ThuongHieu cg WHERE cg.deleted = 0 ORDER BY cg.ngayTao DESC")
    List<ThuongHieu> getListActiveThuongHieu();

    @Query("select ms from ThuongHieu ms where " +
            "(ms.ten like %:search1% " +
            "or ms.ma like %:search1%) " +
            "and (:trangthai is null or ms.trangThai = :trangthai) ")
    Page<ThuongHieu> searchThuongHieu(@Param("search1") String search1,
                                      @Param("trangthai")String loai1,
                                      Pageable pageable);
}
