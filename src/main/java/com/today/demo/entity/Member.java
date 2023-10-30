package com.today.demo.entity;

import com.today.demo.dto.MemberJoinDto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userid;

    private String pw;

    private String roles;

    private Member(Long id, String userid, String pw, String roleUser) {
        this.id = id;
        this.userid = userid;
        this.pw = pw;
        this.roles = roleUser;
    }

    public static Member createUser(MemberJoinDto memberJoinDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .userid(memberJoinDto.getUserid())
                .pw(passwordEncoder.encode(memberJoinDto.getPw()))  //암호화처리
                .roles("USER")
                .build();

    }
}