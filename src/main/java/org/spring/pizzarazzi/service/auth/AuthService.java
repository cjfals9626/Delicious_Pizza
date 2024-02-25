package org.spring.pizzarazzi.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.pizzarazzi.dto.member.MemberSignUpDTO;
import org.spring.pizzarazzi.service.member.MemberService;
import org.spring.pizzarazzi.util.jwt.TokenProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService memberService;

    @Transactional
    public void signup(MemberSignUpDTO memberSignUpDTO) {
        memberService.signup(memberSignUpDTO);
    }

}
