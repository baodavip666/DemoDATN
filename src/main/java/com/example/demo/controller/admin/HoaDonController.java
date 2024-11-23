package com.example.demo.controller.admin;


import com.example.demo.entity.*;
import com.example.demo.repository.admin.*;
import com.example.demo.service.admin.EmailService;
import com.example.demo.service.admin.impl.GhnService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.NumberFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/admin")
public class HoaDonController {
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

    @GetMapping("/hoadon/hien-thi")
    public String hienThiHoaDon(Model model, @RequestParam(name = "page") Optional<Integer> page) {
        int checkpage = page.orElse(0);
        int pagesize = 3;
        checkpage = Math.max(checkpage, 0);
        Pageable pageable = PageRequest.of(checkpage, pagesize);
        Page<HoaDon> hd = hoaDonRepository.listkhongtaomoi(pageable);
//        List<HoaDon> hd = hoaDonRepository.findAll();
        model.addAttribute("hoadon", hd);
        return "admin/hoa-don/hien-thi";
    }

    @GetMapping("/hoadon/timkiem")
    public String hienThitimkiem(Model model, @RequestParam(defaultValue = "") String search1
            , @RequestParam(defaultValue = "", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1
            , @RequestParam(defaultValue = "", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2
            , @RequestParam(required = false) Integer loaihoadontim
            , @RequestParam(defaultValue = "") String trangthaihd
            , @RequestParam(name = "page") Optional<Integer> page) {
        int checkpage = page.orElse(0);
        int pagesize = 3;
        checkpage = Math.max(checkpage, 0);
        Pageable pageable = PageRequest.of(checkpage, pagesize);
        Calendar calendar = Calendar.getInstance();
        // Nếu date2 không được cung cấp, đặt nó là ngày hiện tại
        if (date2 == null) {
            date2 = calendar.getTime();
        }

        // Nếu date1 không được cung cấp, đặt nó là ngày này của năm ngoái
        if (date1 == null) {
            calendar.add(Calendar.YEAR, -1);
            date1 = calendar.getTime();
        }
        Page<HoaDon> hd = hoaDonRepository.searchHoaDon(search1.trim(), loaihoadontim,trangthaihd, date1, date2, pageable);
//        List<HoaDon> hd = hoaDonRepository.findAll();
        model.addAttribute("hoadon", hd);
        return "admin/hoa-don/hien-thi";
    }

    @PostMapping("/hoadon/update/{id}")
    public String suahoadon(
             @PathVariable("id") Integer id
            , Model model
            , @RequestParam("provinceId") String provinceId
            , @RequestParam("districtId") String districtId
            , @RequestParam("wardCode") String wardCode
             ,@RequestParam("diaChiCuThe") String diaChiCuThe,
             @RequestParam("tenNguoiNhan") String tenNguoiNhan,
             @RequestParam("soDienThoai") String soDienThoai
            , @RequestParam(name = "page") Optional<Integer> page, RedirectAttributes redirectAttributes
            ) {
        List<HoaDonChiTiet> hdctlist = hoaDonChiTietRepository.findallbyhoadon(id);
        int tongkhoiluonghd = 0;
        for (int i = 0; i < hdctlist.size(); i++) {
            tongkhoiluonghd += (hdctlist.get(i).getSanPhamChiTiet().getKhoiLuong() * hdctlist.get(i).getSoLuong());
        }
        if (tenNguoiNhan.trim().isEmpty()||tenNguoiNhan.trim().equals("")){
            redirectAttributes.addFlashAttribute("messageeror", "Cập nhật địa chỉ thất bại,tên không được trống");
            return "redirect:/admin/hoadon/view-hoadon/" + id;
        }
        if (soDienThoai.trim().isEmpty()){
            redirectAttributes.addFlashAttribute("messageeror", "Cập nhật địa chỉ thất bại,SĐT không được trống");
            return "redirect:/admin/hoadon/view-hoadon/" + id;
        }
        if (soDienThoai.length()!=10){
            redirectAttributes.addFlashAttribute("messageeror", "Cập nhật địa chỉ thất bại,SĐT phải có 10 số không được trống");
            return "redirect:/admin/hoadon/view-hoadon/" + id;
        }
        if (diaChiCuThe.trim().isEmpty()){
            redirectAttributes.addFlashAttribute("messageeror", "Cập nhật địa chỉ thất bại,địa chỉ cụ thể không được trống");
            return "redirect:/admin/hoadon/view-hoadon/" + id;
        }
        if (provinceId.trim().isEmpty()){
            redirectAttributes.addFlashAttribute("messageeror", "Cập nhật địa chỉ thất bại,Tỉnh thành phố không được trống");
            return "redirect:/admin/hoadon/view-hoadon/" + id;
        }
        if (districtId.trim().isEmpty()){
            redirectAttributes.addFlashAttribute("messageeror", "Cập nhật địa chỉ thất bại,Quận huyện không được trống");
            return "redirect:/admin/hoadon/view-hoadon/" + id;
        }
        if (wardCode.trim().isEmpty()){
            redirectAttributes.addFlashAttribute("messageeror", "Cập nhật địa chỉ thất bại,Xã phường không được trống");
            return "redirect:/admin/hoadon/view-hoadon/" + id;
        }
        Province province = ghnService.getProvinceByID(Integer.parseInt(provinceId));
        Disctrict disctrict = ghnService.getDistrictByID(Integer.parseInt(districtId),Integer.parseInt(provinceId));
        Ward ward = ghnService.getWardByID(wardCode,Integer.parseInt(districtId));
        HoaDon hd = hoaDonRepository.getReferenceById(id);
        hd.setTenNguoiNhan(tenNguoiNhan);
        hd.setNgaySua(new Date());
        hd.setTinhThanhPho(province.getProvinceName());
        hd.setQuanHuyen(disctrict.getDistrictName());
        hd.setPhuongXa(ward.getWardName());
        hd.setPhiShip(ghnService.getShippingFee(Integer.parseInt(districtId),wardCode,tongkhoiluonghd));
        hoaDonRepository.save(hd);
        List<LichSuHoaDon> lichSuHoaDonList = lichSuHoaDonRepository.findAllByHoaDon_Id(id);
        model.addAttribute("lichsuhoadon", lichSuHoaDonList);
        redirectAttributes.addFlashAttribute("messagesucces", "Cập nhật địa chỉ thành công");
        return "redirect:/admin/hoadon/view-hoadon/" + id;
    }

    @ModelAttribute("khachhang")
    public List<KhachHang> getKh() {
        return khachHangRepository.findAll();
    }

    @ModelAttribute("phieugiamgia")
    public List<PhieuGiamGia> getph() {
        return phieuGiamGiaRepository.findAll();
    }

    @ModelAttribute("nhanvienlist")
    public List<NhanVien> getnv() {
        System.out.println("Danh sách nhân viên: " + nhanVienRepository.findAll());
        return nhanVienRepository.findAll();
    }
//    lấy tất cả tỉnh thành của api
    @GetMapping("/provinces")
    public String showProvinces(Model model) {
        List<Province> provinces = ghnService.getProvinces();
        model.addAttribute("provinces", provinces);
        return "diachi";
    }
//    lấy tất cả quận huyện của api

    @GetMapping("/districts")
    @ResponseBody
    public List<Disctrict> getDistricts(@RequestParam("provinceId") int provinceId) {
        return ghnService.getDistricts(provinceId);
    }

    @GetMapping("/wards")
    @ResponseBody
    public List<Ward> getWards(@RequestParam("districtId") int districtId) {
        return ghnService.getWards(districtId);
    }
//    lấy địa chỉ api

    @GetMapping("/hoadon/view-hoadon/{id}")
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
        return "admin/hoa-don/view-hoadon";
    }

    @PostMapping("/hoadon/addlshd/{id}")
    public String addlshoadon(Model model
            , @PathVariable("id") Integer id
            , @RequestParam(value = "nutbuttonls",required = false) String nutbutton
            , @RequestParam(value = "nuthuy",required = false) String nuthuy, Principal principal) throws MessagingException {

        HoaDon hd = hoaDonRepository.getReferenceById(id);
        List<LichSuHoaDon> lichSuHoaDonList = lichSuHoaDonRepository.findAllByHoaDon_Id(id);
        double giamGia = 0.0;
        if (hd.getPhieuGiamGia().getHinhThucGiam() == 0) {
            if (hd.getPhieuGiamGia().getGiaTriDonToiThieu() <= hd.getTongTien()) {
                giamGia = hd.getPhieuGiamGia().getGiaTriDonToiThieu();
            }
        } else if (hd.getPhieuGiamGia().getHinhThucGiam() == 1) {
            if (hd.getPhieuGiamGia().getGiaTriDonToiThieu() <= hd.getTongTien()) {
                giamGia = hd.getTongTien() * hd.getPhieuGiamGia().getPhanTramGiam() / 100;
                if (giamGia > hd.getPhieuGiamGia().getGiamToiDa()) {
                    giamGia = hd.getPhieuGiamGia().getGiamToiDa();
                }
            }
        }
        if ("Xác Nhận".equals(nutbutton)) {
            DataMailDTO dataMail = new DataMailDTO();
            dataMail.setTo("quannvph37802@fpt.edu.vn");
            dataMail.setSubject("[ChinShoes] Đơn hàng của bạn đã được tiếp nhận ✅");
            Map<String, Object> props = new HashMap<>();
            props.put("hoadon", hd);
            props.put("lichsuhoadonfull", lichSuHoaDonRepository.findAllByHoaDon_Id1(id));
            props.put("hoadonchitietlist", hoaDonChiTietRepository.findallbyhoadon(id));
            props.put("giamgia", giamGia);
            dataMail.setProps(props);
                emailService.sendHtmlMail(dataMail, "admin/hoa-don/email-template");
//                emailService.htmlToPdf(dataMail, "emailtemplate",xuatpdf);
        }
        LichSuHoaDon lshd = new LichSuHoaDon();
        if ("Hủy Đơn Hàng".equals(nuthuy)){
            lshd.setHanhDong(nuthuy);
            if (hd.getPhieuGiamGia()!=null){
                PhieuGiamGia pggd = hd.getPhieuGiamGia();
                pggd.setSoLuong(pggd.getSoLuong()+1);
                phieuGiamGiaRepository.save(pggd);
            }
            List<HoaDonChiTiet> hdctl = hoaDonChiTietRepository.findallbyhoadon(hd.getId());
            if(hdctl.size()>0){
                for (HoaDonChiTiet hdctc : hdctl) {
                    SanPhamChiTiet spctd = hdctc.getSanPhamChiTiet();
                    spctd.setSoLuong(spctd.getSoLuong()+hdctc.getSoLuong());
                    sanPhamChiTietRepository.save(spctd);
                }
            }
        }else{
            lshd.setHanhDong(nutbutton);
        }
        lshd.setHoaDon(hd);
        lshd.setNgayTao(new Date());
        String username = principal.getName();
        lshd.setNhanVien(nhanVienRepository.findByEmail(username).get());
        lshd.setKhachHang(hd.getKhachHang());
        lshd.setNguoiTao(nhanVienRepository.findByEmail(username).get().getTen());
        lshd.setDeleted(0);
        lichSuHoaDonRepository.save(lshd);
        hd.setTrangThai(lshd.getHanhDong());
        hoaDonRepository.save(hd);
        model.addAttribute("lichsuhoadon", lichSuHoaDonList);
        return "redirect:/admin/hoadon/view-hoadon/{id}";
    }

    @GetMapping("/hoadon/quaylai/{id}")
    public String removelshoadon(Model model, @PathVariable("id") Integer id, @ModelAttribute("lichsuhoadon") LichSuHoaDon lshd1) {
        List<LichSuHoaDon> llshd = lichSuHoaDonRepository.findAllByHoaDon_Id(id);
        LichSuHoaDon lshd = lichSuHoaDonRepository.getReferenceById(llshd.get(llshd.size() - 1).getId());
        lshd1 = lshd;
        lshd1.setDeleted(1);
        lshd1.setNgaySua(new Date());
        lichSuHoaDonRepository.save(lshd1);
        model.addAttribute("lichsuhoadon", llshd);
        return "redirect:/admin/hoadon/view-hoadon/{id}";
    }
    @GetMapping("/hoadon/xuatpdf/{id}")
    public String xuathoadon(@PathVariable("id") Integer id, Model model) {
        HoaDon hd = hoaDonRepository.getReferenceById(id);
        double giamGia = 0.0;
        if (hd.getPhieuGiamGia().getHinhThucGiam() == 0) {
            if (hd.getPhieuGiamGia().getGiaTriDonToiThieu() <= hd.getTongTien()) {
                giamGia = hd.getPhieuGiamGia().getGiaTriDonToiThieu();
            }
        } else if (hd.getPhieuGiamGia().getHinhThucGiam() == 1) {
            if (hd.getPhieuGiamGia().getGiaTriDonToiThieu() <= hd.getTongTien()) {
                giamGia = hd.getTongTien() * hd.getPhieuGiamGia().getPhanTramGiam() / 100;
                if (giamGia > hd.getPhieuGiamGia().getGiamToiDa()) {
                    giamGia = hd.getPhieuGiamGia().getGiamToiDa();
                }
            }
        }
        model.addAttribute("hoadon", hd);
        model.addAttribute("lichsuhoadonfull", lichSuHoaDonRepository.findAllByHoaDon_Id1(id));
        model.addAttribute("hoadonchitietlist", hoaDonChiTietRepository.findallbyhoadon(id));
        model.addAttribute("giamgia", giamGia);
        return "admin/hoa-don/email-template";
    }
    @GetMapping("/hoadon/dssanpham")
    public String hienThisanpham(Model model, @RequestParam(name = "page") Optional<Integer> page, @RequestParam(required = false) Integer id) {
        int checkpage = page.orElse(0);
        int pagesize = 5;
        checkpage = Math.max(checkpage, 0);
        Pageable pageable = PageRequest.of(checkpage, pagesize);
        Page<SanPhamChiTiet> sanPhamChiTiet = sanPhamChiTietRepository.pagespct(pageable);
        List<SanPhamChiTiet> sanpctl = sanPhamChiTietRepository.findAll();
        Map<Integer, String> hinhAnhMap = new HashMap<>();
        for (SanPhamChiTiet spchiTiet : sanpctl) {
            List<String> hinhanhht = hinhAnhRepository.hinhAnhDuongDan(spchiTiet.getId());
            if (!hinhanhht.isEmpty()) {
                hinhAnhMap.put(spchiTiet.getId(), hinhanhht.get(0)); // Lấy ảnh đầu tiên
            } else {
                hinhAnhMap.put(spchiTiet.getId(), "default-image.jpg"); // Ảnh mặc định nếu không có
            }
        }
        model.addAttribute("hinhAnhMap", hinhAnhMap);
        model.addAttribute("idhienthihd", id);
        model.addAttribute("spctl", sanPhamChiTiet);
        return "admin/hoa-don/add-san-pham";
    }
    public HoaDon setpgg(Integer id) {
        List<HoaDonChiTiet> lsthdct = hoaDonChiTietRepository.findallbyhoadon(id);
        HoaDon hoaDon = hoaDonRepository.getReferenceById(id);
        double totalAmount = lsthdct.stream()
                .mapToDouble(item -> item.getDonGia()).sum();
        List<PhieuGiamGia> lstpgg = phieuGiamGiaRepository.lstpgg();
        PhieuGiamGia pggMax = lstpgg.stream()
                .filter(pgg -> totalAmount >= pgg.getGiaTriDonToiThieu()) // Điều kiện tổng tiền > giá trị đơn tối thiểu
                .max((pgg1, pgg2) -> {
                    // Tính giá trị giảm của pgg1
                    double giaTriGiam1 = pgg1.getHinhThucGiam() == 0
                            ? pgg1.getSoTienGiam()
                            : Math.min(totalAmount * (pgg1.getPhanTramGiam() / 100), pgg1.getGiamToiDa());

                    double giaTriGiam2 = pgg2.getHinhThucGiam() == 0
                            ? pgg2.getSoTienGiam()
                            : Math.min(totalAmount * (pgg2.getPhanTramGiam() / 100), pgg2.getGiamToiDa());
                    // So sánh giá trị giảm
                    return Double.compare(giaTriGiam1, giaTriGiam2);
                }).orElse(null);
        if (pggMax != null) {
            if (pggMax != hoaDon.getPhieuGiamGia() && hoaDon.getPhieuGiamGia() != null) {
                PhieuGiamGia pgghd = hoaDon.getPhieuGiamGia();
                pgghd.setSoLuong(pgghd.getSoLuong() + 1);
                phieuGiamGiaRepository.save(pgghd);
                pggMax.setSoLuong(pggMax.getSoLuong() - 1);
                phieuGiamGiaRepository.save(pggMax);
                hoaDon.setPhieuGiamGia(pggMax);
            } else if (hoaDon.getPhieuGiamGia() == null) {
                pggMax.setSoLuong(pggMax.getSoLuong() - 1);
                phieuGiamGiaRepository.save(pggMax);
                hoaDon.setPhieuGiamGia(pggMax);
            } else if (hoaDon.getPhieuGiamGia() == pggMax) {
                hoaDon.setPhieuGiamGia(pggMax);
            }
            hoaDonRepository.save(hoaDon);
        }
        return hoaDon;
    }
    @PostMapping("/hoadon/updatesphdct")
    public String updateSphdct(@RequestParam("id") Integer id, @RequestParam(value = "soluongadd", required = false) String soluongadd) {
        soluongadd = soluongadd.trim().replaceAll("[^\\w\\s]", ""); // Loại bỏ tất cả ký tự đặc biệt ngoại trừ chữ cái, chữ số, và khoảng trắng
        HoaDonChiTiet hdct = hoaDonChiTietRepository.getReferenceById(id);
        if (soluongadd == null || soluongadd.trim().isEmpty() || soluongadd.trim().equals("0")) {
            soluongadd = "1";
        } else {
            try {
                // Thử chuyển đổi soluongadd sang số nguyên
                int value = Integer.parseInt(soluongadd.trim());

                // Kiểm tra nếu giá trị nhỏ hơn 0
                if (value < 0) {
                    soluongadd = "1";
                }
                // Kiểm tra nếu giá trị lớn hơn số lượng cho phép
                else if (value > hdct.getSanPhamChiTiet().getSoLuong() + hdct.getSoLuong()) {
                    soluongadd = String.valueOf(hdct.getSanPhamChiTiet().getSoLuong() + hdct.getSoLuong());
                } else {
                    // Nếu hợp lệ, gán lại giá trị số nguyên đã kiểm tra
                    soluongadd = String.valueOf(value);
                }
            } catch (NumberFormatException e) {
                // Trường hợp soluongadd không phải là số nguyên
                soluongadd = "1";
            }
        }
        SanPhamChiTiet spct = sanPhamChiTietRepository.getReferenceById(hdct.getSanPhamChiTiet().getId());
        if (Integer.parseInt(soluongadd) > hdct.getSoLuong()) {
            spct.setSoLuong(spct.getSoLuong() - (Integer.parseInt(soluongadd.trim()) - hdct.getSoLuong()));
            sanPhamChiTietRepository.save(spct);
            hdct.setSoLuong(Integer.parseInt(soluongadd));
            hdct.setDonGia(hdct.getSanPhamChiTiet().getDonGia() * hdct.getSoLuong());
            hoaDonChiTietRepository.save(hdct);
        } else {
            spct.setSoLuong(spct.getSoLuong() + (hdct.getSoLuong() - Integer.parseInt(soluongadd.trim())));
            sanPhamChiTietRepository.save(spct);
            hdct.setSoLuong(Integer.parseInt(soluongadd));
            hdct.setDonGia(hdct.getSanPhamChiTiet().getDonGia() * hdct.getSoLuong());
            hoaDonChiTietRepository.save(hdct);
        }
        setpgg(hdct.getHoaDon().getId());
        HoaDon hoaDon = hoaDonRepository.getReferenceById(hdct.getHoaDon().getId());
        List<HoaDonChiTiet> lsthdct = hoaDonChiTietRepository.findallbyhoadon(hdct.getHoaDon().getId());
        double totalAmount = lsthdct.stream()
                .mapToDouble(item -> item.getDonGia()).sum();
        Integer phiship = 0;
        PhieuGiamGia pgg = hoaDon.getPhieuGiamGia();
        double giaTriGiam = 0;
        if (pgg != null) {
            if (pgg.getHinhThucGiam() == 0) {
                // Giảm theo số tiền cố định
                giaTriGiam = pgg.getSoTienGiam();
            } else {
                // Giảm theo phần trăm, kiểm tra giảm tối đa
                double phanTramGiam = totalAmount * (pgg.getPhanTramGiam() / 100);
                giaTriGiam = Math.min(phanTramGiam, pgg.getGiamToiDa());
            }
        }
        if (hoaDon.getPhiShip() != null) {
            phiship = hoaDon.getPhiShip();
        }
        double tongThanhToan = totalAmount - giaTriGiam + phiship;
        hoaDon.setTongTien(tongThanhToan);
        hoaDonRepository.save(hoaDon);
        return "redirect:/admin/hoadon/view-hoadon/"+ hdct.getHoaDon().getId();

    }

    @GetMapping("/hoadon/deletehdct")
    public String deleteHoaDonChiTiet(@RequestParam("id") Integer id) {
        HoaDonChiTiet hdct = hoaDonChiTietRepository.getReferenceById(id);
        Integer idhd = hdct.getHoaDon().getId();
        SanPhamChiTiet spct = sanPhamChiTietRepository.getReferenceById(hdct.getSanPhamChiTiet().getId());
        spct.setSoLuong(spct.getSoLuong() + hdct.getSoLuong());
        sanPhamChiTietRepository.save(spct);
        hoaDonChiTietRepository.delete(hdct);
        setpgg(idhd);

        HoaDon hoaDon = hoaDonRepository.getReferenceById(hdct.getHoaDon().getId());
        List<HoaDonChiTiet> lsthdct = hoaDonChiTietRepository.findallbyhoadon(hdct.getHoaDon().getId());
        double totalAmount = lsthdct.stream()
                .mapToDouble(item -> item.getDonGia()).sum();
        Integer phiship = 0;
        PhieuGiamGia pgg = hoaDon.getPhieuGiamGia();
        double giaTriGiam = 0;
        if (pgg != null) {
            if (pgg.getHinhThucGiam() == 0) {
                // Giảm theo số tiền cố định
                giaTriGiam = pgg.getSoTienGiam();
            } else {
                // Giảm theo phần trăm, kiểm tra giảm tối đa
                double phanTramGiam = totalAmount * (pgg.getPhanTramGiam() / 100);
                giaTriGiam = Math.min(phanTramGiam, pgg.getGiamToiDa());
            }
        }
        if (hoaDon.getPhiShip() != null) {
            phiship = hoaDon.getPhiShip();
        }
        double tongThanhToan = totalAmount - giaTriGiam + phiship;
        hoaDon.setTongTien(tongThanhToan);
        hoaDonRepository.save(hoaDon);
        return "redirect:/admin/hoadon/view-hoadon/"+ hdct.getHoaDon().getId();
    }

    @PostMapping("/hoadon/updatesp")
    public String updateSp(@RequestParam("id") Integer id, @RequestParam("idspct") Integer idspct, @RequestParam(value = "soluongadd", required = false) String soluongadd) {
        HoaDon hd = hoaDonRepository.getReferenceById(id);
        SanPhamChiTiet spct = sanPhamChiTietRepository.getReferenceById(idspct);

        soluongadd = soluongadd.trim().replaceAll("[^\\w\\s]", ""); // Loại bỏ tất cả ký tự đặc biệt ngoại trừ chữ cái, chữ số, và khoảng trắng

        // Đảm bảo mã hóa đơn có độ dài 3 ký tự
        if (soluongadd == null || soluongadd.trim().isEmpty() || soluongadd.trim().equals("0")) {
            soluongadd = "1";
        } else {
            try {
                // Thử chuyển đổi soluongadd sang số nguyên
                int value = Integer.parseInt(soluongadd.trim());

                // Kiểm tra nếu giá trị nhỏ hơn 0
                if (value < 0) {
                    soluongadd = "1";
                }
                // Kiểm tra nếu giá trị lớn hơn số lượng cho phép
                else if (value > spct.getSoLuong()) {
                    soluongadd = String.valueOf(spct.getSoLuong());
                } else {
                    // Nếu hợp lệ, gán lại giá trị số nguyên đã kiểm tra
                    soluongadd = String.valueOf(value);
                }
            } catch (NumberFormatException e) {
                // Trường hợp soluongadd không phải là số nguyên
                soluongadd = "1";
            }
        }

        List<HoaDonChiTiet> listhdct = hoaDonChiTietRepository.findallbyhoadon(id);
        boolean exists = false;
        for (HoaDonChiTiet hdct1 : listhdct) {
            if (hdct1.getSanPhamChiTiet().getId() == idspct) {
                hdct1.setSoLuong(hdct1.getSoLuong() + Integer.parseInt(soluongadd.trim()));
                hdct1.setDonGia(spct.getDonGia() * hdct1.getSoLuong());
                hoaDonChiTietRepository.save(hdct1);
                exists = true;
                break;
            }
        }
        if (!exists) {
            HoaDonChiTiet hdct = new HoaDonChiTiet();
            hdct.setMa("HDCT"+System.currentTimeMillis());
            hdct.setHoaDon(hd);
            hdct.setSanPhamChiTiet(spct);
            hdct.setSoLuong(Integer.parseInt(soluongadd.trim()));
            hdct.setDonGia(spct.getDonGia() * hdct.getSoLuong());
            hdct.setNgayTao(new Date());
            hdct.setDeleted(0);
            hoaDonChiTietRepository.save(hdct);
            setpgg(hdct.getHoaDon().getId());
        }
        spct.setSoLuong(spct.getSoLuong() - Integer.parseInt(soluongadd.trim()));
        sanPhamChiTietRepository.save(spct);
        HoaDon hoaDon = hoaDonRepository.getReferenceById(id);
        List<HoaDonChiTiet> lsthdct = hoaDonChiTietRepository.findallbyhoadon(id);
        double totalAmount = lsthdct.stream()
                .mapToDouble(item -> item.getDonGia()).sum();
        Integer phiship = 0;
        PhieuGiamGia pgg = hoaDon.getPhieuGiamGia();
        double giaTriGiam = 0;
        if (pgg != null) {
            if (pgg.getHinhThucGiam() == 0) {
                // Giảm theo số tiền cố định
                giaTriGiam = pgg.getSoTienGiam();
            } else {
                // Giảm theo phần trăm, kiểm tra giảm tối đa
                double phanTramGiam = totalAmount * (pgg.getPhanTramGiam() / 100);
                giaTriGiam = Math.min(phanTramGiam, pgg.getGiamToiDa());
            }
        }
        if (hoaDon.getPhiShip() != null) {
            phiship = hoaDon.getPhiShip();
        }
        double tongThanhToan = totalAmount - giaTriGiam + phiship;
        hoaDon.setTongTien(tongThanhToan);
        hoaDonRepository.save(hoaDon);
        return "redirect:/admin/hoadon/view-hoadon/"+ id;
    }

}
