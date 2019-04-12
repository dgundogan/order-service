package co.uk.silverbars.order.dto.request;

import co.uk.silverbars.order.constant.OrderType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class RequestDto {

    @JsonProperty(value ="userId",required = true)
    @NotNull
    @NotBlank
    private String userId;

    @JsonProperty(value ="quantity",required = true)
    @NotNull
    private double quantity;

    @JsonProperty(value ="price",required = true)
    @NotNull
    private long price;

    @JsonProperty(value ="orderType",required = true)
    @NotNull
    private OrderType orderType;
}
