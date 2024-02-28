package org.spring.pizzarazzi.util.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.spring.pizzarazzi.dto.kafka.OrderDTO;

import java.io.IOException;

public class OrderDTOSerializer extends JsonSerializer<OrderDTO> {
    @Override
    public void serialize(OrderDTO orderDTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("memberId", orderDTO.getMemberId());
        jsonGenerator.writeNumberField("orderId", orderDTO.getOrderId());
        jsonGenerator.writeStringField("orderStatus", orderDTO.getOrderStatus().name());
        jsonGenerator.writeNumberField("totalPrice", orderDTO.getTotalPrice());
        jsonGenerator.writeEndObject();
    }
}
