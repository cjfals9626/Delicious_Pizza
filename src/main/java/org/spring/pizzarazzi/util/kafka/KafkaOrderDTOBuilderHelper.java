package org.spring.pizzarazzi.util.kafka;

import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO.KafkaOrderDTOBuilder;
public class KafkaOrderDTOBuilderHelper {
    public static KafkaOrderDTOBuilder  toAdmin(Long memberId) {
        return KafkaOrderDTO.builder()
                .from(memberId.toString())
                .to("ADMIN");
    }

    public static KafkaOrderDTOBuilder  toConsumer(Long memberId) {
        return KafkaOrderDTO.builder()
                .from("ADMIN")
                .to(memberId.toString());
    }
}
