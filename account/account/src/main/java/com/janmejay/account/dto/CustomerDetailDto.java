package com.janmejay.account.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

@Schema(
        name = "Customer",
        description = "Schema to hold customer and Account Information"
)
public class CustomerDetailDto {

    @Schema(
            description = "name of the customer", example = "Test"
    )
    @NotEmpty(message = "name can not be null or empty value")
    @Size(min = 5, max = 30, message = "name is not having valid length")
    private String name;

    @Schema(
            description = "Email of the customer", example = "Test@gmail.com"
    )
    @NotEmpty(message ="Email can not be null or empty value")
    @Email(message = "Email should be a valid value")
    private String email;

    @Schema(
            description = "Mobile Number of the customer", example = "1234567890"
    )
    @Pattern( regexp = "$|[0-9]{10}", message = "mobile number should be 10 digit")
    private String mobileNumber;

    @Schema(
            description = "Account detail of the customer"
    )
    private AccountsDto accountDto;

    @Schema(
            description = "Loans detail of the customer"
    )
    private LoansDto loansDto;

    @Schema(
            description = "Account detail of the customer"
    )
    private CardsDto cardsDto;

}
