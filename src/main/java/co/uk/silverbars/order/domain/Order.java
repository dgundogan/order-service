package co.uk.silverbars.order.domain;

import co.uk.silverbars.order.constant.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = "UserId is required.")
    private String userId;

    @NotNull(message = "Quantity is required.")
    private double quantity;

    @NotNull(message = "Price is required.")
    private long price;

    @NotNull(message = "Order Type is required.")
    private OrderType orderType;
}
