package com.janmejay.account.service;

import com.janmejay.account.dto.CustomerDetailDto;

public interface ICustomerService {
    /**
     *
     * @param mobileNo
     * @return
     */
    CustomerDetailDto fetchCustomerDetail(String mobileNo,String correlationId);
}
