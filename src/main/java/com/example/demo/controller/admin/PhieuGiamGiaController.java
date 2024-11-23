package com.example.demo.controller.admin;
import com.example.demo.entity.DataMailDTO;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.KhachHangPhieuGiamGia;
import com.example.demo.entity.PhieuGiamGia;
import com.example.demo.repository.admin.KhachHangPhieuGiamGiaRepository;
import com.example.demo.repository.admin.KhachHangRepository;
import com.example.demo.repository.admin.PhieuGiamGiaRepository;
import com.example.demo.service.admin.EmailService;
import com.example.demo.service.admin.KhachHangService;
import com.example.demo.service.admin.PhieuGiamGiaService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin/phieu-giam-gia")
public class PhieuGiamGiaController {

    @Autowired
    PhieuGiamGiaRepository phieuGiamGiaRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    KhachHangPhieuGiamGiaRepository khachHangPhieuGiamGiaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private PhieuGiamGiaService phieuGiamGiaService;

//    private final EntityManager entityManager;

//    public PhieuGiamGiaController(PhieuGiamGiaRepository phieuGiamGiaRepository,
//                                  EntityManager entityManager) {
//        this.phieuGiamGiaRepository = phieuGiamGiaRepository;
//        this.entityManager = entityManager;


    @GetMapping("/hien-thi")
    public String hienthi(Model model, @RequestParam("page") Optional<Integer> page,
                          @RequestParam(required = false) java.sql.Date startDate,
                          @RequestParam(required = false) java.sql.Date endDate,
                          @RequestParam(required = false) Integer kieu,
                          @RequestParam(required = false) Integer loai,
                          @RequestParam(required = false) String trangThai,
                          @RequestParam(required = false) String magg) {
        if (magg == null){
            magg = "";
        }
        magg = "%"+magg+"%";
        int checkpage = page.orElse(0);
        int pagesize = 5;
        checkpage = Math.max(checkpage, 0);
        Pageable pageable = PageRequest.of(checkpage, pagesize);

        if(startDate == null || endDate == null){
            startDate = java.sql.Date.valueOf("2000-01-01");
            endDate = java.sql.Date.valueOf("2100-01-01");
        }

        Page<PhieuGiamGia> results = phieuGiamGiaRepository.searchPGG(startDate, endDate, kieu, loai, trangThai, magg, pageable);
        model.addAttribute("listsPGG", results);
        model.addAttribute("phieuGiamGia", results);


        return "admin/phieu-giam-gia/phieu-giam-gia";
    }

    @ModelAttribute("khpgg")
    List<KhachHangPhieuGiamGia> getkhPGG() {
        return khachHangPhieuGiamGiaRepository.findAll();
    }
//    @ModelAttribute("maPGG")
//    String getkhPGG() {
//        return "";
//    }

    @GetMapping("/tao-san-pham")
    public String hienThiPGG(Model model, @RequestParam("page") Optional<Integer> page,
                             @RequestParam(required = false) String search) {
        if(search == null){
            search = "";
        }
        search = "%"+search+"%";
        int checkpage = page.orElse(0);
        int pagesize = 5;
        checkpage = Math.max(checkpage, 0);
        Pageable pageable = PageRequest.of(checkpage, pagesize);
        Page<KhachHang> khachHangPage = khachHangRepository.findByParam(search,pageable);

        model.addAttribute("listsKH", khachHangPage);
        PhieuGiamGia phieuGiamGia = new PhieuGiamGia();
        phieuGiamGia.setMa(phieuGiamGiaService.generateRandomCode());
        model.addAttribute("phieuGiamGia", phieuGiamGia);


        return "admin/phieu-giam-gia/create-pgg";
    }


    @PostMapping("/add")
    public String addPGG(@Valid PhieuGiamGia phieuGiamGia, BindingResult bindingResult,
                         @RequestParam(name = "khachHang", required = true, defaultValue = "-1") List<KhachHang> selectedItemsIds,
                         Model model, @RequestParam("page") Optional<Integer> page
            , @RequestParam("sotiengiam") String sotiengiam
            , @RequestParam("giamToiDa") String giamToiDa) throws MessagingException {


        if (bindingResult.hasErrors()) {

            if (phieuGiamGia.getHinhThucGiam() == null) {
                model.addAttribute("errorSoTienGiam", "Bạn Chưa chọn Kiểu Giảm giá");
                model.addAttribute("phieuGiamGia", phieuGiamGia);
                int checkpage = page.orElse(0);
                int pagesize = 3;
                checkpage = Math.max(checkpage, 0);
                Pageable pageable = PageRequest.of(checkpage, pagesize);
                Page<KhachHang> khachHangPage = khachHangRepository.findAll(pageable);

                model.addAttribute("listsKH", khachHangPage);
                return "admin/phieu-giam-gia/create-pgg";
            }

            if (sotiengiam.trim().isEmpty()) {
                model.addAttribute("errorSoTienGiam", "Bạn chưa điền giá trị giảm");
            } else {
                try {
                    String soTienGiamStr = sotiengiam; // Giả sử giá trị là chuỗi
                    int soTienGiam = Integer.parseInt(soTienGiamStr); // Chuyển đổi chuỗi sang số nguyên

                    if (soTienGiam <= 0) {
                        model.addAttribute("errorSoTienGiam", "Số tiền phải lớn hơn 0");
                    }
                    if (phieuGiamGia.getGiaTriDonToiThieu() < soTienGiam) {
                        model.addAttribute("errorSoTienGiam", "Số tiền giảm phải nhỏ hơn giá trị đơn tối thiếu");
                    }
                } catch (NumberFormatException e) {
                    model.addAttribute("errorSoTienGiam", "Số tiền không hợp lệ. Vui lòng nhập số.");
                    model.addAttribute("phieuGiamGia", phieuGiamGia);
                    int checkpage = page.orElse(0);
                    int pagesize = 3;
                    checkpage = Math.max(checkpage, 0);
                    Pageable pageable = PageRequest.of(checkpage, pagesize);
                    Page<KhachHang> khachHangPage = khachHangRepository.findAll(pageable);

                    model.addAttribute("listsKH", khachHangPage);
                    return "admin/phieu-giam-gia/create-pgg";
                } catch (Exception e) {
                    model.addAttribute("errorMessage", "Đã xảy ra lỗi khi xử lý: " + e.getMessage());
                    model.addAttribute("phieuGiamGia", phieuGiamGia);
                    int checkpage = page.orElse(0);
                    int pagesize = 3;
                    checkpage = Math.max(checkpage, 0);
                    Pageable pageable = PageRequest.of(checkpage, pagesize);
                    Page<KhachHang> khachHangPage = khachHangRepository.findAll(pageable);

                    model.addAttribute("listsKH", khachHangPage);
                    return "admin/phieu-giam-gia/create-pgg";
                }
            }
            if (phieuGiamGia.getHinhThucGiam() == 1) {

                try {
                    int soTienGiam = Integer.parseInt(sotiengiam); // Chuyển đổi chuỗi sang số nguyên
                    if (soTienGiam < 1 || soTienGiam > 99) {
                        model.addAttribute("errorPhanTramGiam", "Phần trăm giảm chỉ khoảng 1% - 99%");
                    }
                    if (giamToiDa.trim().isEmpty()) {
                        model.addAttribute("errorGiamToiDa", "Bạn chưa điền giá trị giảm tối đa");
                    }
                    String soTienGiamStr = giamToiDa; // Giả sử giá trị là chuỗi
                    int giamtd = Integer.parseInt(soTienGiamStr); // Chuyển đổi chuỗi sang số nguyên
                    if (giamtd <= 0) {
                        model.addAttribute("errorGiamToiDa", "Số tiền giảm tối đa phải lớn hơn 0");
                    }

                } catch (NumberFormatException e) {
                    model.addAttribute("errorGiamToiDa", "Số tiền giảm tối đa không hợp lệ. Vui lòng nhập số.");
                    model.addAttribute("phieuGiamGia", phieuGiamGia);
                    int checkpage = page.orElse(0);
                    int pagesize = 3;
                    checkpage = Math.max(checkpage, 0);
                    Pageable pageable = PageRequest.of(checkpage, pagesize);
                    Page<KhachHang> khachHangPage = khachHangRepository.findAll(pageable);

                    model.addAttribute("listsKH", khachHangPage);
                    return "admin/phieu-giam-gia/create-pgg";
                } catch (Exception e) {
                    model.addAttribute("errorMessage", "Đã xảy ra lỗi khi xử lý: " + e.getMessage());
                    model.addAttribute("phieuGiamGia", phieuGiamGia);
                    int checkpage = page.orElse(0);
                    int pagesize = 3;
                    checkpage = Math.max(checkpage, 0);
                    Pageable pageable = PageRequest.of(checkpage, pagesize);
                    Page<KhachHang> khachHangPage = khachHangRepository.findAll(pageable);

                    model.addAttribute("listsKH", khachHangPage);
                    return "admin/phieu-giam-gia/create-pgg";
                }
            }

            if (phieuGiamGia.getNgayBatDau() != null && phieuGiamGia.getNgayKetThuc() != null) {
                LocalDateTime now = LocalDateTime.now();
                if (phieuGiamGia.getNgayBatDau().isAfter(phieuGiamGia.getNgayKetThuc())) {
                    model.addAttribute("errorKetThuc", "Không được để ngày bắt đầu hơn ngày kết thúc");
                } else if (phieuGiamGia.getNgayKetThuc().isBefore(now)) {
                    model.addAttribute("errorKetThucMinNow", "Không được để ngày kết thúc bé hơn hôm hiện tại");
                }
            }

            model.addAttribute("phieuGiamGia", phieuGiamGia);
            int checkpage = page.orElse(0);
            int pagesize = 3;
            checkpage = Math.max(checkpage, 0);
            Pageable pageable = PageRequest.of(checkpage, pagesize);
            Page<KhachHang> khachHangPage = khachHangRepository.findAll(pageable);

            model.addAttribute("listsKH", khachHangPage);
            return "admin/phieu-giam-gia/create-pgg";
        }

        phieuGiamGia.setNgaySua(new Date());
        phieuGiamGia.setNgayTao(new Date());
        phieuGiamGia.setNguoiTao("Admin");

        if (phieuGiamGia.getHinhThucGiam() == 0) {
            phieuGiamGia.setSoTienGiam(Double.parseDouble(sotiengiam));
        } else {
            phieuGiamGia.setPhanTramGiam(Double.parseDouble(sotiengiam));
            phieuGiamGia.setSoTienGiam(null);
        }

        LocalDateTime now = LocalDateTime.now();
        if (phieuGiamGia.getNgayBatDau() == null || phieuGiamGia.getNgayKetThuc() == null) {
            now = null;
        } else {
            if (now.isBefore(phieuGiamGia.getNgayBatDau())) {
                phieuGiamGia.setTrangThai("Sắp diễn ra");
            } else if (now.isAfter(phieuGiamGia.getNgayBatDau()) && now.isBefore(phieuGiamGia.getNgayKetThuc())) {
                phieuGiamGia.setTrangThai("Đang diễn ra");
            }

        }

        phieuGiamGiaRepository.save(phieuGiamGia);
        KhachHangPhieuGiamGia khachHangPhieuGiamGia = new KhachHangPhieuGiamGia();

        if (phieuGiamGia.getHinhThucSuDung() == 0) {
            selectedItemsIds.remove(0);
        }

        for (int i = 0; i < selectedItemsIds.size(); i++) {
            khachHangPhieuGiamGia = new KhachHangPhieuGiamGia();
            khachHangPhieuGiamGia.setKhachHang(selectedItemsIds.get(i));
            khachHangPhieuGiamGia.setGiamGia(phieuGiamGia);
            khachHangPhieuGiamGiaRepository.save(khachHangPhieuGiamGia);

            try {
                DataMailDTO dataMail = new DataMailDTO();
                dataMail.setTo(selectedItemsIds.get(i).getEmail());
                dataMail.setSubject("[ChinShoes] Bạn đã nhận được một phiếu giảm giá ✅");
                Map<String, Object> props = new HashMap<>();
                props.put("pgg", phieuGiamGia);
                props.put("kh", selectedItemsIds.get(i));
                dataMail.setProps(props);
                emailService.sendHtmlMail(dataMail, "admin/phieu-giam-gia/email-template-pgg");

            } catch (Exception e) {
                System.out.println(selectedItemsIds.get(i).getEmail() + " của khách hàng có sdt " + selectedItemsIds.get(i).getSdt() + " bị lỗi");
            }

        }
        return "redirect:/admin/phieu-giam-gia/hien-thi";

    }


//    @GetMapping("/search")
//    public String search(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//                         @RequestParam(required = false) Integer kieu,
//                         @RequestParam(required = false) Integer loai,
//                         @RequestParam(required = false) String trangThai,
//                         @RequestParam("page") Optional<Integer> page,
//                         Model model) {
//        Pageable pageable = PageRequest.of(page.orElse(0), 5);
//        Page<PhieuGiamGia> results = phieuGiamGiaRepository.searchPGG(startDate, endDate, kieu, loai, trangThai, pageable);
//        model.addAttribute("listsPGG", results);
//        model.addAttribute("phieuGiamGia", results);
//
//        return "admin/phieu-giam-gia/phieu-giam-gia";
//
//    }

    @PostMapping("/toggle-status/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> toggleStatus(@PathVariable int id) {
        String newStatus = phieuGiamGiaService.toggleStatus(id);
        Map<String, String> response = new HashMap<>();
        response.put("newStatus", newStatus);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/update/{id}")
    public String viewUpdate(@PathVariable("id") Integer id, Model model, @RequestParam("page") Optional<Integer> page,
                             @RequestParam(required = false) String search) {
        PhieuGiamGia phieuGiamGia = phieuGiamGiaRepository.getReferenceById(id);
        if (phieuGiamGia.getPhanTramGiam() != null) {
            phieuGiamGia.setSoTienGiam(phieuGiamGia.getPhanTramGiam());
        }
        model.addAttribute("phieuGiamGia", phieuGiamGia);
        int checkpage = page.orElse(0);
        int pagesize = 3;
        Pageable pageable = PageRequest.of(checkpage, pagesize);
        if(search == null){
            search = "";
        }
        search = "%"+search+"%";
        Page<KhachHang> listkh = khachHangRepository.findByParam(search, pageable);

        model.addAttribute("listsKH", listkh);
        model.addAttribute("idpgg", id);
        return "admin/phieu-giam-gia/update-pgg";
    }

    private Page<KhachHang> convertList(List<KhachHang> khachHangList, Pageable pageable) {
        // Tổng số phần tử trong danh sách
        int totalElements = khachHangList.size();

        // Tính toán chỉ số bắt đầu và kết thúc cho danh sách con
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), totalElements);

        // Lấy danh sách con cho trang hiện tại
        List<KhachHang> pagedList = khachHangList.subList(start, end);

        // Tạo Page từ danh sách đã phân trang
        return new PageImpl<>(pagedList, pageable, totalElements);
    }


    @PostMapping("/update/{id}")
    public String update(@Valid PhieuGiamGia phieuGiamGia, BindingResult bindingResult,
                         @RequestParam(name = "khachHang", required = true, defaultValue = "-1") List<KhachHang> selectedItemsIds,
                         Model model, @RequestParam("page") Optional<Integer> page,

                         @PathVariable Integer id
    )
            throws MessagingException {


        if (bindingResult.hasErrors()) {

            if (phieuGiamGia.getHinhThucGiam() == null) {
                model.addAttribute("errorSoTienGiam", "Bạn Chưa chọn Kiểu Giảm giá");
                model.addAttribute("phieuGiamGia", phieuGiamGia);
                int checkpage = page.orElse(0);
                int pagesize = 3;
                checkpage = Math.max(checkpage, 0);
                Pageable pageable = PageRequest.of(checkpage, pagesize);
                Page<KhachHang> khachHangPage = khachHangRepository.findAll(pageable);

                model.addAttribute("listsKH", khachHangPage);
                return "admin/phieu-giam-gia/create-pgg";
            }



            if (phieuGiamGia.getNgayBatDau() != null && phieuGiamGia.getNgayKetThuc() != null) {
                LocalDateTime now = LocalDateTime.now();
                if (phieuGiamGia.getNgayBatDau().isAfter(phieuGiamGia.getNgayKetThuc())) {
                    model.addAttribute("errorKetThuc", "Không được để ngày bắt đầu hơn ngày kết thúc");
                } else if (phieuGiamGia.getNgayKetThuc().isBefore(now)) {
                    model.addAttribute("errorKetThucMinNow", "Không được để ngày kết thúc bé hơn hôm hiện tại");
                }
            }

            model.addAttribute("phieuGiamGia", phieuGiamGia);
            int checkpage = page.orElse(0);
            int pagesize = 3;
            checkpage = Math.max(checkpage, 0);
            Pageable pageable = PageRequest.of(checkpage, pagesize);
            Page<KhachHang> khachHangPage = khachHangRepository.findAll(pageable);

            model.addAttribute("listsKH", khachHangPage);
            return "admin/phieu-giam-gia/update-pgg";
        }

        phieuGiamGia.setNgaySua(new Date());
        phieuGiamGia.setNgayTao(new Date());
        phieuGiamGia.setNguoiTao("Admin");

        if (phieuGiamGia.getHinhThucGiam() == 0) {
            phieuGiamGia.setSoTienGiam(phieuGiamGia.getSoTienGiam());

        } else {
            phieuGiamGia.setPhanTramGiam(phieuGiamGia.getSoTienGiam());
            phieuGiamGia.setSoTienGiam(null);
        }

        LocalDateTime now = LocalDateTime.now();
        if (phieuGiamGia.getNgayBatDau() == null || phieuGiamGia.getNgayKetThuc() == null) {
            now = null;
        } else {
            if (now.isBefore(phieuGiamGia.getNgayBatDau())) {
                phieuGiamGia.setTrangThai("Sắp diễn ra");
            } else if (now.isAfter(phieuGiamGia.getNgayBatDau()) && now.isBefore(phieuGiamGia.getNgayKetThuc())) {
                phieuGiamGia.setTrangThai("Đang diễn ra");
            } else {
                phieuGiamGia.setTrangThai("Kết thúc");
            }

        }
        PhieuGiamGia pgg = phieuGiamGiaRepository.getReferenceById(id);
        pgg.setSoLuong(phieuGiamGia.getSoLuong());
        pgg.setNgayBatDau(phieuGiamGia.getNgayBatDau());
        pgg.setNgayKetThuc(phieuGiamGia.getNgayKetThuc());
        pgg.setTrangThai(phieuGiamGia.getTrangThai());
        pgg.setHinhThucSuDung(phieuGiamGia.getHinhThucSuDung());

        phieuGiamGiaRepository.save(pgg);

        KhachHangPhieuGiamGia khachHangPhieuGiamGia;

        List<KhachHang> checkKHPGG = khachHangPhieuGiamGiaRepository.getKhachHangPhieuGiamGiaByid(id);

        if (phieuGiamGia.getHinhThucSuDung() == 0) {
            selectedItemsIds.remove(0);
        }

        for (int i = 0; i < selectedItemsIds.size(); i++) {
            for (KhachHang kh1 : checkKHPGG) {
                if (kh1 != selectedItemsIds.get(i)) {
                    khachHangPhieuGiamGia = new KhachHangPhieuGiamGia();
                    khachHangPhieuGiamGia.setKhachHang(selectedItemsIds.get(i));
                    khachHangPhieuGiamGia.setGiamGia(phieuGiamGia);
                    khachHangPhieuGiamGiaRepository.save(khachHangPhieuGiamGia);

                    try {
                        DataMailDTO dataMail = new DataMailDTO();
                        dataMail.setTo(selectedItemsIds.get(i).getEmail());
                        dataMail.setSubject("[ChinShoes] Bạn đã nhận được một phiếu giảm giá ✅");
                        Map<String, Object> props = new HashMap<>();
                        props.put("pgg", phieuGiamGia);
                        props.put("kh", selectedItemsIds.get(i));
                        dataMail.setProps(props);
                        emailService.sendHtmlMail(dataMail, "admin/phieu-giam-gia/email-template-pgg");

                    } catch (Exception e) {
                        System.out.println(selectedItemsIds.get(i).getEmail() + " của khách hàng có sdt " + selectedItemsIds.get(i).getSdt() + " bị lỗi");
                    }

                }
            }

        }
        return "redirect:/admin/phieu-giam-gia/hien-thi";
    }

    @GetMapping("/doi-trang-thai")
    public String khoaPgg(@RequestParam Integer id, HttpServletRequest request){
        PhieuGiamGia pgg = phieuGiamGiaRepository.findById(id).get();
        if(pgg.getBlock() == null){
            if(pgg.getTrangThai().equalsIgnoreCase("Đang diễn ra") || pgg.getTrangThai().equalsIgnoreCase("Sắp diễn ra")){
                pgg.setTrangThai("Kết thúc");
            }
            pgg.setBlock(true);
        }
        else if(pgg.getBlock() == false){
            if(pgg.getTrangThai().equalsIgnoreCase("Đang diễn ra") || pgg.getTrangThai().equalsIgnoreCase("Sắp diễn ra")){
                pgg.setTrangThai("Kết thúc");
            }
            pgg.setBlock(true);
        }
        else if(pgg.getBlock() == true){
            pgg.setBlock(false);
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(pgg.getNgayBatDau())) {
                pgg.setTrangThai("Sắp diễn ra");
            } else if (now.isAfter(pgg.getNgayBatDau()) && now.isBefore(pgg.getNgayKetThuc())) {
                pgg.setTrangThai("Đang diễn ra");
            }else {
                pgg.setTrangThai("Kết thúc");
            }
        }
        phieuGiamGiaRepository.save(pgg);
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }
}
