package org.spring.pizzarazzi.util.kafka;

import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO.KafkaOrderDTOBuilder;
public class KafkaOrderDTOBuilderHelper {
    public static KafkaOrderDTOBuilder toAdmin(Long memberId) {
        return KafkaOrderDTO.builder()
                .from(memberId.toString())
                .to("ADMIN");
    }

    public static KafkaOrderDTOBuilder toConsumer(Long memberId) {
        return KafkaOrderDTO.builder()
                .from("ADMIN")
                .to(memberId.toString());
    }

    public static KafkaOrderDTOBuilder orderPizza(String memberEmail, Long memberId) {
        return toAdmin(memberId)
                .massage(memberEmail + "님이 피자를 주문하셨습니다.");
    }

    public static KafkaOrderDTOBuilder takeOrder(Long memberId) {
        return toConsumer(memberId)
                .massage("피자 주문이 접수되었습니다.");
    }

    public static KafkaOrderDTOBuilder rejectOrder(Long memberId) {
        return toConsumer(memberId)
                .massage("피자 주문이 거절되었습니다.");
    }

    public static KafkaOrderDTOBuilder cancelOrder(String memberEmail, Long memberId) {
        return toAdmin(memberId)
                .massage(memberEmail + "님이 주문을 취소하셨습니다.");
    }

    public static KafkaOrderDTOBuilder deliverOrder(Long memberId) {
        return toConsumer(memberId)
                .massage("배달이 시작되었습니다.");
    }

    public static KafkaOrderDTOBuilder completeOrder(String memberEmail, Long memberId) {
        return toAdmin(memberId)
                .massage(memberEmail + "님의 주문이 배달 완료되었습니다.");
    }
}
