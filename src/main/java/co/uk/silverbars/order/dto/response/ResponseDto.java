package co.uk.silverbars.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto extends ResourceSupport {
    public long price;
    public double totalQuantity;
}
