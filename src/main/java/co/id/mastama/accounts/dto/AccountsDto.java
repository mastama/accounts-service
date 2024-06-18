package co.id.mastama.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    /**
     * ^: Menandakan awal string.
     * \d: Mencocokkan satu digit angka (0-9).
     * {10}: Menandakan tepat 10 kali digit angka.
     * $: Menandakan akhir string.
     */
    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp = "^\\d{10}$", message = "AccountNumber must be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;

    @NotEmpty(message = "branchAddress can not be a null or empty")
    private String branchAddress;
}
