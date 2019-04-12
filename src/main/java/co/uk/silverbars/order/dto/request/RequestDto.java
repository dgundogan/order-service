package co.uk.silverbars.order.dto.request;

import co.uk.silverbars.order.constant.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private long id;
    private String userId;
    private double quantity;
    private long price;
    private OrderType orderType;
}
