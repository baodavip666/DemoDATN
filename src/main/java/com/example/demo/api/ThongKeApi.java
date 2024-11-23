package com.example.demo.api;

import com.example.demo.dto.ThongKeThang;
import com.example.demo.service.admin.impl.ThongKeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/thong-ke")
public class ThongKeApi {

    @Autowired
    private ThongKeImpl statisticsService;

    @GetMapping("/public/thong-ke-nam")
    public ResponseEntity<?> getListDetail(@RequestParam Integer nam){
        List<Long> soluongdonhang = statisticsService.thongKeDonHangNam(nam);
        List<Long> soluongsp = statisticsService.soLuongSanPhamBanTheoNam(nam);
        List<Long>[] arr = new List[2];
        arr[0] = soluongdonhang;
        arr[1] = soluongsp;
        return new ResponseEntity<>(arr, HttpStatus.OK);
    }

    @GetMapping("/public/thong-ke-thang")
    public ResponseEntity<?> thongKeTheoThang(@RequestParam Integer nam, @RequestParam Integer thang){
        List<ThongKeThang> soluongdonhang = statisticsService.thongKeDonHangThangNam(nam, thang);
        List<ThongKeThang> soluongsp = statisticsService.thongKeSanPhamThangNam(nam, thang);
        List<ThongKeThang>[] arr = new List[2];
        arr[0] = soluongdonhang;
        arr[1] = soluongsp;
        return new ResponseEntity<>(arr, HttpStatus.OK);
    }

    @GetMapping("/public/thong-ke-ngay")
    public ResponseEntity<?> thongKeTheoNgay(@RequestParam java.sql.Date start, @RequestParam java.sql.Date end){
        List<ThongKeThang> soluongdonhang = statisticsService.thongKeDonHangNgay(new java.util.Date(start.getTime()), new java.util.Date(end.getTime()));
        List<ThongKeThang> soluongsp = statisticsService.thongKeSanPhamNgay(start, end);
        List<ThongKeThang>[] arr = new List[2];
        arr[0] = soluongdonhang;
        arr[1] = soluongsp;
        return new ResponseEntity<>(arr, HttpStatus.OK);
    }

    @GetMapping("/public/doanh-thu-nam")
    public ResponseEntity<?> doanhThuNam(@RequestParam Integer nam){
        List<Long> list = statisticsService.doanhThuNam(nam);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/public/tang-truong-doanh-thu")
    public ResponseEntity<?> tangTruongDoanhThu(@RequestParam Integer nam){
        List<Float> list = statisticsService.tangTruongDoanhThu(nam);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/public/don-huy-thanh-cong")
    public ResponseEntity<?> donHuyVaThanhCong(){
        int[] list = statisticsService.donHuyVaThanhCong();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
