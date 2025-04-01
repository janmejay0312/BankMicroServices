package com.janmejay.account.service.client;

import com.janmejay.account.dto.CardsDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="card", fallback = CardsFallBack.class)
public interface CardFeignClient {

    @GetMapping(value = "/api/card/fetch", consumes = "application/json")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("X-correlation-id") String correlationId, @RequestParam
                                                     String mobileNumber);
}
