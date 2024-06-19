package co.id.mastama.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
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
    @Schema(description = "Account number of BANKAPAYA account", example = "4596370869")
    private Long accountNumber;

    @NotEmpty(message = "AccountType can not be a null or empty")
    @Schema(description = "Account type of BANKAPAYA account", example = "Saving")
    private String accountType;

    @NotEmpty(message = "branchAddress can not be a null or empty")
    @Schema(description = "BANKAPAYA branch address", example = "Bekasi Raya")
    private String branchAddress;
}
