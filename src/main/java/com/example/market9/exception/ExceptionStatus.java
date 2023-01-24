package com.example.market9.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionStatus {
    DUPLICATED_USERNAME(409, "이미 사용중인 아이디 입니다."),
    ALREADY_EXIST_SELLER(409,"셀러로 등록된 아이디 입니다,"),
    ALREADY_EXIST_ADMIN(409,"어드민으로 등록된 아이디 입니다."),
    ALREADY_EXIST_REQUEST(409,"이미 전송된 요청입니다."),
    WRONGADMINTOKEN(400, "잘못된 관리자 비밀번호 입니다."),
    ACCESS_DENINED(500, "접근 권한이 없습니다."),
    AUTHENTICATION(500, "인증 실패"),
    BOARD_NOT_EXIST(404, "해당하는 아이디의 판매 게시물이 없습니다."),
    REQUEST_NOT_EXIST(404,"해당하는 요청이 존재하지 않습니다.");
    private final int statusCode;
    private final String message;
}
