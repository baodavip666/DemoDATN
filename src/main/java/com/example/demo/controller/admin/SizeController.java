package com.example.demo.controller.admin;

import com.example.demo.entity.Size;
import com.example.demo.repository.admin.SizeRepository;
import com.example.demo.service.admin.SizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("admin/san-pham/size")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @Autowired
    private SizeRepository sizeRepository;

    @GetMapping("view")
    public String searchSize(Model model, @RequestParam(defaultValue = "") String search1
            ,  @RequestParam(required = false) String trangthai, @RequestParam(name = "page") Optional<Integer> page) {
        int checkpage = page.orElse(0);
        int pagesize = 3;
        checkpage = Math.max(checkpage, 0);
        Pageable pageable = PageRequest.of(checkpage, pagesize);
        String loai1Int = null;
        if (trangthai != null && !trangthai.isEmpty()) {
            loai1Int = trangthai;
        }

        Page<Size> listSize = sizeRepository.searchSize(
                search1.trim(), loai1Int, pageable);
        model.addAttribute("sizeLists", listSize);
        model.addAttribute("search1", search1);
        model.addAttribute("trangthai", trangthai != null ? trangthai : "");
        return "admin/san-pham/size/view";
    }
    @GetMapping("create")
    public String createForm(Model model) {
        model.addAttribute("size", new Size());
        return "admin/san-pham/size/create";
    }

    @PostMapping("create")
    public String create(@Valid Size size, BindingResult bindingResult, Model model) {
        return sizeService.create(size, bindingResult, model);
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("size", sizeService.findById(id));
        return "admin/san-pham/size/detail";
    }

    @GetMapping("view-update/{id}")
    public String viewUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("size", sizeService.findById(id));
        return "admin/san-pham/size/view-update";
    }

    @PostMapping("update/{id}")
    public String update(@Valid Size size, BindingResult bindingResult, Model model) {
        return sizeService.update(size, bindingResult, model);
    }

    @GetMapping("remove/{id}")
    public String remove(@PathVariable Integer id) {
        return sizeService.delete(id);
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
//        Page<Size> sizePage = sizeService.listFilter(ma, trangThai1, trangThai2, pageable);
//        model.addAttribute("sizeLists", sizePage);
//        System.out.println("List size: " + sizePage.getTotalElements());
//        return "admin/san-pham/size/search";
//    }
}