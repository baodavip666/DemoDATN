package com.example.demo.repository.admin;

import com.example.demo.entity.CoGiay;
import com.example.demo.entity.XuatXu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XuatXuRepository extends JpaRepository<XuatXu, Integer> {
    @Query("SELECT cg FROM XuatXu cg WHERE cg.deleted = 0 ORDER BY cg.ngayTao DESC")
    Page<XuatXu> findAllActiveXuatXus(Pageable pageable);

    @Query("SELECT cg FROM XuatXu cg WHERE cg.deleted = 0 ORDER BY cg.ngayTao DESC")
    List<XuatXu> getListActiveXuatXus();

    @Query("SELECT th FROM XuatXu th WHERE (th.ma LIKE %:ma% OR th.ten LIKE %:ma%) AND (th.trangThai = :trangThai1 OR th.trangThai = :trangThai2) AND th.deleted = 0 ORDER BY th.ngayTao DESC")
    Page<XuatXu> listFilter(String ma, String trangThai1, String trangThai2, Pageable pageable);

    @Query("select ms from XuatXu ms where " +
            "(ms.ten like %:search1% " +
            "or ms.ma like %:search1%) " +
            "and (:trangthai is null or ms.trangThai = :trangthai) ")
    Page<XuatXu> searchXuatXu(@Param("search1") String search1,
                              @Param("trangthai")String loai1,
                              Pageable pageable);
}
