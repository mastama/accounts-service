package co.id.mastama.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
@Data
public class CustomerDto {

    @Schema(description = "Name of the customer", example = "Mastama")
    @NotEmpty(message = "Name can not be a null or empty.The length of the customer name should be min 1 ")
    @Size(min = 1, max = 255)
    private String name;

    @Schema(description = "Email address of the customer", example = "mastama@modernitpooling.co.id")
    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    /**
     * 	^: Menandakan awal string.
     * 	\d: Mencocokkan satu digit angka (0-9).
     * 	* / +: Menandakan nol atau lebih dari digit yang cocok sebelumnya.
     * 	{11,13}: Menandakan minimal 11 digit dan maksimal 13 digit.
     * 	$: Menandakan akhir string.
     */
    @Schema(description = "Mobile number of the customer", example = "081245678901")
    @Pattern(regexp = "^\\d{11,13}$", message = "MobileNumber must be numbers only and must be between 11 to 13 digits")
    private String mobileNumber;

    @Schema(description = "Account details of the customer")
    private AccountsDto accountsDto;
}
