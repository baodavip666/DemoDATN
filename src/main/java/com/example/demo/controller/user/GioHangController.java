package com.example.demo.controller.user;

import com.example.demo.entity.GioHang;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.KhachHang;
import com.example.demo.repository.user.GioHangChiTietRepository;
import com.example.demo.repository.user.GioHangRepository;
import com.example.demo.service.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class GioHangController {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private UserUtils userUtils;

    @GetMapping(value = "/giohang")
    public String GioHang(Model model) {
        KhachHang khachHang = userUtils.getKhachHangWithAuthority();
        if(khachHang != null){
            GioHang gioHang = gioHangRepository.findByKhachHang(khachHang.getId()).get();
            List<GioHangChiTiet> list = gioHangChiTietRepository.findByGioHang(gioHang.getId());
            Double tongTien = 0D;
            for(GioHangChiTiet g : list){
                tongTien += g.getSoLuong() * g.getSanPhamChiTiet().getDonGia();
            }
            model.addAttribute("listcart", list);
            model.addAttribute("tongTien", tongTien);
        }
        return "user/gio-hang/giohang";
    }

    @GetMapping("/tang-giam-sl")
    public String tangGiamSoLuong(@RequestParam Integer id, @RequestParam Integer soLuong, HttpServletRequest request){
        GioHangChiTiet ghct = gioHangChiTietRepository.findById(id).get();
        ghct.setSoLuong(ghct.getSoLuong() + soLuong);
        if(ghct.getSoLuong() == 0){
            gioHangChiTietRepository.deleteById(id);
        }
        else {
            gioHangChiTietRepository.save(ghct);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


    @GetMapping("/delete-giohang")
    public String deleteDonHang(RedirectAttributes redirectAttributes, @RequestParam("id") Integer id){
        gioHangChiTietRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa giỏ hàng thành công!");
        return "redirect:giohang";
    }
}
