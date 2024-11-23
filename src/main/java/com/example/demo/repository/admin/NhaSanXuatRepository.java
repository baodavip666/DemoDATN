package com.example.demo.repository.admin;

import com.example.demo.entity.CoGiay;
import com.example.demo.entity.NhaSanXuat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhaSanXuatRepository extends JpaRepository<NhaSanXuat, Integer> {

    @Query("SELECT cg FROM NhaSanXuat cg WHERE cg.deleted = 0 ORDER BY cg.ngayTao DESC")
    Page<NhaSanXuat> findAllActiveNhaSanXuats(Pageable pageable);

    @Query("SELECT cg FROM NhaSanXuat cg WHERE cg.deleted = 0 ORDER BY cg.ngayTao DESC")
    List<NhaSanXuat> getListActiveNhaSanXuats();

    @Query("SELECT ms FROM NhaSanXuat ms WHERE (ms.ma LIKE %:ma% OR ms.ten LIKE %:ma%) AND (ms.trangThai = :trangThai1 OR ms.trangThai = :trangThai2) AND ms.deleted = 0 ORDER BY ms.ngayTao DESC")
    Page<NhaSanXuat> listFilter(String ma, String trangThai1, String trangThai2, Pageable pageable);

    @Query("select ms from NhaSanXuat ms where " +
            "(ms.ten like %:search1% " +
            "or ms.ma like %:search1%) " +
            "and (:trangthai is null or ms.trangThai = :trangthai) ")
    Page<NhaSanXuat> searchNhaSanXuat(@Param("search1") String search1,
                                      @Param("trangthai")String loai1,
                                      Pageable pageable);

}
