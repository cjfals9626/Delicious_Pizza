package org.spring.pizzarazzi.dto.common;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgDTO {
    private boolean isSuccess;
    private String msg;
    private Object data;
}
