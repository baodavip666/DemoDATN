package com.example.demo.controller.user;

import com.example.demo.entity.*;
import com.example.demo.repository.admin.*;
import com.example.demo.repository.user.DonHangRepository;
import com.example.demo.service.admin.EmailService;
import com.example.demo.service.admin.impl.GhnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("user")
public class DonHangController {

    @Autowired
    DonHangRepository donHangRepository;

    @Autowired
    HoaDonRepository hoaDonRepository;
    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;
    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;
    @Autowired
    PhieuGiamGiaRepository phieuGiamGiaRepository;
    @Autowired
    KhachHangRepository khachHangRepository;
    @Autowired
    NhanVienRepository nhanVienRepository;
    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;
    @Autowired
    HoaDonHinhThucThanhToanRepository hoaDonHinhThucThanhToanRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    HinhAnhRepository hinhAnhRepository;
    @Autowired
    private GhnService ghnService;

    @GetMapping(value = "/donhang")
    public String viewDonHang(){
        return "user/don-hang/donhang";
    }
    @GetMapping(value="/donhang/timkiem")
    public String hienThitimkiem(Model model, @RequestParam(defaultValue = "") String keyword) {
        List<HoaDon> hd = donHangRepository.listFilter(keyword.trim());
        model.addAttribute("hoadon", hd);
        return "user/don-hang/donhangsearch";
    }

    @GetMapping("/donhang/donhangview/{id}")
    public String viewhoadon(Model model, @PathVariable("id") Integer id, @RequestParam(name = "page") Optional<Integer> page) {
        HoaDon hd = hoaDonRepository.getReferenceById(id);
        model.addAttribute("hoadon", hd);
        List<LichSuHoaDon> lichSuHoaDonList = lichSuHoaDonRepository.findAllByHoaDon_Id(id);
        List<HoaDonChiTiet> lsthdct = hoaDonChiTietRepository.findallbyhoadon(id);
        double totalAmount = lsthdct.stream()
                .mapToDouble(item -> item.getDonGia()).sum();
        PhieuGiamGia pgg = hd.getPhieuGiamGia();
        double giaTriGiam = 0;
        if (pgg != null) {
            if (pgg.getHinhThucGiam() == 0) {
                // Giảm theo số tiền cố định
                giaTriGiam = pgg.getSoTienGiam();
                if(giaTriGiam>totalAmount){
                    giaTriGiam = totalAmount;
                }
            } else {
                // Giảm theo phần trăm, kiểm tra giảm tối đa
                double phanTramGiam = totalAmount * (pgg.getPhanTramGiam() / 100);
                giaTriGiam = Math.min(phanTramGiam, pgg.getGiamToiDa());
            }
        }
        model.addAttribute("giamgia", giaTriGiam);
        List<HinhThucThanhToanHoaDon> hinhThucThanhToanHoaDonList = hoaDonHinhThucThanhToanRepository.findAllByHoaDon_Id(id);
        int checkpage = page.orElse(0);
        int pagesize = 3;
        checkpage = Math.max(checkpage, 0);
        Pageable pageable = PageRequest.of(checkpage, pagesize);
        Page<HoaDonChiTiet> hdct = hoaDonChiTietRepository.findAllByHoaDon_Id(id, pageable);
        List<HoaDonChiTiet> hdctlist = hoaDonChiTietRepository.findallbyhoadon(id);
        model.addAttribute("totalAmount", totalAmount);
        Map<Integer, String> hinhAnhMap = new HashMap<>();
        for (HoaDonChiTiet chiTiet : hdctlist) {
            List<String> hinhanhht = hinhAnhRepository.hinhAnhDuongDan(chiTiet.getSanPhamChiTiet().getId());
            if (!hinhanhht.isEmpty()) {
                hinhAnhMap.put(chiTiet.getSanPhamChiTiet().getId(), hinhanhht.get(0)); // Lấy ảnh đầu tiên
            } else {
                hinhAnhMap.put(chiTiet.getSanPhamChiTiet().getId(), "default-image.jpg"); // Ảnh mặc định nếu không có
            }
        }
        model.addAttribute("hinhAnhMap", hinhAnhMap);
        model.addAttribute("hoadonchitiet", hdct);
        model.addAttribute("hoadonchitietlist", hdctlist);
        model.addAttribute("lichsuhoadon", lichSuHoaDonList);
        if (lichSuHoaDonList.size()>0){
            model.addAttribute("lichsuhoadonphantucuoihanhdong", lichSuHoaDonList.get(lichSuHoaDonList.size()-1).getHanhDong());
        }
        model.addAttribute("lichsuhoadonfull", lichSuHoaDonRepository.findAllByHoaDon_Id1(id));
        model.addAttribute("hinhThucThanhToanHoaDonList", hinhThucThanhToanHoaDonList);

        if (hd != null) {
            // Lấy thông tin tỉnh/thành phố từ GHN API dựa trên tên
            Province province = ghnService.getProvinceByName(hd.getTinhThanhPho());
            List<Disctrict> districts = new ArrayList<>();
            List<Ward> wards = new ArrayList<>();
            if (province != null) {
                // Lấy thông tin quận/huyện từ GHN API dựa trên tên và ID tỉnh/thành phố
                Disctrict district = ghnService.getDistrictByName(hd.getQuanHuyen(), province.getProvinceID());
                districts = ghnService.getDistricts(province.getProvinceID());
                model.addAttribute("selectedDistrict", district);
                if (district != null) {
                    // Lấy thông tin phường/xã từ GHN API dựa trên tên và ID quận/huyện
                    Ward ward = ghnService.getWardByName(hd.getPhuongXa(), district.getDistrictID());
                    wards = ghnService.getWards(district.getDistrictID());
                    model.addAttribute("selectedWard", ward);
                }
            }

            List<Province> provinces = ghnService.getProvinces();
            model.addAttribute("provinces", provinces);
            model.addAttribute("districts", districts);
            model.addAttribute("wards", wards);
            model.addAttribute("selectedProvince", province);
        }


//        model.addAttribute("vouchergiam",vouchergiam);
//        model.addAttribute()
        return "user/don-hang/donhangview";
    }

}
