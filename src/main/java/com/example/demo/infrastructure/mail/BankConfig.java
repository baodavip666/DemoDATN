package com.example.demo.infrastructure.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfig {
    @Value("Bearer HFBHQCE7V4TPRMVEVOACKJZKP7JTRDYQ4DENJL8QNBOBJYAT9LH3IGR1OP6USXLK")
    private String Authorization;
    @Value("https://my.sepay.vn/userapi/transactions/list?account_number=8602418574&limit=20")
    private String bankUrl;
    public String getAuthorization() {
        return Authorization;
    }
    public String getbankUrl() {
        return bankUrl;
    }

}
