package com.today.demo.controller.member;

import com.today.demo.dto.BoardResponseDTO;
import com.today.demo.dto.MemberJoinDto;
import com.today.demo.dto.Request.EmailCheckReq;
import com.today.demo.redisService.MailRedisTemplateService;
import com.today.demo.repository.MarkerRepository;
import com.today.demo.service.BoardService;
import com.today.demo.service.HeartService;
import com.today.demo.service.MemberService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final MailRedisTemplateService mailRedisTemplateService;
    private final HeartService heartService;

    @GetMapping("/member/heart/{userId}")
    public List<BoardResponseDTO> getMemberHeartList(@PathVariable String userId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size){
        return heartService.getMemberHeartList(userId,page,size);
    }

    @GetMapping("/member/board/{userId}")
    public List<BoardResponseDTO> getMemberBoardList(@PathVariable String userId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size){
        return boardService.getMemberBoardList(userId,page,size);
    }


    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberJoinDto dto) {
        try {
            boolean isValid = mailRedisTemplateService.verifyCode(dto.getEmail(), dto.getAuthCode());
            if (isValid) {
                memberService.join(dto);
                return ResponseEntity.ok("join success");
            } else {
                return ResponseEntity.badRequest().body("유효하지 않은 인증 코드입니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        }
    }

    @PostMapping("/sign-up/emailCheck")
    public ResponseEntity<String> emailCheck(@RequestParam String email) throws MessagingException, UnsupportedEncodingException {
        mailRedisTemplateService.sendCodeToEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/sign-up/verify")
    public ResponseEntity<String> verifyCode(@RequestBody EmailCheckReq emailCheckReq) {
        boolean isValid = mailRedisTemplateService.verifyCode(emailCheckReq.getEmail(), emailCheckReq.getAuthCode());

        if (isValid) {
            return ResponseEntity.ok("인증이 성공적으로 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("인증번호가 올바르지 않습니다.");
        }
    }
}
