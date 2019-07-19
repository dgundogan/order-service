package co.uk.silverbars.order.controller;

import co.uk.silverbars.order.constant.OrderType;
import co.uk.silverbars.order.dto.response.ResponseDto;
import co.uk.silverbars.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = OrderController.class)
@AutoConfigureWebClient
public class OrderControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private OrderService service;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void givenDto_whenCallAddOrderEndPoint_thenReturnsOk() throws Exception {
        Resource resource = new ClassPathResource("/fixtures/request.json");

        mockMvc.perform(post("/orders/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(IOUtils.toString(resource.getInputStream(), "UTF-8")
                    )).andExpect(status().isOk());
    }

    @Test
    public void givenId_whenCallRemoveOrder_thenReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/orders/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent());
    }

    @Test
    public void givenOrderTypeIsSell_whenCallGetSummaryOrders_thenReturMergedAndOrderedList() throws Exception {
        ResponseDto dto = ResponseDto.builder().price(100).totalQuantity(1.2).build();
        when(service.getOrderSummary(OrderType.SELL)).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/orders/search?orderType=SELL")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void givenOrderTypeIsBuy_whenCallGetSummaryOrders_thenReturMergedAndOrderedList() throws Exception {
        ResponseDto dto = ResponseDto.builder().price(100).totalQuantity(1.2).build();
        when(service.getOrderSummary(OrderType.BUY)).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/orders/search?orderType=BUY")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}