package org.spring.pizzarazzi.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLoginDTO {
    private String accessToken;
}
