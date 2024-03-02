package org.spring.pizzarazzi.controller.member;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.common.MsgDTO;
import org.spring.pizzarazzi.dto.member.MemberInfoDTO;
import org.spring.pizzarazzi.dto.request.member.RequestCheckPasswordDTO;
import org.spring.pizzarazzi.dto.request.member.RequestMemberUpdateDTO;
import org.spring.pizzarazzi.service.member.MemberService;
import org.spring.pizzarazzi.util.jwt.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.spring.pizzarazzi.util.prefix.ConstPrefix.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @GetMapping("/info")
    public ResponseEntity<MsgDTO> getMemberInfo(@RequestHeader(value = AUTHORIZATION) String accessToken){
        Long memberId = Long.valueOf(tokenProvider.getId(tokenProvider.resolveToken(accessToken)));
        try {
            MemberInfoDTO memberInfoDTO = memberService.getMemberInfo(memberId);
            return ResponseEntity.ok(new MsgDTO(true, "회원 정보 조회 성공", memberInfoDTO));
        } catch (Exception e) {
            return ResponseEntity.ok(new MsgDTO(false, "회원 정보 조회 실패", null));
        }
    }

    //비밀번호 확인
    @PostMapping("/checkPassword")
    public ResponseEntity<MsgDTO> checkPassword(@RequestHeader(value = AUTHORIZATION) String accessToken, @RequestBody RequestCheckPasswordDTO requestCheckPasswordDTO){
        Long memberId = Long.valueOf(tokenProvider.getId(tokenProvider.resolveToken(accessToken)));
        try {
            MemberInfoDTO memberInfoDTO = memberService.checkPassword(memberId, requestCheckPasswordDTO);
            if(memberInfoDTO != null){
                return ResponseEntity.ok(new MsgDTO(true, "비밀번호 확인 성공", memberInfoDTO));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new MsgDTO(false, "비밀번호 확인 실패", null));
        }
        return ResponseEntity.ok(new MsgDTO(false, "비밀번호 잘못 입력", null));
    }

    @PutMapping("/update")
    public ResponseEntity<MsgDTO> updateMemberInfo(@RequestHeader(value = AUTHORIZATION) String accessToken, @RequestBody RequestMemberUpdateDTO memberUpdateDTO){
        Long memberId = Long.valueOf(tokenProvider.getId(tokenProvider.resolveToken(accessToken)));
        try {
            memberService.updateMemberInfo(memberId, memberUpdateDTO);
            return ResponseEntity.ok(new MsgDTO(true, "회원 정보 수정 성공", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new MsgDTO(false, "회원 정보 수정 실패", null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MsgDTO> deleteMember(@RequestHeader(value = AUTHORIZATION) String accessToken){
        Long memberId = Long.valueOf(tokenProvider.getId(tokenProvider.resolveToken(accessToken)));
        try {
            memberService.deleteMember(memberId);
            return ResponseEntity.ok(new MsgDTO(true, "회원 탈퇴 성공", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new MsgDTO(false, "회원 탈퇴 실패", null));
        }
    }

}
