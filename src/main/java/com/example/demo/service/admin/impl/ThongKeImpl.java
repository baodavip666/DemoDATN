package com.example.demo.service.admin.impl;


import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.ThongKeThang;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.repository.admin.HoaDonChiTietRepository;
import com.example.demo.repository.admin.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ThongKeImpl {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    public List<Map<String, Object>> getTopCustomersByPurchases() {
        // Lấy danh sách top khách hàng đã mua hàng
        List<Object[]> allCustomers = hoaDonRepository.findTopCustomersByPurchases();

        Date currentDate = new Date();

        // Tạo một lịch để lấy tháng và năm hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
        // Lọc và tính tổng số lượng cho tháng hiện tại
        return allCustomers.stream()
                .filter(customer -> {
                    // Lấy tên khách hàng
                    String customerName = (String) customer[0];

                    // Kiểm tra xem hóa đơn nào có ngày tạo trong tháng hiện tại
                    return hoaDonRepository.findAll().stream()
                            .filter(hd -> hd.getKhachHang() != null && hd.getKhachHang().getTen().equals(customerName)) // Kiểm tra null ở đây
                            .anyMatch(hd -> {
                                // Lấy ngày tạo hóa đơn
                                Calendar saleCalendar = Calendar.getInstance();
                                saleCalendar.setTime(hd.getNgayTao());
                                return saleCalendar.get(Calendar.YEAR) == currentYear &&
                                        saleCalendar.get(Calendar.MONTH) + 1 == currentMonth;
                            });
                })
                .limit(3) // Giới hạn kết quả chỉ lấy 5 khách hàng
                .map(customer -> Map.of(
                        "name", customer[0],
                        "purchaseCount", customer[1],
                        "totalAmount", customer[2]
                ))
                .collect(Collectors.toList());
    }

    public List<Object[]> getTopSellingProductsForCurrentMonth() {
        return hoaDonChiTietRepository.findTopSellingProducts();
    }

    private String formatIntegerNumber(Integer value) {
        NumberFormat numberFormat = NumberFormat.getIntegerInstance(new Locale("vi", "VN"));
        return value != null ? numberFormat.format(value) : "0";
    }

    public Integer getTotalQuantity() {
        return hoaDonChiTietRepository.findTotalQuantity();
    }

    public String getTotalRevenue() {
        Double totalRevenue = hoaDonRepository.findTotalRevenue();
        Integer revenueAsInteger = totalRevenue != null ? (int) Math.round(totalRevenue) : 0;
        return formatIntegerNumber(revenueAsInteger);
    }


    public Map<String, Object> getRevenueStatsForView() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalQuantity", formatIntegerNumber(getTotalQuantity()));
        stats.put("totalRevenue", getTotalRevenue());
        return stats;
    }

    public String getFormattedTodayRevenue() {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        Double revenue = hoaDonRepository.getTodayRevenue(startOfDay, endOfDay);
        revenue = revenue != null ? revenue : 0.0;
        Integer revenueAsInteger = (int) Math.round(revenue);
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        return numberFormat.format(revenueAsInteger);
    }

    public Map<Integer, Double> getMonthlyRevenue() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        // Lấy ngày đầu tiên và ngày cuối cùng của năm hiện tại
        calendar.set(currentYear, Calendar.JANUARY, 1, 0, 0, 0);
        Date startOfYear = calendar.getTime();

        calendar.set(currentYear, Calendar.DECEMBER, 31, 23, 59, 59);
        Date endOfYear = calendar.getTime();

        // Lấy tất cả các bản ghi trong năm hiện tại
        List<HoaDonChiTiet> allInCurrentYear = hoaDonChiTietRepository.findAllInYear(startOfYear, endOfYear);

        // Tính tổng doanh thu theo tháng
        Map<Integer, Double> monthlyRevenue = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthlyRevenue.put(i, 0.0);
        }
        for (HoaDonChiTiet hdct : allInCurrentYear) {
            calendar.setTime(hdct.getNgayTao());
            int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH trả về 0-11
            double revenue = hdct.getSoLuong() * hdct.getDonGia();
            monthlyRevenue.put(month, monthlyRevenue.get(month) + revenue);
        }

        return monthlyRevenue;
    }

    public List<Object[]> thongKeTheoThang() {
        List<Object[]> results = hoaDonChiTietRepository.thongKeTheoThang();
        List<Object[]> thongKeList = new ArrayList<>();

        // Khởi tạo tất cả 12 tháng với giá trị mặc định
        for (int i = 1; i <= 12; i++) {
            thongKeList.add(new Object[]{i, 0, 0L}); // Tháng, Số hóa đơn, Tổng số lượng sản phẩm
        }

        // Gắn kết quả truy vấn vào danh sách
        for (Object[] row : results) {
            Integer thang = (Integer) row[0];
            Long soHoaDon = (Long) row[1];
            Long tongSoLuongSanPham = (Long) row[2];
            thongKeList.set(thang - 1, new Object[]{thang, soHoaDon.intValue(), tongSoLuongSanPham});
        }

        // Trả về danh sách đã sắp xếp theo tháng
        return thongKeList;
    }


    public List<Long> thongKeDonHangNam(Integer nam){
        List<Long> list = new ArrayList<>();
        for(int i=1; i< 13; i++){
            Long sum = hoaDonRepository.thongKeDonHangNam(i, nam);
            if(sum == null){
                sum = 0L;
            }
            list.add(sum);
        }
        return list;
    }

    public List<Long> soLuongSanPhamBanTheoNam(Integer nam){
        List<Long> list = new ArrayList<>();
        for(int i=1; i< 13; i++){
            Long sum = hoaDonChiTietRepository.thongKeDonHangNam(i, nam);
            if(sum == null){
                sum = 0L;
            }
            list.add(sum);
        }
        return list;
    }

    public List<ThongKeThang> thongKeDonHangThangNam(Integer nam, Integer thang){
        List<ThongKeThang> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, nam);
        calendar.set(Calendar.MONTH, thang - 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int day = 1; day <= daysInMonth; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            Date date = calendar.getTime();
            Long soLuong = hoaDonRepository.thongKeTheoNgay(date);
            ThongKeThang thongKeThang = new ThongKeThang();
            thongKeThang.setNgay(date);
            thongKeThang.setSoLuong(soLuong);
            list.add(thongKeThang);
        }
        return list;
    }


    public List<ThongKeThang> thongKeSanPhamThangNam(Integer nam, Integer thang){
        List<ThongKeThang> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, nam);
        calendar.set(Calendar.MONTH, thang - 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int day = 1; day <= daysInMonth; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            Date date = calendar.getTime();
            Long soLuong = hoaDonChiTietRepository.thongKeTheoNgay(date);
            ThongKeThang thongKeThang = new ThongKeThang();
            thongKeThang.setNgay(date);
            thongKeThang.setSoLuong(soLuong);
            list.add(thongKeThang);
        }
        return list;
    }

    public List<ThongKeThang> thongKeDonHangNgay(Date start, Date end){
        List<ThongKeThang> list = new ArrayList<>();

        Long ls = (end.getTime() - start.getTime()) ;
        Long soNgay = ls / (1000L * 60L * 60L * 24L);
        for (int i = 0; i <= soNgay; i++) {
            Date date = new Date(start.getTime() + (1000L * 60L * 60L * 24L) * i);
            Long soLuong = hoaDonRepository.thongKeTheoNgay(date);
            ThongKeThang thongKeThang = new ThongKeThang();
            thongKeThang.setNgay(date);
            thongKeThang.setSoLuong(soLuong);
            list.add(thongKeThang);
        }
        return list;
    }

    public List<ThongKeThang> thongKeSanPhamNgay(Date start, Date end){
        List<ThongKeThang> list = new ArrayList<>();
        Long ls = (end.getTime() - start.getTime()) ;
        Long soNgay = ls / (1000L * 60L * 60L * 24L);
        for (int i = 0; i <= soNgay; i++) {
            Date date = new Date(start.getTime() + (1000L * 60L * 60L * 24L) * i);
            Long soLuong = hoaDonChiTietRepository.thongKeTheoNgay(date);
            ThongKeThang thongKeThang = new ThongKeThang();
            thongKeThang.setNgay(date);
            thongKeThang.setSoLuong(soLuong);
            list.add(thongKeThang);
        }
        return list;
    }



    public Double calculateRevenueGrowthPercentage() {
        // Lấy ngày hiện tại
        Date currentDate = new Date();

        // Lấy ngày hôm trước
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -1);
        Date previousDate = calendar.getTime();

        // Lấy doanh thu của cả hai ngày
        Double todayRevenue = hoaDonChiTietRepository.getTotalRevenueByDate(currentDate);
        Double yesterdayRevenue = hoaDonChiTietRepository.getTotalRevenueByPreviousDate(previousDate);

        // Nếu giá trị null, đặt là 0 để tránh lỗi NullPointerException
        todayRevenue = todayRevenue != null ? todayRevenue : 0.0;
        yesterdayRevenue = yesterdayRevenue != null ? yesterdayRevenue : 0.0;

        // Tính toán phần trăm tăng trưởng
        if (yesterdayRevenue == 0) {
            return todayRevenue > 0 ? 100.0 : 0.0; // Nếu không có doanh thu hôm qua, tăng trưởng là 100% nếu hôm nay có doanh thu
        } else {
            return ((todayRevenue - yesterdayRevenue) / yesterdayRevenue) * 100;
        }
    }

    public List<CustomerDto> khachHangMoi(){
        return hoaDonRepository.top3Customer();
    }


    public List<Long> doanhThuNam(Integer nam){
        List<Long> list = new ArrayList<>();
        for(int i=1; i< 13; i++){
            Long sum = hoaDonRepository.doanhThuNam(i, nam);
            if(sum == null){
                sum = 0L;
            }
            list.add(sum);
        }
        return list;
    }

    public List<Float> tangTruongDoanhThu(Integer nam){
        List<Long> list = new ArrayList<>();
        for(int i=1; i< 13; i++){
            Long sum = hoaDonRepository.doanhThuNam(i, nam);
            if(sum == null){
                sum = 0L;
            }
            list.add(sum);
        }
        List<Float> result = new ArrayList<>();
        result.add(0F);
        for(int i=1; i<list.size(); i++){
            System.out.println(list.get(i));
            if(list.get(i-1) == 0 && list.get(i) == 0){
                result.add(0F);
            }
            else if(list.get(i-1) == 0 && list.get(i) > 0){
                result.add(100F);
            }
            else if(list.get(i-1) > 0 && list.get(i) == 0){
                result.add(0F);
            }
            else if(list.get(i-1) > 0 && list.get(i) > 0){
                Float kq = (Float.valueOf(list.get(i)) - Float.valueOf(list.get(i-1)) )/ Float.valueOf(list.get(i-1)) * 100;
                result.add(kq);
            }
            else{
                result.add(0F);
            }
        }

        return result;
    }

    public int[] donHuyVaThanhCong(){
        int[] ressult = new int[2];
        int soDonHuy = hoaDonRepository.tongDonHuy();
        int soDonNhan = hoaDonRepository.tongDonNhan();
        ressult[0] = soDonHuy;
        ressult[1] = soDonNhan;
        return ressult;
    }
}
