package com.example.demo.controller.user;

import com.example.demo.configuration.VNPayService;
import com.example.demo.dto.SanPhamThanhToan;
import com.example.demo.entity.*;
import com.example.demo.repository.admin.*;
import com.example.demo.repository.user.GioHangChiTietRepository;
import com.example.demo.service.admin.impl.EmailImpl;
import com.example.demo.service.admin.impl.GhnService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/user")
public class ThanhToanController {
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

    @Autowired
    VNPayService vnPayService;


    @GetMapping("/viewthanhtoan")
    public String viewThanhToan(Principal principal, Model model, @RequestParam List<Integer> idspct, @RequestParam List<Integer> soluong) {
        KhachHang kh = new KhachHang();
        DiaChi dc = new DiaChi();
        float khoiLuong = 0F;
        if (principal != null) {
            String username = principal.getName();
            kh = khachHangRepository.findByEmail(username).get();
            dc = diaChiRepository.findDiaChiDefaultByKhachHangId(kh.getId());
        }
        model.addAttribute("khachhang", kh);
        model.addAttribute("diachi", dc);
        Disctrict dsc = null;
        Ward wardcs = null;
        if (dc != null) {
            // Lấy thông tin tỉnh/thành phố từ GHN API dựa trên tên
            Province province = ghnService.getProvinceByName(dc.getTinhThanhPho());
            List<Disctrict> districts = new ArrayList<>();
            List<Ward> wards = new ArrayList<>();
            if (province != null) {
                // Lấy thông tin quận/huyện từ GHN API dựa trên tên và ID tỉnh/thành phố
                Disctrict district = ghnService.getDistrictByName(dc.getQuanHuyen(), province.getProvinceID());
                districts = ghnService.getDistricts(province.getProvinceID());
                dsc = district;
                model.addAttribute("selectedDistrict", district);
                if (district != null) {
                    // Lấy thông tin phường/xã từ GHN API dựa trên tên và ID quận/huyện
                    Ward ward = ghnService.getWardByName(dc.getPhuongXa(), district.getDistrictID());
                    wards = ghnService.getWards(district.getDistrictID());
                    wardcs = ward;
                    model.addAttribute("selectedWard", ward);
                }
            }

            List<Province> provinces = ghnService.getProvinces();
            model.addAttribute("provinces", provinces);
            model.addAttribute("districts", districts);
            model.addAttribute("wards", wards);
            model.addAttribute("selectedProvince", province);
        }

        List<SanPhamChiTiet> listspct = sanPhamChiTietRepository.findAllById(idspct);
        List<SanPhamThanhToan> sanPhamThanhToanList = new ArrayList<>();
        Double tongTien = 0D;
        for(int i = 0; i< listspct.size(); i++){
            tongTien += listspct.get(i).getDonGia() * soluong.get(i);
            khoiLuong += listspct.get(i).getKhoiLuong() * soluong.get(i);
            sanPhamThanhToanList.add(new SanPhamThanhToan(listspct.get(i), soluong.get(i) ));
        }
        model.addAttribute("listsptt", sanPhamThanhToanList);
        model.addAttribute("tongTien", tongTien);
        int canNang = 0;
        if(khoiLuong == (int) khoiLuong){
            canNang = (int)khoiLuong;
        }
        else{
            canNang = (int)khoiLuong + 1;
        }
        LocalDateTime now = LocalDateTime.now();
        List<PhieuGiamGia> phieuGiamGias = phieuGiamGiaRepository.findByDateAndTongTien(now, tongTien);
        int phiShip = 0;
        if(principal != null){
            List<PhieuGiamGia> phieuGiamGiasPrivate = khachHangPhieuGiamGiaRepository.findByDateAndTongTien(now, tongTien, kh.getId());
            phieuGiamGias.addAll(phieuGiamGiasPrivate);
            phiShip = ghnService.getShippingFee(dsc.getDistrictID(), wardcs.getWardCode(), canNang);
        }

        model.addAttribute("phieuGiamGia", phieuGiamGias);
        model.addAttribute("phiShip", phiShip);
        return "user/gio-hang/thanhtoan";
    }

    @PostMapping("/create")
    public String thanhToanPost(@RequestParam String idspcts, @RequestParam String soluongs, @RequestParam String hoTen,
                                @RequestParam String provinceId, @RequestParam String districtId, @RequestParam String wardCode,
                                @RequestParam String dccuthe, @RequestParam String dienthoai, @RequestParam String email,
                                @RequestParam Integer payment, @RequestParam(required = false) Integer chonKhuyenMai, Principal principal,
                                HttpSession session, RedirectAttributes redirectAttributes){
        KhachHang kh = null;
        if (principal != null) {
            String username = principal.getName();
            kh = khachHangRepository.findByEmail(username).get();
        }

        String[] arrIdSpct = idspcts.split(",");
        String[] arrSoLuong = soluongs.split(",");
        List<SanPhamThanhToan> listSpThanhToan= new ArrayList<SanPhamThanhToan>();
        Double tongTien = 0D;
        float khoiLuong = 0f;
        String chuoiidsp = "";
        String chuoisoluongsp = "";
        for(int i=0; i< arrIdSpct.length; i++){
            SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(Integer.valueOf(arrIdSpct[i])).get();
            int soLuong = Integer.valueOf(arrSoLuong[i]);
            listSpThanhToan.add(new SanPhamThanhToan(sanPhamChiTiet, soLuong));
            tongTien += soLuong * sanPhamChiTiet.getDonGia();
            khoiLuong += sanPhamChiTiet.getKhoiLuong() * soLuong;
            chuoiidsp += "&idspct="+arrIdSpct[i];
            chuoisoluongsp += "&soluong="+arrSoLuong[i];
        }
        String newString = chuoiidsp.substring(1);
        if (hoTen.trim().isEmpty()){
            redirectAttributes.addFlashAttribute("messageeror", "Thanh toán thất bại họ tên không được trống");
            return "redirect:/user/viewthanhtoan?"+newString + chuoisoluongsp;
        }
        if (hoTen.trim().length()>250){
            redirectAttributes.addFlashAttribute("messageeror", "Thanh toán thất bại họ tên quá dài");
            return "redirect:/user/viewthanhtoan?"+newString + chuoisoluongsp;
        }
        if (provinceId.trim().isEmpty() || districtId.trim().isEmpty() || wardCode.trim().isEmpty() || dccuthe.trim().isEmpty()){
            redirectAttributes.addFlashAttribute("messageeror", "Thanh toán thất bại địa chỉ không được trống ô nào");
            return "redirect:/user/viewthanhtoan?"+newString + chuoisoluongsp;
        }
        if (dccuthe.trim().length()>250){
            redirectAttributes.addFlashAttribute("messageeror", "Thanh toán thất bại địa chỉ cụ thể quá dài");
            return "redirect:/user/viewthanhtoan?"+newString + chuoisoluongsp;
        }
        if (dienthoai.trim().isEmpty()){
            redirectAttributes.addFlashAttribute("messageeror", "Thanh toán thất bại số điện thoại không được trống");
            return "redirect:/user/viewthanhtoan?"+newString + chuoisoluongsp;
        }
        if (dienthoai.length()>11){
            redirectAttributes.addFlashAttribute("messageeror", "Thanh toán thất bại số điện thoại quá dài");
            return "redirect:/user/viewthanhtoan?"+newString + chuoisoluongsp;
        }
        if (dienthoai.length()<9){
            redirectAttributes.addFlashAttribute("messageeror", "Thanh toán thất bại số điện thoại không hợp lệ");
            return "redirect:/user/viewthanhtoan?"+newString + chuoisoluongsp;
        }
        try {
            int sdt = Integer.parseInt(dienthoai);
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("messageeror", "Thanh toán thất bại số điện thoại không hợp lệ");
            return "redirect:/user/viewthanhtoan?"+newString + chuoisoluongsp;
        }
        if (!dienthoai.startsWith("0")){
            redirectAttributes.addFlashAttribute("messageeror", "Thanh toán thất bại số điện thoại phải bắt đầu bằng 0");
            return "redirect:/user/viewthanhtoan?"+newString + chuoisoluongsp;
        }
        if (email.trim().length()>250){
            redirectAttributes.addFlashAttribute("messageeror", "Thanh toán thất bại email quá dài");
            return "redirect:/user/viewthanhtoan?"+newString + chuoisoluongsp;
        }
        if (email.trim().isEmpty()){
            redirectAttributes.addFlashAttribute("messageeror", "Thanh toán thất bại email không được trống");
            return "redirect:/user/viewthanhtoan?"+newString + chuoisoluongsp;
        }

        int canNang = 0;
        if(khoiLuong == (int) khoiLuong){
            canNang = (int)khoiLuong;
        }
        else{
            canNang = (int)khoiLuong + 1;
        }

        Province province = ghnService.getProvinceByID(Integer.parseInt(provinceId));
        Disctrict disctrict = ghnService.getDistrictByID(Integer.parseInt(districtId), Integer.parseInt(provinceId));
        Ward ward = ghnService.getWardByID(wardCode, Integer.parseInt(districtId));
        PhieuGiamGia phieuGiamGia = null;
        double tientru = 0.0;
        if(chonKhuyenMai != null){
            phieuGiamGia = phieuGiamGiaRepository.findById(chonKhuyenMai).get();
            if(phieuGiamGia.getHinhThucGiam() == 0){
                tientru = tongTien - phieuGiamGia.getSoTienGiam();
                tongTien = tientru;
            }
            else{
                if(tongTien * phieuGiamGia.getPhanTramGiam() / 100 < phieuGiamGia.getGiamToiDa()){
                    tientru = tongTien - (tongTien * phieuGiamGia.getPhanTramGiam()/100);
                    tongTien = tientru;
                }
                else{
                    tientru = tongTien - phieuGiamGia.getGiamToiDa();
                    tongTien = tientru;
                }
            }
        }
        int phiShip = ghnService.getShippingFee(Integer.parseInt(districtId), wardCode, canNang);
        tongTien += phiShip;
        HoaDon hoaDon = new HoaDon();
        hoaDon.setTongTien(tongTien);
        hoaDon.setTrangThai("Chờ xác nhận");
        hoaDon.setLoaiHoaDon(1);
        hoaDon.setDiaChiCuThe(dccuthe);
        hoaDon.setNgayTao(new Date(System.currentTimeMillis()));
        hoaDon.setTinhThanhPho(province.getProvinceName());
        hoaDon.setQuanHuyen(disctrict.getDistrictName());
        hoaDon.setPhuongXa(ward.getWardName());
        hoaDon.setPhiShip(phiShip);
        hoaDon.setTenNguoiNhan(hoTen);
        hoaDon.setSoDienThoai(dienthoai);
        hoaDon.setGhiChu(email);
        hoaDon.setMa("HD"+System.currentTimeMillis());
        hoaDon.setPhieuGiamGia(phieuGiamGia);
        hoaDon.setKhachHang(kh);

        if (hoaDon.getTongTien()>1000000){
            redirectAttributes.addFlashAttribute("messageeror", "Thanh toán thất bại đơn hàng quá 1 triệu \n xin vui lòng thanh toán trước");
            return "redirect:/user/viewthanhtoan?"+newString + chuoisoluongsp;
        }

        if(payment == 0){
            hoaDonRepository.save(hoaDon);
            for(SanPhamThanhToan sptt : listSpThanhToan){
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setMa("HDCT"+System.currentTimeMillis());
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
            hinhThucThanhToan.setTen("Tiền mặt");
            hinhThucThanhToan.setNgayTao(new Date(System.currentTimeMillis()));
            hinhThucThanhToan.setDeleted(0);
            hinhThucThanhToan.setLoaiGiaoDich("Trả Sau");
            hinhThucThanhToanRepository.save(hinhThucThanhToan);
            HinhThucThanhToanHoaDon hinhThucThanhToanHoaDon = new HinhThucThanhToanHoaDon();
            hinhThucThanhToanHoaDon.setHoaDon(hoaDon);
            hinhThucThanhToanHoaDon.setHinhThucThanhToan(hinhThucThanhToan);
            hoaDonHinhThucThanhToanRepository.save(hinhThucThanhToanHoaDon);
        }
        // thanh toán online
        else{
            session.setAttribute("hoadon", hoaDon);
            session.setAttribute("listspThanhToan", listSpThanhToan);
            String url = vnPayService.createOrderClient(tongTien.intValue(), String.valueOf(System.currentTimeMillis()),"http://localhost:8080/thanh-cong");
            return "redirect:"+url;
        }

        return "user/gio-hang/thanhcong";
    }
}
