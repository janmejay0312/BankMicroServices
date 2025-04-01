package com.janmejay.account.service.client;

import com.janmejay.account.dto.LoansDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="loan", fallback = LoansFallBack.class)
public interface LoanFeignClient {
    @GetMapping(value = "/api/loan/fetch", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("X-correlation-id") String correlationId, @RequestParam
                                                     String mobileNumber);
}
