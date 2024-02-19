package com.today.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinDto {
    private String userid;
    private String pw;
    private String email;
    private String authCode;
}
