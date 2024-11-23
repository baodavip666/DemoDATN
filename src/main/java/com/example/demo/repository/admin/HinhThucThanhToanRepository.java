package com.example.demo.repository.admin;
import com.example.demo.entity.HinhThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HinhThucThanhToanRepository extends JpaRepository<HinhThucThanhToan,Integer> {

    @Query("select count(h.id) from HinhThucThanhToan h where h.vnpOrderInfo = ?1")
    Long findByVnpOrderInfo(String vnp_orderInfo);
}
