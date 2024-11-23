package com.example.demo.controller.user;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.PhieuGiamGia;
import com.example.demo.entity.Size;
import com.example.demo.repository.admin.KhachHangPhieuGiamGiaRepository;
import com.example.demo.repository.admin.KhachHangRepository;
import com.example.demo.repository.admin.PhieuGiamGiaRepository;
import com.example.demo.repository.user.GiamGiaRepository;
import com.example.demo.service.admin.EmailService;
import com.example.demo.service.admin.KhachHangService;
import com.example.demo.service.admin.PhieuGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;



@Controller
@RequestMapping(value = "/user")
public class GiamGiaController {
    @Autowired
    GiamGiaRepository GiamGiaRepository;

    @GetMapping("/phieugiamgia")
    public String view(Model model) {
        List<PhieuGiamGia> list = GiamGiaRepository.findALLPGG();
        model.addAttribute("listGG", list);
        return "user/giam-gia/giamgia";
    }

    @GetMapping("/phieugiamgia/timkiem")
    public String search(Model model, @RequestParam(defaultValue = "") String search1) {
        List<PhieuGiamGia> gg = GiamGiaRepository.searchGG(search1.trim());
        model.addAttribute("listGG", gg);
        return "user/giam-gia/giamgiasearch";
    }
}
