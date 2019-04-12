package co.uk.silverbars.order.handler.response;

import lombok.Data;

@Data
public class ErrorItem {
    private String code;
    private String message;
}
