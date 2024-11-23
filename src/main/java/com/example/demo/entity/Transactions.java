package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
    @JsonProperty("id")
    private int id;
    @JsonProperty("transaction_content")
    private String transaction_content;
    @JsonProperty("amount_in")
    private double amount_in;
    @JsonProperty("transaction_date")
    private String transaction_date;
}
