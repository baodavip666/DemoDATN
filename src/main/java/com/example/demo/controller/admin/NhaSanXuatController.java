package com.example.demo.controller.admin;

import com.example.demo.entity.DeGiay;
import com.example.demo.entity.NhaSanXuat;
import com.example.demo.repository.admin.DeGiayRepository;
import com.example.demo.repository.admin.NhaSanXuatRepository;
import com.example.demo.service.admin.DeGiayService;
import com.example.demo.service.admin.NhaSanXuatService;
import com.example.demo.service.admin.impl.NhaSanXuatImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/san-pham/nha-san-xuat")
public class NhaSanXuatController {

    @Autowired
    private NhaSanXuatService nhaSanXuatService;

    @Autowired
    private NhaSanXuatRepository nhaSanXuatRepository;

    @Autowired
    private NhaSanXuatImpl nhaSanXuatImpl;

    @GetMapping("view")
    public String searchNhaSanXuat(Model model, @RequestParam(defaultValue = "") String search1
            ,  @RequestParam(required = false) String trangthai, @RequestParam(name = "page") Optional<Integer> page) {
        int checkpage = page.orElse(0);
        int pagesize = 3;
        checkpage = Math.max(checkpage, 0);
        Pageable pageable = PageRequest.of(checkpage, pagesize);
        String loai1Int = null;
        if (trangthai != null && !trangthai.isEmpty()) {
            loai1Int = trangthai;
        }

        Page<NhaSanXuat> listNhaSanXuat = nhaSanXuatRepository.searchNhaSanXuat(
                search1.trim(), loai1Int, pageable);
        model.addAttribute("nhaSanXuatLists", listNhaSanXuat);
        model.addAttribute("search1", search1);
        model.addAttribute("trangthai", trangthai != null ? trangthai : "");
        return "admin/san-pham/nha-san-xuat/view";
    }

    @GetMapping("create")
    public String createForm(Model model) {
        model.addAttribute("nhaSanXuat", new NhaSanXuat());
        return "admin/san-pham/nha-san-xuat/create";
    }

    @PostMapping("create")
    public String create(@Valid NhaSanXuat nhaSanXuat, BindingResult bindingResult, Model model) {
        return nhaSanXuatService.create(nhaSanXuat, bindingResult, model);
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("nhaSanXuat", nhaSanXuatService.findById(id));
        return "admin/san-pham/nha-san-xuat/detail";
    }

    @GetMapping("view-update/{id}")
    public String viewUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("nhaSanXuat", nhaSanXuatService.findById(id));
        return "admin/san-pham/nha-san-xuat/view-update";
    }

    @PostMapping("update/{id}")
    public String update(@Valid NhaSanXuat nhaSanXuat, BindingResult bindingResult, Model model) {
        return nhaSanXuatService.update(nhaSanXuat, bindingResult, model);
    }

    @GetMapping("remove/{id}")
    public String remove(@PathVariable Integer id) {
        return nhaSanXuatService.delete(id);
    }


    @GetMapping("/inexcel")
    public ResponseEntity<InputStreamResource> download() throws IOException {
        String fileName = "nhasanxuat.xlsx";
        ByteArrayInputStream actualData = nhaSanXuatImpl.exportToExcel();
        InputStreamResource file = new InputStreamResource(actualData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }

    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file, Model model) {
        try {
            List<NhaSanXuat> nhaSanXuatList = nhaSanXuatImpl.importExcel(file);
            nhaSanXuatRepository.saveAll(nhaSanXuatList);
            model.addAttribute("message", "Tải lên thành công!");
        } catch (IOException e) {
            model.addAttribute("message", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/san-pham/nha-san-xuat/view";
    }
//    @GetMapping("/search")
//    public String viewSize(
//            @RequestParam(defaultValue = "") String ma,
//            @RequestParam(defaultValue = "") String trangThai,
//            Pageable pageable,
//            Model model) {
//        String trangThai1 = "";
//        String trangThai2 = "";
//        if (trangThai.equals("")) {
//            trangThai1 = "Đang hoạt động";
//            trangThai2 = "Ngừng hoạt động";
//        } else if (trangThai.equals("Đang hoạt động")) {
//            trangThai1 = "Đang hoạt động";
//            trangThai2 = "Đang hoạt động";
//        } else {
//            trangThai1 = "Ngừng hoạt động";
//            trangThai2 = "Ngừng hoạt động";
//        }
//        Page<NhaSanXuat> nhaSanXuatPage = nhaSanXuatService.listFilter(ma, trangThai1, trangThai2, pageable);
//        model.addAttribute("nhaSanXuatLists", nhaSanXuatPage);
//        System.out.println("List nhaSanXuat: " + nhaSanXuatPage.getTotalElements());
//        return "admin/san-pham/nha-san-xuat/search";
//    }
}