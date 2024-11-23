package com.example.demo.controller.admin;

import com.example.demo.dto.SanPhamResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.admin.*;
import com.example.demo.service.admin.CoGiayService;
import com.example.demo.service.admin.DanhMucService;
import com.example.demo.service.admin.DeGiayService;
import com.example.demo.service.admin.MauSacService;
import com.example.demo.service.admin.SanPhamChiTietService;
import com.example.demo.service.admin.SanPhamService;
import com.example.demo.service.admin.SizeService;
import com.example.demo.service.admin.ThuongHieuService;
import com.example.demo.service.admin.impl.UploadImageFileImpl;
import com.google.zxing.qrcode.decoder.Mode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.awt.*;
import java.net.URI;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/admin/san-pham")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private CoGiayService coGiayService;

    @Autowired
    private ThuongHieuService thuongHieuService;

    @Autowired
    private DeGiayService deGiayService;

    @Autowired
    private DanhMucService danhMucService;

    @Autowired
    private XuatXuRepository xuatXuRepository;

    @Autowired
    private NhaSanXuatRepository nhaSanXuatRepository;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    MauSacRepository mauSacRepository;

    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    HinhAnhRepository hinhAnhRepository;

    @Autowired
    private UploadImageFileImpl cloudinaryService;

    @ModelAttribute("coGiayLists")
    public List<CoGiay> getCoGiayLists() {
        return coGiayService.getListActiveCoGiays();
    }

    @ModelAttribute("danhMucLists")
    public List<DanhMuc> getDanhMucLists() {
        return danhMucService.getListActiveDanhMucs();
    }

    @ModelAttribute("thuongHieuLists")
    public List<ThuongHieu> getThuongHieuLists() {
        return thuongHieuService.getListActiveThuongHieus();
    }

    @ModelAttribute("deGiayLists")
    public List<DeGiay> getDeGiayLists() {
        return deGiayService.getListActiveCoGiays();
    }

    @ModelAttribute("chatLieuLists")
    public List<ChatLieu> getchatLieuLists() {
        return chatLieuRepository.findAll();
    }

    @ModelAttribute("xuatXuLists")
    public List<XuatXu> getxuatXuLists() {
        return xuatXuRepository.findAll();
    }

    @ModelAttribute("nhaSanXuatLists")
    public List<NhaSanXuat> getnhaSanXuatLists() {
        return nhaSanXuatRepository.findAll();
    }

    @ModelAttribute("colors")
    public List<MauSac> getMauSac() {
        return mauSacService.getAll();
    }

    @ModelAttribute("sizes")
    public List<Size> getSize() {
        return sizeService.getAll();
    }


    @GetMapping("view")
    public String view(Model model, @RequestParam("page") Optional<Integer> page,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) String trangThai) {
        int checkpage = page.orElse(0);
        int pagesize = 3;
        Sort sort = Sort.by("id").descending();

        checkpage = Math.max(checkpage, 0);
        Pageable pageable = PageRequest.of(checkpage, pagesize, sort);
        List<SanPham> lstsp = sanPhamRepository.findAll();

        for (SanPham sp : lstsp) {
            if (sanPhamChiTietRepository.totalByProduct(sp.getId()) < 1) {
                if (sp.getTrangThai().equals("Đang hoạt động")) {
                    sp.setTrangThai("Hết hàng");
                    sanPhamRepository.save(sp);
                    List<SanPhamChiTiet> lstspct = sanPhamChiTietRepository.findBySanPham(sp.getId());
                    for (SanPhamChiTiet spct1 : lstspct) {
                        spct1.setTrangThai("Hết hàng");
                        sanPhamChiTietRepository.save(spct1);
                    }
                }
            } else {
                if (sp.getTrangThai().equals("Hết hàng") && sanPhamChiTietRepository.totalByProduct(sp.getId()) > 1) {
                    sp.setTrangThai("Đang hoạt động");
                    sanPhamRepository.save(sp);
                }
            }

        }
        Page<SanPham> sanPhamPage = sanPhamService.getAllSanPhams(search, trangThai, pageable);
        model.addAttribute("sanPhamLists", sanPhamPage);
        return "admin/san-pham/view";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("sanPham", sanPhamService.findById(id));
        return "admin/san-pham/detail";
    }

    @GetMapping("create")
    public String create(Model model) {
        SanPham sanPham = new SanPham();
        sanPham.setMa(sanPhamService.generateRandomCodeSP());
        model.addAttribute("sanPham", sanPham);

        return "admin/san-pham/create";
    }

    @GetMapping("create/ren-ctsp")
    public String renctsp(Model model, @RequestParam Integer idSP) {

        List<SanPhamChiTiet> renSPCT = sanPhamChiTietRepository.renSPCT(idSP);
        SanPham sanPham = sanPhamRepository.getReferenceById(idSP);
        model.addAttribute("tenSP", sanPham);
        model.addAttribute("renctsp", renSPCT);

        return "admin/san-pham/view-ren-ctsp";
    }

    @PostMapping("create")
    public String create(@Valid SanPham sanPham, BindingResult bindingResult,
                         @RequestParam("selectedColors") List<String> selectedColors,
                         @RequestParam("selectedSizes") List<String> selectedSizes,
                         @RequestParam("xuatXu") Integer xuatXu,
                         @RequestParam("nhaSanXuat") Integer nhaSanXuat,
                         Model model) {
        return sanPhamService.create(sanPham, bindingResult, selectedColors, selectedSizes, xuatXu, nhaSanXuat, model);
    }

    @GetMapping("view-update/{id}")
    public String viewUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("sanPham", sanPhamService.findById(id));
        return "admin/san-pham/view-update";
    }

    @GetMapping("delete-renctsp/{id}")
    public String delterenctsp(@PathVariable Integer id, Model model) {

        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id).get();
        List<SanPhamChiTiet> renSPCT = sanPhamChiTietRepository.renSPCT(sanPhamChiTiet.getSanPham().getId());
        model.addAttribute("renctsp", renSPCT);
        if (sanPhamChiTietRepository.existsById(id)) {
            sanPhamChiTietRepository.deleteById(id);
        }

        return "admin/san-pham/view-ren-ctsp";
    }

    @PostMapping("update/{id}")
    public String update(@Valid SanPham sanPham, BindingResult bindingResult, Model model) {
        return sanPhamService.update(sanPham, bindingResult, model);
    }

    @GetMapping("remove/{id}")
    public String remove(@PathVariable Integer id,RedirectAttributes redirectAttributes) {
        return sanPhamService.delete(id,redirectAttributes);
    }

    @PostMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable int id) {
        sanPhamService.toggleStatus(id);
        return "redirect:/admin/san-pham/view";
    }

    @PostMapping("/done-renctsp")
    public ResponseEntity<String> saveTableData(@RequestBody List<SanPhamChiTietDTO> sanPhamList) {
        try {
            SanPhamChiTiet sanPhamChiTiet;
            for (SanPhamChiTietDTO sanPham : sanPhamList) {
                sanPhamChiTiet = new SanPhamChiTiet();
                if (sanPhamChiTietRepository.existsById(sanPham.getIdCtsp())) {
                    sanPhamChiTiet = sanPhamChiTietRepository.getReferenceById(sanPham.getIdCtsp());
                    sanPhamChiTiet.setDonGia(sanPham.getDonGia());
                    sanPhamChiTiet.setSoLuong(sanPham.getSoLuong());

                    sanPhamChiTietRepository.save(sanPhamChiTiet);
                }
            }
            return ResponseEntity.ok("Lưu thành công!");
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi để kiểm tra chi tiết
            return ResponseEntity.badRequest().body("Đã xảy ra lỗi!");
        }
    }


    @GetMapping("san-pham-chi-tiet/view/{id}")
    public String viewChiTiet(@PathVariable Integer id,
                              @RequestParam(required = false) Integer chatLieuId,
                              @RequestParam(required = false) Integer coGiayId,
                              @RequestParam(required = false) Integer deGiayId,
                              @RequestParam(required = false) Integer thuongHieuId,
                              @RequestParam(required = false) String trangThai,
                              @RequestParam(required = false) Integer mauSacId,
                              @RequestParam(required = false) Integer kichCoId,
                              @RequestParam(required = false) String khoanggia,
                              Model model, @RequestParam("page") Optional<Integer> page) {
        int checkpage = page.orElse(0);
        int pagesize = 10;
        checkpage = Math.max(checkpage, 0);
        Pageable pageable = PageRequest.of(checkpage, pagesize);
        List<SanPhamChiTiet> listspct = sanPhamChiTietRepository.findBySanPham(id);
        for (SanPhamChiTiet spct1 : listspct) {
            if (spct1.getTrangThai().equals("Hết hàng") && spct1.getSoLuong() > 0) {
                spct1.setTrangThai("Đang hoạt động");
                sanPhamChiTietRepository.save(spct1);
            } else if (spct1.getTrangThai().equals("Đang hoạt động") && spct1.getSoLuong() < 1) {
                spct1.setTrangThai("Hết hàng");
                sanPhamChiTietRepository.save(spct1);
            }
        }

        Page<SanPhamChiTiet> sanPhamChiTietPage = sanPhamChiTietService.findProductsByCriteria(chatLieuId, coGiayId, deGiayId, thuongHieuId, trangThai, mauSacId, kichCoId, id, khoanggia, pageable);
        model.addAttribute("sanPhamChiTietLists", sanPhamChiTietPage);
        model.addAttribute("idSP", id);
        return "admin/san-pham/san-pham-chi-tiet/view";
    }


    @GetMapping("/san-pham-chi-tiet/delete/{id}")
    public String deleteSPCT(@PathVariable Integer id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Long count = hoaDonChiTietRepository.countByIdSpct(id);
        if (count > 0) {
            redirectAttributes.addFlashAttribute("message", "Đã có đơn hàng, không thể xóa");
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
        List<HoaDonChiTiet> lstHDCT = hoaDonChiTietRepository.getHDCTbyIDSPCT(id);
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.getReferenceById(id);
        if (sanPhamChiTiet != null && lstHDCT.isEmpty()) {
            sanPhamChiTietRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("messageokdlete", "Xóa thành công");
        }
        return "redirect:/admin/san-pham/san-pham-chi-tiet/view/" + sanPhamChiTiet.getSanPham().getId();
    }

    @PostMapping("san-pham-chi-tiet/ren-spct")
    public String renSPCT(@RequestParam("selectedColors") List<MauSac> selectedColors,
                          @RequestParam("selectedSizes") List<Size> selectedSizes,
                          Model model) {

        System.out.println(selectedSizes);
        System.out.println(selectedColors);
        return "redirect:/admin/san-pham/create";
    }

    @PostMapping("san-pham-chi-tiet/update")
    public String updateCTSP(@RequestParam("idspct") Integer id,
                             @RequestParam Integer chatLieuId1,
                             @RequestParam Integer mauSacId1,
                             @RequestParam Integer kichCoId1,
                             @RequestParam Integer xuatXu1,
                             @RequestParam String donviTinh,
                             @RequestParam String khoiLuong,
                             @RequestParam String soluongsua,
                             @RequestParam String mactsp1,
                             @RequestParam String donGia1,
                             @RequestParam String moTa,
                             @RequestParam String ghiChu,
                             @RequestParam String trangThai1,

                             RedirectAttributes redirectAttributes) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.getReferenceById(id);
        Long count = hoaDonChiTietRepository.countByIdSpct(id);

        if (count > 0) {
            boolean check = true;
            if (sanPhamChiTiet.getChatLieu().getId() != chatLieuId1) {
                redirectAttributes.addFlashAttribute("messageeror", "Chất liệu không được thay đổi");
                check = false;
            }

            if (sanPhamChiTiet.getMauSac().getId() != xuatXu1) {
                redirectAttributes.addFlashAttribute("messageeror", "Xuất Xứ không được thay đổi");
                check = false;
            }
            if (sanPhamChiTiet.getMauSac().getId() != mauSacId1) {
                redirectAttributes.addFlashAttribute("messageeror", "Màu sắc không được thay đổi");
                check = false;
            }
            if (sanPhamChiTiet.getSize().getId() != kichCoId1) {
                redirectAttributes.addFlashAttribute("messageeror", "Kích cỡ không được thay đổi");
                check = false;
            }
            if (!sanPhamChiTiet.getMa().equals(mactsp1)) {
                redirectAttributes.addFlashAttribute("messageeror", "Mã không được thay đổi");
                check = false;
            }
            if (!sanPhamChiTiet.getDonViTinh().equals(donviTinh)) {
                redirectAttributes.addFlashAttribute("messageeror", "Đơn vị tính không được thay đổi");
                check = false;
            }
            try {
                if (sanPhamChiTiet.getKhoiLuong() != Integer.parseInt(khoiLuong)) {
                    redirectAttributes.addFlashAttribute("messageeror", "Khối lượng không được thay đổi");
                    check = false;
                }
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("messageeror", "Khối lượng không được thay đổi");
                check = false;
            }

            try {
                donGia1 = donGia1.replace(",", "");
                if (sanPhamChiTiet.getDonGia() != Double.parseDouble(donGia1)) {
                    redirectAttributes.addFlashAttribute("messageeror", "Đơn Giá không được thay đổi");
                    check = false;
                }
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("messageeror", "Đơn giá không được thay đổi");
                check = false;
            }

            redirectAttributes.addFlashAttribute("codede", id);
            try {
                Integer sl = Integer.parseInt(soluongsua);
                if (sl < 0 || sl > 2000000000) {
                    redirectAttributes.addFlashAttribute("messageeror", "Nhập sai số lượng");
                    check = false;
                }
                if (check) {
                    if (trangThai1 != sanPhamChiTiet.getTrangThai()) {
                        sanPhamChiTiet.setTrangThai(trangThai1);
                    }
                    sanPhamChiTiet.setSoLuong(sl);
                    sanPhamChiTiet.setMoTa(moTa);
                    sanPhamChiTiet.setGhiChu(ghiChu);
                    sanPhamChiTietRepository.save(sanPhamChiTiet);
                    redirectAttributes.addFlashAttribute("messagesucces", "Sửa thành công");
                    return "redirect:/admin/san-pham/san-pham-chi-tiet/view/" + sanPhamChiTiet.getSanPham().getId();
                }
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("messageeror", "Số Lượng sai định dạng");
            }
        }


        if (count == 0) {
            boolean check = true;
            if (sanPhamChiTiet.getMauSac().getId() != mauSacId1) {
                redirectAttributes.addFlashAttribute("messageeror", "Màu sắc không được thay đổi");
                check = false;

            }
            if (sanPhamChiTiet.getSize().getId() != kichCoId1) {
                redirectAttributes.addFlashAttribute("messageeror", "Kích cỡ không được thay đổi");
                check = false;

            }
            if (!sanPhamChiTiet.getMa().equals(mactsp1)) {
                redirectAttributes.addFlashAttribute("messageeror", "Mã không được thay đổi");
                check = false;

            }
            if (!sanPhamChiTiet.getDonViTinh().equals(donviTinh)) {
                redirectAttributes.addFlashAttribute("messageeror", "Đơn vị tính không được thay đổi");
                check = false;
            }
            try {
                if (Integer.parseInt(khoiLuong) < 0 || Integer.parseInt(khoiLuong) > 1000) {
                    redirectAttributes.addFlashAttribute("messageeror", "Khối lượng phải lớn hơn 0 hoặc bé hơn 1KG");
                    check = false;
                }
                try {
                    donGia1 = donGia1.replace(",", "");
                    if (Double.parseDouble(donGia1) < 0) {
                        redirectAttributes.addFlashAttribute("messageeror", "Đơn giá phải lớn hơn 0");
                        check = false;
                    }
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("messageeror", "Đơn giá sai định dạng");
                    check = false;
                }
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("messageeror", "Khối lượng sai định dạng");
                check = false;
            }
            try {
                Integer sl = Integer.parseInt(soluongsua);
                if (sl < 0 || sl > 2000000000) {
                    redirectAttributes.addFlashAttribute("messageeror", "Nhập sai số lượng");
                    check = false;

                }
                if (check) {
                    if (trangThai1 != sanPhamChiTiet.getTrangThai()) {
                        sanPhamChiTiet.setTrangThai(trangThai1);
                    }
                    Double dongia = Double.parseDouble(donGia1);
                    ChatLieu chatLieu = chatLieuRepository.getReferenceById(chatLieuId1);
                    XuatXu xuatXu = xuatXuRepository.getReferenceById(xuatXu1);

                    sanPhamChiTiet.setDonGia(dongia);
                    sanPhamChiTiet.setKhoiLuong(Integer.parseInt(khoiLuong));
                    sanPhamChiTiet.setChatLieu(chatLieu);
                    sanPhamChiTiet.setXuatXu(xuatXu);
                    sanPhamChiTiet.setSoLuong(sl);
                    sanPhamChiTiet.setMoTa(moTa);
                    sanPhamChiTiet.setGhiChu(ghiChu);
                    sanPhamChiTietRepository.save(sanPhamChiTiet);
                    redirectAttributes.addFlashAttribute("messagesucces", "Sửa thành công");
                    return "redirect:/admin/san-pham/san-pham-chi-tiet/view/" + sanPhamChiTiet.getSanPham().getId();
                }
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("messageeror", "Số Lượng sai định dạng");
            }
            redirectAttributes.addFlashAttribute("codede", id);
            sanPhamChiTietRepository.save(sanPhamChiTiet);
        }

        return "redirect:/admin/san-pham/san-pham-chi-tiet/view/" + sanPhamChiTiet.getSanPham().getId();
    }

    @GetMapping("/san-pham-chi-tiet/view-add/{id}")
    public String viewAdd(Model model, @PathVariable Integer id) {
        SanPhamChiTiet spctNew = new SanPhamChiTiet();
        SanPham sanPham = sanPhamService.findById(id);
        spctNew.setSanPham(sanPham);
        model.addAttribute("addCTSP", spctNew);
        model.addAttribute("idSP", id);
        return "/admin/san-pham/san-pham-chi-tiet/create";
    }

    @PostMapping("san-pham-chi-tiet/add/{id}")
    public String addCTSP(
            SanPhamChiTiet sanPhamChiTiet,
            @PathVariable Integer id,
            @RequestParam Integer chatLieuId2,
            @RequestParam Integer nhaSanXuat2,
            @RequestParam Integer mauSacId2,
            @RequestParam Integer kichCoId2,
            @RequestParam Integer xuatXu2,
            @RequestParam String mactsp2,
            @RequestParam String donGia2,
            @RequestParam String soluongsua2,
            @RequestParam("files") List<MultipartFile> files,
            RedirectAttributes redirectAttributes, Model model) {
//        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.getReferenceById(id);

        boolean check = true;
        if (donGia2 == null || donGia2.trim().equals("")) {
            redirectAttributes.addFlashAttribute("messageeroradd", "Bạn chưa nhập đơn giá");
            return "redirect:/admin/san-pham/san-pham-chi-tiet/view-add/" + id;

        }
        if (soluongsua2 == null || "".equals(soluongsua2.trim())) {
            redirectAttributes.addFlashAttribute("messageeroradd", "Bạn chưa nhập Số lượng");
            return "redirect:/admin/san-pham/san-pham-chi-tiet/view-add/" + id;

        }
        if (files.isEmpty()) {
            redirectAttributes.addFlashAttribute("messageeroradd", "Phải có hình hành của sản phẩm");
            return "redirect:/admin/san-pham/san-pham-chi-tiet/view-add/" + id;

        }
        if (files.size() < 3) {
            redirectAttributes.addFlashAttribute("messageeroradd", "Phải có ít nhất 3 ảnh");
            return "redirect:/admin/san-pham/san-pham-chi-tiet/view-add/" + id;

        }
        try {
            if (Double.parseDouble(donGia2) < 0 || Double.parseDouble(donGia2) > 20_000_000_000L) {
                redirectAttributes.addFlashAttribute("messageeroradd", "Đơn Giá phải lớn hơn 0 và bé hơn 20 tỷ");
                return "redirect:/admin/san-pham/san-pham-chi-tiet/view-add/" + id;

            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messageeroradd", "Sai giá trị đơn giá");
            return "redirect:/admin/san-pham/san-pham-chi-tiet/view-add/" + id;

        }
        SanPhamChiTiet sanPhamChiTiet1 = new SanPhamChiTiet();
        try {
            Integer sl = Integer.parseInt(soluongsua2);
            if (sl < 0 || sl > 2000000000) {
                redirectAttributes.addFlashAttribute("messageeroradd", "Nhập sai số lượng");
                check = false;
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messageeroradd", "Số Lượng sai định dạng");
        }
        if (check) {
            SanPhamChiTiet spct = sanPhamChiTietRepository.findBySanPham(id).get(0);


            sanPhamChiTiet1.setMauSac(mauSacRepository.getReferenceById(mauSacId2));
            sanPhamChiTiet1.setSize(sizeRepository.getReferenceById(kichCoId2));
            sanPhamChiTiet1.setChatLieu(chatLieuRepository.getReferenceById(chatLieuId2));
            sanPhamChiTiet1.setXuatXu(xuatXuRepository.getReferenceById(xuatXu2));
            sanPhamChiTiet1.setNhaSanXuat(nhaSanXuatRepository.getReferenceById(nhaSanXuat2));
            sanPhamChiTiet1.setTrangThai("Đang hoạt động");
            sanPhamChiTiet1.setMa(sanPhamService.generateRandomCodeSPCT());
            sanPhamChiTiet1.setDonGia(Double.parseDouble(donGia2));
            sanPhamChiTiet1.setNguoiTao("Admin");
            sanPhamChiTiet1.setNgayTao(new Date());
            sanPhamChiTiet1.setNgaySua(new Date());
            sanPhamChiTiet1.setSoLuong(Integer.parseInt(soluongsua2));
            sanPhamChiTiet1.setSanPham(sanPhamRepository.getReferenceById(id));
            sanPhamChiTiet1.setDonViTinh(spct.getDonViTinh());
            sanPhamChiTiet1.setKhoiLuong(spct.getKhoiLuong());
            List<SanPhamChiTiet> lst = sanPhamChiTietRepository.findAll();
            for (SanPhamChiTiet spct1 : lst
            ) {
                if (spct1.getMa().equals(sanPhamChiTiet1.getMa())) {
                    redirectAttributes.addFlashAttribute("messageeroradd", "Mã vừa tạo đã trùng vui lòng tạo lại");
                    return "redirect:/admin/san-pham/san-pham-chi-tiet/view-add/" + id;
                } else if (spct1.getSize().getId() == sanPhamChiTiet1.getSize().getId()
                        && spct1.getMauSac().getId() == sanPhamChiTiet1.getMauSac().getId()
                        && spct1.getSanPham().getId() == sanPhamChiTiet1.getSanPham().getId()
                ) {
                    redirectAttributes.addFlashAttribute("messageeroradd", "Biến thể này đã tồn tại");
                    return "redirect:/admin/san-pham/san-pham-chi-tiet/view-add/" + id;
                }

            }
            sanPhamChiTietRepository.save(sanPhamChiTiet1);
            List<String> list = new ArrayList<>();
            ExecutorService es = Executors.newCachedThreadPool();
            for (int i = 0; i < files.size(); i++) {
                Integer x = i;
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String image = cloudinaryService.uploadFile(files.get(x));
                            list.add(image);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            es.shutdown();
            try {
                boolean finished = es.awaitTermination(100000, TimeUnit.MINUTES);
                if (finished) {

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<HinhAnh> result = new ArrayList<>();
            for (String s : list) {
                HinhAnh hinhAnh = new HinhAnh();
                hinhAnh.setDuongDan(s);
                hinhAnh.setNgayTao(new Date(System.currentTimeMillis()));
                hinhAnh.setSanPhamChiTiet(sanPhamChiTiet1);
                hinhAnhRepository.save(hinhAnh);
                result.add(hinhAnh);
            }
            redirectAttributes.addFlashAttribute("messagesucces", "Thêm thành công");

            return "redirect:/admin/san-pham/san-pham-chi-tiet/view/" + id;
        }
        return "redirect:/admin/san-pham/san-pham-chi-tiet/view-add/" + id;
    }

}