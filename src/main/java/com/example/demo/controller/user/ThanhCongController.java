package com.example.demo.controller.user;

import com.example.demo.configuration.VNPayService;
import com.example.demo.dto.SanPhamThanhToan;
import com.example.demo.entity.*;
import com.example.demo.repository.admin.*;
import com.example.demo.repository.user.GioHangChiTietRepository;
import com.example.demo.service.admin.impl.GhnService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

@Controller
public class ThanhCongController {

    @Autowired
    VNPayService vnPayService;

    @Autowired
    HoaDonRepository hoaDonRepository;
    @Autowired
    KhachHangRepository khachHangRepository;
    @Autowired
    DiaChiRepository diaChiRepository;
    @Autowired
    GhnService ghnService;
    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    PhieuGiamGiaRepository phieuGiamGiaRepository;

    @Autowired
    KhachHangPhieuGiamGiaRepository khachHangPhieuGiamGiaRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;

    @Autowired
    HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Autowired
    HoaDonHinhThucThanhToanRepository hoaDonHinhThucThanhToanRepository;

    @GetMapping("/thanh-cong")
    public String thanhCong(HttpServletRequest request, Model model, HttpSession session) throws Exception{
        int check = vnPayService.orderReturn(request);
        Long count = hinhThucThanhToanRepository.findByVnpOrderInfo(request.getParameter("vnp_OrderInfo"));
        if(count > 0 || check == 0){
            model.addAttribute("warning", 0);
            return "user/thanhcong";
        }
        else if(check == 1){
            model.addAttribute("success", 1);
            HoaDon hoaDon = (HoaDon) session.getAttribute("hoadon");
            List<SanPhamThanhToan> listspThanhToan = (List<SanPhamThanhToan>) session.getAttribute("listspThanhToan");
            KhachHang kh = hoaDon.getKhachHang();
            PhieuGiamGia phieuGiamGia = hoaDon.getPhieuGiamGia();
            hoaDonRepository.save(hoaDon);
            Double tongTien = 0D;
            for(SanPhamThanhToan sptt : listspThanhToan){
                tongTien += sptt.getSoLuong() * sptt.getSanPhamChiTiet().getDonGia();
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(hoaDon);
                hoaDonChiTiet.setDonGia(sptt.getSanPhamChiTiet().getDonGia() * sptt.getSoLuong());
                hoaDonChiTiet.setSoLuong(sptt.getSoLuong());
                hoaDonChiTiet.setSanPhamChiTiet(sptt.getSanPhamChiTiet());
                hoaDonChiTiet.setNgayTao(new Date(System.currentTimeMillis()));
                if(kh != null){
                    hoaDonChiTiet.setNguoiTao(kh.getEmail());
                }
                sptt.getSanPhamChiTiet().setSoLuong(sptt.getSanPhamChiTiet().getSoLuong() - sptt.getSoLuong());
                sanPhamChiTietRepository.save(sptt.getSanPhamChiTiet());
                hoaDonChiTietRepository.save(hoaDonChiTiet);

                if(kh != null){
                    gioHangChiTietRepository.deleteByKhachHang(kh.getId(), sptt.getSanPhamChiTiet().getId());
                }


            }
            if(phieuGiamGia != null){
                phieuGiamGia.setSoLuong(phieuGiamGia.getSoLuong() - 1);
                phieuGiamGiaRepository.save(phieuGiamGia);
            }
            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(hoaDon);
            lichSuHoaDon.setNgayTao(new Date(System.currentTimeMillis()));
            lichSuHoaDon.setKhachHang(kh);
            lichSuHoaDon.setHanhDong("Tạo mới");
            lichSuHoaDon.setDeleted(0);
            lichSuHoaDonRepository.save(lichSuHoaDon);
            HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
            hinhThucThanhToan.setSoTienThanhToan(tongTien);
            hinhThucThanhToan.setTen("Vnpay");
            hinhThucThanhToan.setNgayTao(new Date(System.currentTimeMillis()));
            hinhThucThanhToan.setDeleted(0);
            hinhThucThanhToan.setLoaiGiaoDich("Trả Trước");
            hinhThucThanhToan.setVnpOrderInfo(request.getParameter("vnp_OrderInfo"));
            hinhThucThanhToanRepository.save(hinhThucThanhToan);
            HinhThucThanhToanHoaDon hinhThucThanhToanHoaDon = new HinhThucThanhToanHoaDon();
            hinhThucThanhToanHoaDon.setHoaDon(hoaDon);
            hinhThucThanhToanHoaDon.setHinhThucThanhToan(hinhThucThanhToan);
            hoaDonHinhThucThanhToanRepository.save(hinhThucThanhToanHoaDon);

        }
        else if(check == -1){
            model.addAttribute("error", 0);
        }
        return "user/gio-hang/thanhcong";
    }
}
