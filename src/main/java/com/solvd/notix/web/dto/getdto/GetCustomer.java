package com.solvd.notix.web.dto.getdto;

import com.solvd.notix.utils.DataFaker;
import com.solvd.notix.web.dto.Customer;

public class GetCustomer {

    public static Customer getCustomerWithAllFields(){
        return Customer.builder()
                .name(DataFaker.getName())
                .address(DataFaker.getAddress())
                .phoneNumber(DataFaker.getPhoneNumber())
                .build();
    }
}
