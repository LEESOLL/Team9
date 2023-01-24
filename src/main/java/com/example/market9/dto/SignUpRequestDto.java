package com.example.market9.dto;

import com.example.market9.entity.UserRoleEnum;
import lombok.*;
import org.apache.catalina.User;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter

@NoArgsConstructor
public class SignUpRequestDto {

//    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)")
    private String username;

//    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9~!@#$%^&*()+|=]{8,15}$", message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9),특수문자(~!@#$%^&*()+|=)")
    private String password;

    private String nickname;

    private boolean admin = false;
    private String adminToken = "";
    private  String filename;
    private String filepath;
}
