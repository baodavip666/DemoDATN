package com.example.demo.controller.user;


import com.example.demo.entity.*;
import com.example.demo.repository.admin.*;
import com.example.demo.repository.user.HomeRepository;
import com.example.demo.service.admin.SanPhamService;
import com.example.demo.service.user.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("user")
public class HomeController {
    @Autowired
    HomeRepository homeRepository;
    @Autowired
    SanPhamRepository sanPhamRepository;
    @Autowired
    HomeService homeService;
    @Autowired
    SanPhamService sanPhamService;
    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;
    @Autowired
    HinhAnhRepository hinhAnhRepository;
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    MauSacRepository mauSacRepository;


    @GetMapping("/san-pham/hienThi")
    public String hienThi(@RequestParam(defaultValue = "") String tensp,@RequestParam(required = false) Integer danhmuc,@RequestParam(required = false) Integer idsize,
                          @RequestParam(required = false) Integer idMauSac, Model model,@RequestParam(defaultValue = "0") Integer sapxep) {
        List<SanPhamChiTiet> list = new ArrayList<>();
        if (sapxep == 2){
            list = homeRepository.findurl(danhmuc,idMauSac,idsize,tensp);
        }else{
            list = homeRepository.findurl1(danhmuc,idMauSac,idsize,tensp);
        }
        List<SanPhamChiTiet> resultList = new ArrayList<>();
        Map<SanPham, SanPhamChiTiet> map = new HashMap<>();

        for (SanPhamChiTiet item : list) {
            SanPham sanpham = item.getSanPham();
            // Chỉ thêm item nếu sản phẩm chưa tồn tại trong map
            if (!map.containsKey(sanpham)) {
                map.put(sanpham, item);
                resultList.add(item);
            }
        }
        // Định dạng đơn giá và thêm list sản phẩm chi tiết vào model
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        Map<Integer, String> donGiaFormattedMap = list.stream()
                .collect(Collectors.toMap(
                        SanPhamChiTiet::getId,
                        sp -> formatter.format(sp.getDonGia().intValue())
                ));
        model.addAttribute("donGiaFormattedMap", donGiaFormattedMap);

        model.addAttribute("listsp", resultList);

        // Lấy hình ảnh cho từng sản phẩm chi tiết
        Map<Integer, String> hinhAnhMap = new HashMap<>();
        for (SanPhamChiTiet spchiTiet : list) {
            List<String> hinhanhht = hinhAnhRepository.hinhAnhDuongDan(spchiTiet.getId());
            if (!hinhanhht.isEmpty()) {
                hinhAnhMap.put(spchiTiet.getId(), hinhanhht.get(0)); // Lấy ảnh đầu tiên
            } else {
                hinhAnhMap.put(spchiTiet.getId(), "default-image.jpg"); // Ảnh mặc định nếu không có
            }
        }

        model.addAttribute("hinhAnhMap", hinhAnhMap);
        model.addAttribute("tensp",tensp);
        model.addAttribute("danhmuc",danhmuc);
        model.addAttribute("idsize",idsize);
        model.addAttribute("idMauSac",idMauSac);
        model.addAttribute("danhmuc",danhmuc);
        model.addAttribute("sapxep",sapxep);

        // Trả về view hiển thị sản phẩm

        return "user/san-pham/sanpham";
    }

    @GetMapping("/home")
    public String viewHome(Model model) {
        List<SanPhamChiTiet> list = homeService.getTop5UniqueSanPhamChiTiets();
        model.addAttribute("list", list);
        List<SanPhamChiTiet> listsp = homeService.getTop5ProductSanPhamChiTiets();
        model.addAttribute("listsplist", listsp);
        // Định dạng đơn giá và thêm list sản phẩm chi tiết vào model
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        Map<Integer, String> donGiaFormattedMap = list.stream()
                .collect(Collectors.toMap(
                        SanPhamChiTiet::getId,
                        sp -> formatter.format(sp.getDonGia().intValue())
                ));
        model.addAttribute("donGiaFormattedMap", donGiaFormattedMap);
        Map<Integer, String> hinhAnhMap = new HashMap<>();
        for (SanPhamChiTiet spchiTiet : listsp) {
            List<String> hinhanhht = hinhAnhRepository.hinhAnhDuongDan(spchiTiet.getId());
            if (!hinhanhht.isEmpty()) {
                hinhAnhMap.put(spchiTiet.getId(), hinhanhht.get(0)); // Lấy ảnh đầu tiên
            } else {
                hinhAnhMap.put(spchiTiet.getId(), "default-image.jpg"); // Ảnh mặc định nếu không có
            }
        }
        model.addAttribute("hinhAnhMap", hinhAnhMap);
        return "user/home";
    }


    @GetMapping("/san-pham/detail/{id}")
    public String viewSanPhamChiTiet(@PathVariable("id") Integer id, Model model) {
        SanPhamChiTiet sanPhamCT = homeRepository.findSanPhamChiTietById(id);
        SanPham sanPham = sanPhamRepository.getReferenceById(sanPhamCT.getSanPham().getId());
        List<SanPhamChiTiet> lstsanphamCT = sanPhamChiTietRepository.findBySanPham(sanPhamCT.getSanPham().getId());
        List<SanPhamChiTiet> lstsanphammausac = new ArrayList<>();
        List<SanPhamChiTiet> sanPhamMoi = new ArrayList<>();


        for (SanPhamChiTiet spct : lstsanphamCT) {
            boolean isUniqueColor = true;
            for (SanPhamChiTiet spct1 : sanPhamMoi) {
                if (spct1.getMauSac().equals(spct.getMauSac())) {
                    isUniqueColor = false;
                    break;
                }
            }
            if (isUniqueColor) {
                sanPhamMoi.add(spct);
            }
        }

// Sau khi hoàn thành vòng lặp, thêm tất cả sản phẩm mới vào lstsanphammausac
        lstsanphammausac.addAll(sanPhamMoi);
        List<SanPhamChiTiet> lstsanphamsize = sanPhamChiTietRepository.findspctSizeBySanPhamandmausac(sanPhamCT.getSanPham().getId(),sanPhamCT.getMauSac().getId());
        model.addAttribute("lstsanphamsize", lstsanphamsize);
        model.addAttribute("lstsanphammausac", lstsanphammausac);

        if (sanPhamCT == null) {
            model.addAttribute("errorMessage", "Sản phẩm không tồn tại");
            return "error";
        }
        List<String> lsthinhAnh = hinhAnhRepository.hinhAnhDuongDan(id);
        String formattedDonGia = String.format("%,.0f", sanPhamCT.getDonGia());
        model.addAttribute("sanPhamCT", sanPhamCT);
        model.addAttribute("formattedDonGia", formattedDonGia);
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("lstsanphamCT", lstsanphamCT);
        for (int i = 0; i < lsthinhAnh.size(); i++) {
            model.addAttribute("lsthinhAnh"+i, lsthinhAnh.get(i));
        }
        return "user/chi-tiet-san-pham/chitietsanpham";
    }


    @ModelAttribute("size")
    public List<Size> getSize() {
        return sizeRepository.findAll();
    }

    @ModelAttribute("mauSac")
    public List<MauSac> getMauSac() {
        return mauSacRepository.findAll();
    }

    @GetMapping(value = "/bangsize")
    public String bangSize() {
        return "user/bang-size/bangsize";
    }

    @GetMapping(value = "/vechungtoi")
    public String veChungToi() {
        return "user/ve-chung-toi/vechungtoi";
    }


}
