package org.spring.pizzarazzi.dto.member;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class MemberLoginDTO {
    private String email;
    private String password;
}
