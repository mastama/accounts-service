package co.id.mastama.accounts.dto;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
@Data @NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    @Schema(description = "Status code in the response", example = "200")
    private String statusCode;

    @Schema(description = "Status message in the response", example = "Request processed successfully")
    private String statusMessage;

    @Schema(description = "Response value or data successfully", example = """
{
    "name": "Mastama",
    "email": "mastama@modernitpooling.co.id",
    "mobileNumber": "081245678901",
    "accountsDto": {
        "accountNumber": 4596370869,
        "accountType": "Saving",
        "branchAddress": "Bekasi Raya"
    }
}
""")
    private Object data;
}
