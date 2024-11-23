package com.example.demo.repository.admin;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @Query("select hd from HoaDon hd where " +
            "(hd.ma like %:search1% " +
            "or hd.nhanVien.ten like %:search1% " +
            "or hd.khachHang.ten like %:search1% " +
            "or hd.soDienThoai like %:search1%) " +
            "and (:loai1 is null or hd.loaiHoaDon = :loai1) " +
            "and (hd.trangThai like %:trangthaihd%) " +
            "and hd.ngayTao between :date1 and :date2")
    Page<HoaDon> searchHoaDon(String search1,
                                Integer loai1,
                                String trangthaihd,
                                Date date1,
                                Date date2,Pageable pageable);
    @Query(value = "select * from hoa_don where loai_hoa_don = 1 and trang_thai like N'%Tạo mới%' order by ngay_tao\n",nativeQuery = true)
    List<HoaDon> listhoadonbh();

    @Query(value = "select * from hoa_don where trang_thai not like N'%Tạo mới%' order by ngay_tao\n",nativeQuery = true)
    Page<HoaDon> listkhongtaomoi(Pageable page);

    @Query("select hd.ma from HoaDon hd order by hd.ma desc")
    List<String> listma();

    //Thống kê topkh
    @Query(value = "SELECT kh.ten, COUNT(hd.id) AS purchaseCount, SUM(hd.tong_tien) AS totalAmount " +
            "FROM hoa_don hd " +
            "INNER JOIN khach_hang kh ON hd.id_khach_hang = kh.id " +
            "WHERE hd.trang_thai = N'Nhận hàng thành công' " +
            "GROUP BY kh.ten " +
            "ORDER BY totalAmount DESC",
            nativeQuery = true)
    List<Object[]> findTopCustomersByPurchases();

    @Query(value = "select SUM(tong_tien) from hoa_don " +
            "where trang_thai = N'Nhận hàng thành công'", nativeQuery = true)
    Double findTotalRevenue();

    @Query(value = "select SUM(tong_tien) from hoa_don  " +
            "WHERE trang_thai = N'Nhận hàng thành công' " +
            "AND ngay_tao BETWEEN :startOfDay AND :endOfDay",
            nativeQuery = true)
    Double getTodayRevenue(LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query(value = "select count(h.id) from hoa_don h where h.trang_thai = N'Nhận hàng thành công' and Month(h.ngay_tao) = ?1 and year(h.ngay_tao) =?2 ", nativeQuery = true)
    Long thongKeDonHangNam(int i, Integer nam);

    @Query(value = "select count(h.id) from hoa_don h where CONVERT(DATE, h.ngay_tao) = CONVERT(DATE, ?1) and h.trang_thai = N'Nhận hàng thành công'", nativeQuery = true)
    Long thongKeTheoNgay(Date date);

    @Query(value = "SELECT TOP 3 MAX(h.ten_nguoi_nhan) AS nguoiNhan, MAX(h.tong_tien) AS tongTien,\n" +
            "(select count(hd.id) from hoa_don hd where hd.id_khach_hang = h.id_khach_hang) as soLuongDon\n" +
            "FROM hoa_don h\n" +
            "GROUP BY h.id_khach_hang\n" +
            "ORDER BY MAX(h.id) DESC;",nativeQuery = true)
    public List<CustomerDto> top3Customer();

    @Query(value = "select sum(h.tong_tien) from hoa_don h where h.trang_thai = N'Nhận hàng thành công' and Month(h.ngay_tao) = ?1 and year(h.ngay_tao) =?2 ", nativeQuery = true)
    public Long doanhThuNam(Integer thang, Integer nam);

    @Query(value = "select count(h.id) from hoa_don h where h.trang_thai = N'Hủy Đơn Hàng'",nativeQuery = true)
    int tongDonHuy();

    @Query(value = "select count(h.id) from hoa_don h where h.trang_thai = N'Nhận hàng thành công'",nativeQuery = true)
    int tongDonNhan();
}
