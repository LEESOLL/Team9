package com.example.market9.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionStatus {
    DUPLICATED_USERNAME(409, "이미 사용중인 아이디 입니다."),
    WRONG_USERNAME(404, "아이디를 잘못 입력 하였거나 등록되지 않은 아이디 입니다."),
    WRONG_PASSWORD(400, "잘못된 비밀번호 입니다."),
    WRONG_PROFILE(404, "프로필이 존재하지 않습니다."),
    WRONG_AUTHORITY_DEMAND(404, "판매자 권한 신청서가 존재하지 않습니다"),
    ALREADY_EXIST_SELLER(409,"셀러로 등록된 아이디 입니다,"),
    ALREADY_EXIST_ADMIN(409,"어드민으로 등록된 아이디 입니다."),
    ALREADY_EXIST_REQUEST(409,"이미 전송된 요청입니다."),
    ALREADY_PROCESSED_REQUEST(409,"이미 처리된 요청입니다."),
    WRONG_ADMINTOKEN(400, "잘못된 관리자 비밀번호 입니다."),
    ACCESS_DENINED(500, "접근 권한이 없습니다."),
    AUTHENTICATION(500, "인증 실패"),
    BOARD_NOT_EXIST(404, "해당하는 아이디의 판매 게시물이 없습니다."),
    REQUEST_NOT_EXIST(404,"해당하는 요청이 존재하지 않습니다."),
    WRONG_SELLER_ID_T0_BOARD(403,"다른 판매자의 게시물에는 접근 할 수 없습니다."),
    WRONG_SELLER_ID_TO_USER_REQUEST(403,"다른 판매자의 요청목록에는 접근 할 수 없습니다.");



    private final int statusCode;
    private final String message;
}
