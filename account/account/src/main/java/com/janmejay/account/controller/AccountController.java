package com.janmejay.account.controller;

import com.janmejay.account.constants.AccountsConstants;
import com.janmejay.account.dto.CustomerDto;
import com.janmejay.account.dto.ErrorResponseDto;
import com.janmejay.account.dto.ResponseDto;
import com.janmejay.account.entity.Customer;
import com.janmejay.account.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Crud REST APIs for Account in a Bank",
        description = "Crud REST APIs for Account in a Bank to create, update, fetch and delete account details"
)
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountController {

    @Autowired
    IAccountService iAccountService;

    @Operation(
            summary = "Create Rest API",
            description = "Create Rest API to create customer in a Bank"
    )@ApiResponse(
            responseCode = "201",
            description = "HTTP status created"
    )
    @PostMapping(value = "/account/save")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){

    Customer customer   =  iAccountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Rest API",
            description = "Create Rest API to fetch customer using a mobile number in a Bank"
    )@ApiResponse(
            responseCode = "200",
            description = "HTTP status OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccount(@RequestParam
                                                        @Pattern( regexp = "$|[0-9]{10}", message = "mobile number should be 10 digit")
                                                        String mobileNumber){

        CustomerDto customerDto = iAccountService.fetchAccount(mobileNumber);

        return  ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "Update Rest API",
            description = "Rest API to update customer in a Bank"
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

@PutMapping("/update")
  public  ResponseEntity<ResponseDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto){

        boolean isUpdated = iAccountService.updateCustomer(customerDto);
        ResponseEntity<ResponseDto> responseDto =  null;
        if(isUpdated)
            responseDto = ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        else
            responseDto = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));

        return  responseDto;
  }

    @Operation(
            summary = "Update Rest API",
            description = "Rest API to delete customer in a Bank"
    ) @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal Server Error",
                    content =@Content(schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })

    @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                     @Pattern( regexp = "$|[0-9]{10}", message = "mobile number should be 10 digit")
                                                     String mobileNumber){
      boolean isDeleted = iAccountService.deleteCustomer(mobileNumber);
      ResponseEntity<ResponseDto> responseDto =  null;
      if(isDeleted)
          responseDto = ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
      else
          responseDto = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));

      return  responseDto;
  }
}
