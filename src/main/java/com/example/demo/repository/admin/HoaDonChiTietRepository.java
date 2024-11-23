package com.example.demo.repository.admin;

import com.example.demo.entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet,Integer> {

    Page<HoaDonChiTiet> findAllByHoaDon_Id(Integer idhoadon, Pageable pageable);
    @Query("select hdct from HoaDonChiTiet hdct where hdct.hoaDon.id =?1")
    List<HoaDonChiTiet> findallbyhoadon(Integer idhoadon);
    @Query("select hdct.ma from HoaDonChiTiet hdct order by hdct.ma desc")
    List<String> listma();

    //Thống kê
    @Query(value = "SELECT TOP 5 sp.ten, SUM(hdt.so_luong) AS totalQuantity " +
            "FROM hoa_don_chi_tiet hdt " +
            "INNER JOIN san_pham_chi_tiet spct ON hdt.id_san_pham_chi_tiet = spct.id " +
            "INNER JOIN san_pham sp ON spct.id_san_pham = sp.id " +
            "INNER JOIN hoa_don h ON h.id = hdt.id_hoa_don " +
            "WHERE h.trang_thai = N'Nhận hàng thành công' " +
            "AND MONTH(h.ngay_tao) = MONTH(GETDATE()) " +
            "AND YEAR(h.ngay_tao) = YEAR(GETDATE()) " +
            "GROUP BY sp.ten " +
            "ORDER BY totalQuantity DESC",
            nativeQuery = true)
    List<Object[]> findTopSellingProducts();

    @Query(value = "select sum(hc.so_luong) from hoa_don_chi_tiet hc inner join hoa_don h on h.id = hc.id_hoa_don " +
            "where h.trang_thai = N'Nhận hàng thành công'", nativeQuery = true)
    Integer findTotalQuantity();


    @Query("SELECT hdct FROM HoaDonChiTiet hdct WHERE hdct.ngayTao BETWEEN :startOfYear AND :endOfYear")
    List<HoaDonChiTiet> findAllInYear(@Param("startOfYear") Date startOfYear, @Param("endOfYear") Date endOfYear);

    @Query("SELECT FUNCTION('MONTH', hdct.ngayTao) AS thang, " +
            "COUNT(DISTINCT hd.id) AS soHoaDon, " +
            "SUM(hdct.soLuong) AS tongSoLuongSanPham " +
            "FROM HoaDonChiTiet hdct " +
            "LEFT JOIN hdct.hoaDon hd " +
            "WHERE FUNCTION('YEAR', hdct.ngayTao) = FUNCTION('YEAR', CURRENT_DATE) " +
            "GROUP BY FUNCTION('MONTH', hdct.ngayTao) " +
            "ORDER BY FUNCTION('MONTH', hdct.ngayTao)")
    List<Object[]> thongKeTheoThang();


    //so sánh
    @Query("SELECT SUM(hdct.soLuong * hdct.donGia) FROM HoaDonChiTiet hdct WHERE CAST(hdct.ngayTao AS date) = CAST(:currentDate AS date)")
    Double getTotalRevenueByDate(Date currentDate);

    @Query("SELECT SUM(hdct.soLuong * hdct.donGia) FROM HoaDonChiTiet hdct WHERE CAST(hdct.ngayTao AS date) = CAST(:previousDate AS date)")
    Double getTotalRevenueByPreviousDate(Date previousDate);

    @Query("select count(h.id) from HoaDonChiTiet h where h.sanPhamChiTiet.id = ?1")
    Long countByIdSpct(Integer id);

    @Query("select count(h.id) from HoaDonChiTiet h where h.sanPhamChiTiet.id in " +
            "(select spct.id from SanPhamChiTiet spct join SanPham sp on sp.id = spct.id where sp.id = ?1)")
    Long countByIdSP(Integer id);

    @Query("select hdct from HoaDonChiTiet hdct where hdct.sanPhamChiTiet.id =?1")
    List<HoaDonChiTiet> getHDCTbyIDSPCT(Integer id);


    @Query(value = "select sum(hc.so_luong) from hoa_don_chi_tiet hc inner join hoa_don h on h.id = hc.id_hoa_don " +
            "where h.trang_thai = N'Nhận hàng thành công' and Month(h.ngay_tao) = ?1 and year(h.ngay_tao) =?2", nativeQuery = true)
    Long thongKeDonHangNam(int i, Integer nam);

    @Query(value = "select sum(hc.so_luong) from hoa_don_chi_tiet hc inner join hoa_don h on h.id = hc.id_hoa_don" +
            " where CONVERT(DATE, h.ngay_tao) = CONVERT(DATE, ?1) and h.trang_thai = N'Nhận hàng thành công'", nativeQuery = true)
    Long thongKeTheoNgay(Date date);


}
