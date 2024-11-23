package com.example.demo.controller.user;

import com.example.demo.entity.KhachHang;
import com.example.demo.repository.admin.KhachHangRepository;
import com.example.demo.service.admin.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class QuenMatKhauController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/quen-mat-khau")
    public String viewPage(){
        return "user/dang-nhap/quenmatkhau";
    }


    @PostMapping("/quen-mat-khau")
    public String action(@RequestParam String email, RedirectAttributes redirectAttributes){
        Optional<KhachHang> khachHang = khachHangRepository.findByEmail(email);
        if(khachHang.isEmpty()){
            redirectAttributes.addFlashAttribute("error","Email không tồn tại");
            return "redirect:/quen-mat-khau";
        }
        String otp = genOtp();
        khachHang.get().setOtpQuenMatKhau(otp);
        khachHangRepository.save(khachHang.get());
        emailService.sendEmail(khachHang.get().getEmail(), "Quên mật khẩu","Click vào link sau để đặt lại mật khẩu: http://localhost:8080/dat-lai-mat-khau?email="+khachHang.get().getEmail()+"&key="+otp);
        return "redirect:/login";
    }


    @GetMapping("/dat-lai-mat-khau")
    public String datLaiMatKhauView(){
        return "user/dang-nhap/datlaimatkhau";
    }

    @PostMapping("/dat-lai-mat-khau")
    public String datLaiMatKhauPost(@RequestParam String emailpost, @RequestParam String keypost,
                                    @RequestParam String password,RedirectAttributes redirectAttributes){
        Optional<KhachHang> khachHang = khachHangRepository.findByEmail(emailpost);
        if(!keypost.equals(khachHang.get().getOtpQuenMatKhau())){
            redirectAttributes.addFlashAttribute("error","Mã xác nhận không chính xác");
            return "redirect:/dat-lai-mat-khau";
        }
        khachHang.get().setMatKhau(passwordEncoder.encode(password));
        khachHang.get().setOtpQuenMatKhau(null);
        khachHangRepository.save(khachHang.get());
        return "redirect:/login";
    }

    public String genOtp(){
        String str = "12345667890";
        Integer length = str.length()-1;
        StringBuilder stringBuilder = new StringBuilder("");
        for(int i=0; i<6; i++){
            Integer ran = (int)(Math.random()*length);
            stringBuilder.append(str.charAt(ran));
        }
        return String.valueOf(stringBuilder);
    }
}
