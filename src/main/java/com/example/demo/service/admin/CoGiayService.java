package com.example.demo.service.admin;

import com.example.demo.entity.CoGiay;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Random;

public interface CoGiayService {


    List<CoGiay> getAll();

    String create(@Valid CoGiay coGiay, BindingResult bindingResult, Model model);

    String update(@Valid CoGiay coGiay, BindingResult bindingResult, Model model);

    String delete(int id);

    CoGiay findById(int id);

    Page<CoGiay> getAllActive(Pageable pageable);

    default String generateRandomCode() {
        int length = 7;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return "CG-" + sb.toString();
    }

    @Transactional
    void toggleStatus(int id);

    Page<CoGiay> listFilter(String ma, String trangThai1, String trangThai2, Pageable pageable);

    List<CoGiay> getListActiveCoGiays();

}