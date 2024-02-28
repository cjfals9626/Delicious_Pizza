package org.spring.pizzarazzi.util.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.spring.pizzarazzi.dto.kafka.OrderDTO;
import org.spring.pizzarazzi.enums.OrderStatus;

import java.io.IOException;

public class OrderDTODeserializer extends JsonDeserializer<OrderDTO> {

    @Override
    public OrderDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode memberId = node.get("memberId");
        JsonNode orderId = node.get("orderId");
        JsonNode orderStatus = node.get("orderStatus");
        JsonNode totalPrice = node.get("totalPrice");

        if (memberId != null && orderId != null && orderStatus != null && totalPrice != null) {
            return OrderDTO.builder()
                    .memberId(memberId.asLong())
                    .orderId(orderId.asLong())
                    .orderStatus(OrderStatus.valueOf(orderStatus.asText()))
                    .totalPrice(totalPrice.asLong())
                    .build();
        }

        return null;
    }

}
