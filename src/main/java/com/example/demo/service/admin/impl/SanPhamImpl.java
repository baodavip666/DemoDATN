package com.example.demo.service.admin.impl;

import com.example.demo.dto.SanPhamResponse;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.Size;
import com.example.demo.repository.admin.*;
import com.example.demo.service.admin.SanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SanPhamImpl implements SanPhamService {

    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    XuatXuRepository xuatXuRepository;

    @Autowired
    NhaSanXuatRepository nhaSanXuatRepository;

    @Autowired
    ChatLieuRepository chatLieuRepository;

    @Autowired
    MauSacRepository mauSacRepository;

    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;
    @Override
    public List<SanPham> getAll() {
        return sanPhamRepository.findAll();
    }

    @Override
    public List<SanPham> getAllActiveListSanPhams() {
        return sanPhamRepository.getListActiveSanPham();
    }

    @Override
    public Page<SanPham> getAllSanPhams(String search, String trangThai, Pageable pageable) {
        if (search == null) {
            search = "";
        }
        search = "%" + search + "%";
        Page<SanPham> page = null;
        System.out.println("search: " + search);
        if (trangThai != null) {
            if (trangThai == "") {
                trangThai = null;
            }
        }
        if (trangThai == null) {
            page = sanPhamRepository.findByParam(search, pageable);
        } else {
            page = sanPhamRepository.findByParamAndTrangThai(search, trangThai, pageable);
        }
        for (SanPham s : page.getContent()) {
            Long soLuongTon = sanPhamChiTietRepository.totalByProduct(s.getId());
            s.setSoLuongTon(soLuongTon);
        }
        System.out.println("size page: " + page.getContent().size());
        return page;
    }

    @Override
    public SanPham findById(Integer id) {
        return sanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public String create(@Valid SanPham sanPham, BindingResult bindingResult,
                         @RequestParam("selectedColors") List<String> selectedColors,
                         @RequestParam("selectedSizes") List<String> selectedSizes,
                         @RequestParam("xuatXu") Integer xuatXu,
                         @RequestParam("nhaSanXuat") Integer nhaSanXuat

            , Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sanPham", sanPham);
            return "/admin/san-pham/create";
        }

        if (sanPham.getMa() == null || sanPham.getMa().trim().isEmpty()) {
            sanPham.setMa(generateRandomCodeSP());
        }

        for (SanPham sp : sanPhamRepository.findAll()) {
            if (sanPham.getMa().equalsIgnoreCase(sp.getMa())) {
                model.addAttribute("errorMa", "Mã sản phẩm này đã tồn tại trong CSDL!");
                model.addAttribute("sanPham", sanPham);
                return "admin/san-pham/mau-sac/create";
            }
        }

        sanPham.setNguoiTao("Admin");
        sanPham.setNgayTao(new Date());
        sanPham.setTrangThai("Đang hoạt động");
        sanPhamRepository.save(sanPham);

        SanPhamChiTiet sanPhamChiTiet;
        List<SanPhamChiTiet> lstcheck = new ArrayList<>();

        for (int i = 0; i < selectedColors.size() - 1; i++) {
            for (int j = 0; j < selectedSizes.size() - 1; j++) {

                sanPhamChiTiet = new SanPhamChiTiet();

                sanPhamChiTiet.setSanPham(sanPham);

                sanPhamChiTiet.setXuatXu(xuatXuRepository.getReferenceById(xuatXu));
                sanPhamChiTiet.setChatLieu(chatLieuRepository.getReferenceById(1));
                sanPhamChiTiet.setNhaSanXuat(nhaSanXuatRepository.getReferenceById(nhaSanXuat));

                sanPhamChiTiet.setMauSac(mauSacRepository.getReferenceById(Integer.parseInt(selectedColors.get(i))));
                sanPhamChiTiet.setSize(sizeRepository.getReferenceById(Integer.parseInt(selectedSizes.get(j))));

                sanPhamChiTiet.setMa(generateRandomCodeSPCT());

                sanPhamChiTiet.setTrangThai("Đang hoạt động");
                sanPhamChiTiet.setDonGia(0d);
                sanPhamChiTiet.setSoLuong(0);
                sanPhamChiTiet.setNgayTao(new Date());
                sanPhamChiTiet.setNguoiTao("Admin");

                sanPhamChiTiet.setDonViTinh("gram");

                sanPhamChiTiet.setKhoiLuong(400);

                sanPhamChiTietRepository.save(sanPhamChiTiet);

                lstcheck.add(sanPhamChiTiet);

            }
        }

        return "redirect:/admin/san-pham/create/ren-ctsp?idSP=" + sanPham.getId();

    }


    @Override
    public void toggleStatus(int id) {
        SanPham sanPham = sanPhamRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Sản phẩm không tồn tại"));

        if ("Đang hoạt động".equals(sanPham.getTrangThai())) {
            sanPham.setTrangThai("Ngừng hoạt động");
        } else if ("Ngừng hoạt động".equals(sanPham.getTrangThai())) {
            sanPham.setTrangThai("Đang hoạt động");
        }

        sanPham.setNguoiSua("Admin");
        sanPham.setNgaySua(new Date());
        sanPhamRepository.save(sanPham);
    }

    @Override
    public String update(@Valid SanPham sanPham, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sanPham", sanPham);
            return "admin/san-pham/view-update";
        }

        SanPham currentSanPham = sanPhamRepository.getReferenceById(sanPham.getId());
        if (currentSanPham == null) {
            model.addAttribute("errorMa", "Mã sản phẩm không tồn tại!");
            model.addAttribute("sanPham", sanPham);
            return "admin/san-pham/mau-sac/view-update";
        }

        if (sanPham.getMa() == null || sanPham.getMa().trim().isEmpty()) {
            sanPham.setMa(generateRandomCodeSP());
        }

        for (SanPham sp : sanPhamRepository.findAll()) {
            if (!sp.getId().equals(sanPham.getId()) && sanPham.getMa().equalsIgnoreCase(sp.getMa())) {
                model.addAttribute("errorMa", "Mã sản phẩm đã tồn tại trong CSDL!");
                model.addAttribute("mauSac", sanPham);
                return "admin/san-pham/view-update";
            }
        }

        sanPham.setNguoiSua("Admin");
        sanPham.setNgaySua(new Date());

        sanPhamRepository.save(sanPham);
        if(sanPham.getTrangThai().equals("Ngừng hoạt động")){
            List<SanPhamChiTiet> lstSPCT = sanPhamChiTietRepository.findBySanPham(sanPham.getId());
            for (SanPhamChiTiet spct: lstSPCT
            ) {
                spct.setTrangThai("Ngừng hoạt động");
                sanPhamChiTietRepository.save(spct);
            }
        }
        return "redirect:/admin/san-pham/view";
    }

    @Override
    public String delete(int id, RedirectAttributes redirectAttributes) {
        SanPham sanPham = sanPhamRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Sản phẩm không tồn tại"));
        Long count = hoaDonChiTietRepository.countByIdSP(id);
        if(count < 1 && sanPham != null){
            for (SanPhamChiTiet spct : sanPhamChiTietRepository.findAll()){
                if(spct.getSanPham().getId() == id){
                    sanPhamChiTietRepository.deleteById(spct.getId());
                }
            }
            sanPhamRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("messageesuccess", "Xóa Thành công");
        }
        if(count > 0 && sanPham != null){
            redirectAttributes.addFlashAttribute("messageerror", "Không thể xóa sản phẩm do đã có hóa đơn");
        }

        return "redirect:/admin/san-pham/view";
    }

}
