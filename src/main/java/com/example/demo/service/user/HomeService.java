package com.example.demo.service.user;

import com.example.demo.entity.SanPham;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.repository.admin.SanPhamRepository;
import com.example.demo.repository.user.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class HomeService {
    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    public List<SanPhamChiTiet> getUniqueSanPhamChiTiets() {
        List<SanPhamChiTiet> result = new ArrayList<>();
        List<SanPham> sanPhams = sanPhamRepository.findAll();
        for (SanPham sp : sanPhams) {
            List<SanPhamChiTiet> spctList = homeRepository.findBySanPham(sp.getId());
            if (!spctList.isEmpty()) {
                result.add(spctList.get(0));
            }
        }
        return result;
    }

    public List<SanPhamChiTiet> getTop5UniqueSanPhamChiTiets() {
        List<SanPhamChiTiet> result = new ArrayList<>();
        List<SanPham> sanPhams = sanPhamRepository.findAll();

        for (SanPham sp : sanPhams) {
            List<SanPhamChiTiet> spctList = homeRepository.findBySanPham(sp.getId());
            if (!spctList.isEmpty()) {
                result.add(spctList.get(0));
            }
        }

        result.sort(Comparator.comparing(SanPhamChiTiet::getNgayTao).reversed());

        return result.stream().limit(5).collect(Collectors.toList());
    }

    public List<SanPhamChiTiet> getTop5ProductSanPhamChiTiets() {
        List<SanPhamChiTiet> result = new ArrayList<>();
        List<SanPham> sanPhams = sanPhamRepository.findAll();

        for (SanPham sp : sanPhams) {
            List<SanPhamChiTiet> spctList = homeRepository.findBySanPham(sp.getId());
            if (!spctList.isEmpty()) {
                result.add(spctList.get(0));
            }
        }

        result.sort(Comparator.comparing(SanPhamChiTiet::getSoLuong).reversed());

        return result.stream().limit(5).collect(Collectors.toList());
    }
    public List<SanPhamChiTiet> getGiaSP(Double minPrice, Double maxPrice) {
        List<SanPhamChiTiet> result = new ArrayList<>();
        List<SanPhamChiTiet> sanPhamChiTiets = homeRepository.findGia(minPrice, maxPrice);

        Map<Integer, SanPhamChiTiet> uniqueSanPhamMap = new HashMap<>();
        for (SanPhamChiTiet spct : sanPhamChiTiets) {
            if (!uniqueSanPhamMap.containsKey(spct.getSanPham().getId())) {
                uniqueSanPhamMap.put(spct.getSanPham().getId(), spct);
            }
        }

        result.addAll(uniqueSanPhamMap.values());

        return result;
    }
    public List<SanPhamChiTiet> getGiaTang() {
        List<SanPhamChiTiet> result = new ArrayList<>();
        List<SanPham> sanPhams = sanPhamRepository.findAll();

        for (SanPham sp : sanPhams) {
            List<SanPhamChiTiet> spctList = homeRepository.filterSP(sp.getId());
            if (!spctList.isEmpty()) {
                result.add(spctList.get(0));
            }
        }

        result.sort(Comparator.comparing(SanPhamChiTiet::getDonGia));

        return result;
    }

    public List<SanPhamChiTiet> getGiaGiam() {
        List<SanPhamChiTiet> result = new ArrayList<>();
        List<SanPham> sanPhams = sanPhamRepository.findAll();

        for (SanPham sp : sanPhams) {
            List<SanPhamChiTiet> spctList = homeRepository.filterSP(sp.getId());
            if (!spctList.isEmpty()) {
                result.add(spctList.get(0));
            }
        }

        result.sort(Comparator.comparing(SanPhamChiTiet::getDonGia).reversed());

        return result;
    }

    public List<SanPhamChiTiet> getSearch(String nameSearch) {
        List<SanPhamChiTiet> result = new ArrayList<>();
        List<SanPham> sanPhams = homeRepository.search(nameSearch);
        for (SanPham sp : sanPhams) {
            List<SanPhamChiTiet> spctList = homeRepository.findBySanPham(sp.getId());
            if (!spctList.isEmpty()) {
                result.add(spctList.get(0));
            }
        }
        return result;
    }

    public List<SanPhamChiTiet> getGiayNam() {
        List<SanPhamChiTiet> result = new ArrayList<>();
        List<SanPham> sanPhams = homeRepository.findGiayNam();
        for (SanPham sp : sanPhams) {
            List<SanPhamChiTiet> spctList = homeRepository.findBySanPham(sp.getId());
            if (!spctList.isEmpty()) {
                result.add(spctList.get(0));
            }
        }
        return result;
    }

    public List<SanPhamChiTiet> getGiayNu() {
        List<SanPhamChiTiet> result = new ArrayList<>();
        List<SanPham> sanPhams = homeRepository.findGiayNu();
        for (SanPham sp : sanPhams) {
            List<SanPhamChiTiet> spctList = homeRepository.findBySanPham(sp.getId());
            if (!spctList.isEmpty()) {
                result.add(spctList.get(0));
            }
        }
        return result;
    }

    public List<SanPhamChiTiet> getGiayTre() {
        List<SanPhamChiTiet> result = new ArrayList<>();
        List<SanPham> sanPhams = homeRepository.findGiayTreem();
        for (SanPham sp : sanPhams) {
            List<SanPhamChiTiet> spctList = homeRepository.findBySanPham(sp.getId());
            if (!spctList.isEmpty()) {
                result.add(spctList.get(0));
            }
        }
        return result;
    }

    public List<SanPhamChiTiet> getSanPhamChiTietsBySize(Integer idsize) {
        List<SanPhamChiTiet> spctBySize = homeRepository.findSanPhamChiTietsBySize(idsize);

        Map<Integer, SanPhamChiTiet> sanPhamMap = new HashMap<>();

        for (SanPhamChiTiet spct : spctBySize) {
            Integer sanPhamId = spct.getSanPham().getId();
            if (!sanPhamMap.containsKey(sanPhamId)) {
                sanPhamMap.put(sanPhamId, spct);
            }
        }
        return new ArrayList<>(sanPhamMap.values());
    }

    public List<SanPhamChiTiet> getSanPhamChiTietsByMauSac(Integer idMauSac) {
        List<SanPhamChiTiet> spctByMS = homeRepository.findSanPhamChiTietsByMauSac(idMauSac);

        Map<Integer, SanPhamChiTiet> sanPhamMap = new HashMap<>();

        for (SanPhamChiTiet spct : spctByMS) {
            Integer sanPhamId = spct.getSanPham().getId();
            if (!sanPhamMap.containsKey(sanPhamId)) {
                sanPhamMap.put(sanPhamId, spct);
            }
        }
        return new ArrayList<>(sanPhamMap.values());
    }


    
}
