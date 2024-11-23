package com.example.demo.service.admin.impl;

import com.example.demo.controller.admin.APIBank;
import com.example.demo.controller.admin.ApiResponse;
import com.example.demo.entity.Province;
import com.example.demo.entity.Transactions;
import com.example.demo.infrastructure.mail.BankConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BankService {
    @Autowired
    private BankConfig bankConfig;
    @Autowired
    private RestTemplate restTemplate;
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bankConfig.getAuthorization());
        return headers;
    }
    public List<Transactions> getTransactions() {
        ResponseEntity<APIBank<List<Transactions>>> response = restTemplate.exchange(
                bankConfig.getbankUrl(),
                HttpMethod.GET,
                new HttpEntity<>(createHeaders()),
                new ParameterizedTypeReference<APIBank<List<Transactions>>>() {}
        );
        return response.getBody().getTransactions();
    }
}
