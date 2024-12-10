package com.janmejay.account.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Account",
        description = "Schema to hold Account Information"
)
public class AccountsDto {


    @Schema(
            description = "Account number assigned to the customer", example = "2535536363"
    )
    @NotEmpty(message = "Account can not be null or empty value")
    @Pattern(regexp = "$|[0-9]{10}", message = "AccountNumber must be 10 digit")
    private Long accountNo;

    @Schema(
            description = "Account Type assigned to the customer", example = "SAVINGS"
    )
    @NotEmpty(message = "AccountType must not be null or empty value")
    private String accountType;

    @Schema(
            description = "Branch Address assigned to the customer", example = "abc 202618"
    )
    @NotEmpty(message = "BranchAddress must not be null or empty value")
    private String branchAddress;
}
