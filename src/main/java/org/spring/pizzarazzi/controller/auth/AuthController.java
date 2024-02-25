package org.spring.pizzarazzi.controller.auth;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.common.MsgDTO;
import org.spring.pizzarazzi.dto.member.MemberSignUpDTO;
import org.spring.pizzarazzi.exception.DuplicateMemberException;
import org.spring.pizzarazzi.service.auth.AuthService;
import org.spring.pizzarazzi.service.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;
    private final static String HEADER = "Bearer ";

    @PostMapping("/signup")
    public ResponseEntity<MsgDTO> signup(@RequestBody MemberSignUpDTO memberSignUpDTO) {
        try{
            authService.signup(memberSignUpDTO);
        }catch (DuplicateMemberException e) {
            return ResponseEntity.ok(new MsgDTO(false, "회원가입 실패", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "회원가입 성공", null));
    }

}
