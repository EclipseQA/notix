package com.solvd.notix.web.dto.getdto;

import com.solvd.notix.utils.DataFaker;
import com.solvd.notix.web.dto.CustomerModel;

public class GetCustomerModel {

    public static CustomerModel getCustomerModelWithAllFields(){
        return CustomerModel.builder()
                .name(DataFaker.getName())
                .address(DataFaker.getAddress())
                .phoneNumber(DataFaker.getPhoneNumber())
                .build();
    }
}
