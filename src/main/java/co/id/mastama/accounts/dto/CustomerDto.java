package co.id.mastama.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name can not be a null or empty.The length of the customer name should be min 1 ")
    @Size(min = 1, max = 255)
    private String name;

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
    @Pattern(regexp = "^\\d{11,13}$", message = "MobileNumber must be numbers only and must be between 11 to 13 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
