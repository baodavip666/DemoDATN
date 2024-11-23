package com.example.demo.api;

import com.example.demo.entity.Disctrict;
import com.example.demo.entity.Province;
import com.example.demo.entity.Ward;
import com.example.demo.service.admin.impl.GhnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressApi {

    @Autowired
    private GhnService ghnService;

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
}
