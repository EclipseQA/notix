package com.solvd.notix.web.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Customer {

    private String name;
    private String address;
    private String phoneNumber;
}
