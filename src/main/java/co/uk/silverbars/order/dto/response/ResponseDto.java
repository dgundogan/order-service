package co.uk.silverbars.order.dto.response;

import lombok.*;
import org.springframework.hateoas.ResourceSupport;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResponseDto extends ResourceSupport {
    public long price;
    public double totalQuantity;
}
