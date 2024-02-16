package com.today.demo.redisService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.today.demo.entity.Member;
import com.today.demo.repository.MemberRepository;
import com.today.demo.service.MailService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailRedisTemplateService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private final MemberRepository memberRepository;

    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    private final MailService mailService;

    public final long AUTH_CODE_EXPIRATION_MILLIS = 1800000; // 30 * 60 * 1000 == 30분


    public String sendCodeToEmail(String email) throws MessagingException, UnsupportedEncodingException {
        checkDuplicatedEmail(email);
        String authNum = mailService.sendEmail(email);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        hashOperations.put(AUTH_CODE_PREFIX + email, "value", authNum);
        redisTemplate.expire(AUTH_CODE_PREFIX + email, Duration.ofMillis(this.AUTH_CODE_EXPIRATION_MILLIS));
        return authNum;
    }
    private void checkDuplicatedEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
            throw new RuntimeException("중복된 이메일입니다."); // 중복된 이메일이 발견되면 예외를 던집니다.
        }
    }

    public boolean verifyCode(String email, String authCode) {
        checkDuplicatedEmail(email);
        // Redis에서 저장된 인증번호 가져오기
        String storedCode = hashOperations.get(AUTH_CODE_PREFIX + email, "value");

        // 저장된 인증번호와 입력한 인증번호 비교
        return authCode.equals(storedCode);
    }

}
