package com.example.demo.repository.user;

import com.example.demo.entity.SanPham;
import com.example.demo.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HomeRepository extends JpaRepository<SanPhamChiTiet, Integer> {
    @Query("select sp from SanPham sp where sp.ten like ?1  ")
    List<SanPham> search(String nameSearch);

    @Query("SELECT spct FROM SanPhamChiTiet spct WHERE spct.donGia BETWEEN :minPrice AND :maxPrice")
    List<SanPhamChiTiet> findGia(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    @Query("SELECT sp FROM SanPham sp JOIN sp.danhMuc dm WHERE dm.id = 1")
    List<SanPham> findGiayNam();

    @Query("SELECT sp FROM SanPhamChiTiet sp where sp.sanPham.ten like %?4% and (?1 is null or sp.sanPham.danhMuc.id = ?1) and (?2 is null or sp.mauSac.id = ?2) and (?3 is null or sp.size.id = ?3) and sp.donGia >0 order by sp.donGia desc ")
    List<SanPhamChiTiet> findurl(Integer danhmuc,Integer mausac,Integer size,String tensp);
    @Query("SELECT sp FROM SanPhamChiTiet sp where sp.sanPham.ten like %?4% and (?1 is null or sp.sanPham.danhMuc.id = ?1) and (?2 is null or sp.mauSac.id = ?2) and (?3 is null or sp.size.id = ?3) and sp.donGia >0 order by sp.donGia asc ")
    List<SanPhamChiTiet> findurl1(Integer danhmuc,Integer mausac,Integer size,String tensp);
    @Query("SELECT sp FROM SanPham sp JOIN sp.danhMuc dm WHERE dm.id = 2")
    List<SanPham> findGiayNu();

    @Query("SELECT sp FROM SanPham sp JOIN sp.danhMuc dm WHERE dm.id = 3")
    List<SanPham> findGiayTreem();

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.sanPham.id = :sanPhamId and s.donGia>0")
    List<SanPhamChiTiet> filterSP(@Param("sanPhamId") Integer sanPhamId);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.size.id = :idsize and s.donGia>0")
    List<SanPhamChiTiet> findSanPhamChiTietsBySize(@Param("idsize") Integer idsize);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.mauSac.id = :idMauSac and s.donGia>0")
    List<SanPhamChiTiet> findSanPhamChiTietsByMauSac(@Param("idMauSac") Integer idMauSac);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.sanPham.id = :sanPhamId and s.donGia > 0 ORDER BY s.soLuong DESC")
    List<SanPhamChiTiet> findBySanPham(@Param("sanPhamId") Integer sanPhamId);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.id = :id and s.donGia > 0")
    SanPhamChiTiet findSanPhamChiTietById(@Param("id") Integer id);
}
