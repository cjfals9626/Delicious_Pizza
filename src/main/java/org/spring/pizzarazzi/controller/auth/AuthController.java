package org.spring.pizzarazzi.controller.auth;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.response.ResponseLoginDTO;
import org.spring.pizzarazzi.dto.common.MsgDTO;
import org.spring.pizzarazzi.dto.request.member.RequestMemberLoginDTO;
import org.spring.pizzarazzi.dto.request.member.RequestMemberSignUpDTO;
import org.spring.pizzarazzi.exception.DuplicateMemberException;
import org.spring.pizzarazzi.service.auth.AuthService;
import org.spring.pizzarazzi.service.member.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.spring.pizzarazzi.util.prefix.ConstPrefix.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;
    private final static String HEADER = "Bearer ";

    @PostMapping("/signup")
    public ResponseEntity<MsgDTO> signup(@RequestBody RequestMemberSignUpDTO requestMemberSignUpDTO) {
        try{
            authService.signup(requestMemberSignUpDTO);
        }catch (DuplicateMemberException e) {
            return ResponseEntity.ok(new MsgDTO(false, "회원가입 실패", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "회원가입 성공", null));
    }

    @PostMapping("/login")
    public ResponseEntity<MsgDTO> login(@RequestBody RequestMemberLoginDTO requestLoginDto) {
        ResponseLoginDTO responseLoginDto = authService.login(requestLoginDto);
        if(responseLoginDto.getAccessToken() != null){
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(ACCESS_TOKEN, HEADER + responseLoginDto.getAccessToken());
            return new ResponseEntity<>(new MsgDTO(true, "로그인 성공", memberService.getLoginInfo(requestLoginDto)), httpHeaders, HttpStatus.OK);
        }
        return ResponseEntity.ok(new MsgDTO(false, "로그인 실패", null));
    }

}
