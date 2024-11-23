package com.example.demo.repository.admin;

import com.example.demo.entity.ChatLieu;
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
public interface ChatLieuRepository extends JpaRepository<ChatLieu, Integer> {

    @Query("SELECT cg FROM ChatLieu cg WHERE cg.deleted = 0 ORDER BY cg.ngayTao DESC")
    Page<ChatLieu> getAllActiveChatLieu(Pageable pageable);

    @Query("SELECT cg FROM ChatLieu cg WHERE cg.deleted = 0 ORDER BY cg.ngayTao DESC")
    List<ChatLieu> getListActiveChatLieus();

    @Query("SELECT sz FROM ChatLieu sz WHERE (sz.ma LIKE %:ma% OR sz.ten LIKE %:ma%) AND (sz.trangThai = :trangThai1 OR sz.trangThai = :trangThai2) AND sz.deleted = 0 ORDER BY sz.ngayTao DESC")
    Page<ChatLieu> listFilter(String ma, String trangThai1, String trangThai2, Pageable pageable);

    @Query("select ms from ChatLieu ms where " +
            "(ms.ten like %:search1% " +
            "or ms.ma like %:search1%) " +
            "and (:trangthai is null or ms.trangThai = :trangthai) ")
    Page<ChatLieu> searchChatLieu(@Param("search1") String search1,
                                  @Param("trangthai")String loai1,
                                  Pageable pageable);

}
