package com.today.demo.controller;

import com.today.demo.dto.MemberJoinDto;
import com.today.demo.dto.Request.EmailCheckReq;
import com.today.demo.redisService.MailRedisTemplateService;
import com.today.demo.service.MemberService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MailRedisTemplateService mailRedisTemplateService;
    @PostMapping("/join")
    public ResponseEntity<String> join(MemberJoinDto dto) {
        try {
            memberService.join(dto);
            return ResponseEntity.ok("join success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sign-up/emailCheck")
    public ResponseEntity<String> emailCheck(@RequestParam @Valid String email) throws MessagingException, UnsupportedEncodingException {
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
