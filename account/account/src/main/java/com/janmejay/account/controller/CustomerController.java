package com.janmejay.account.controller;

import com.janmejay.account.dto.CustomerDetailDto;
import com.janmejay.account.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Crud REST APIs for Customer Detail in a Bank",
        description = "Crud REST APIs for Customer in a Bank to create, update, fetch and delete customer details"
)
@RestController
@RequestMapping(value = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    ICustomerService iCustomerService;

    @Operation(
            summary = "get Rest API",
            description = "Rest API to get customer in a Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Internal Server Error"
            )
    })
    @GetMapping("/fetchCustomerDetail")
    public ResponseEntity<CustomerDetailDto> fetchCustomerDetail(@RequestHeader("X-correlation-id") String correlationId,
            @RequestParam
            @Pattern( regexp = "$|[0-9]{10}", message = "mobile number should be 10 digit")
                                                                 String mobileNumber){
        logger.debug("Incoming traceId:{}",correlationId);
        CustomerDetailDto customerDetailDto = iCustomerService.fetchCustomerDetail(mobileNumber,correlationId);
       return ResponseEntity.status(HttpStatus.OK).body(customerDetailDto);
    }


}
