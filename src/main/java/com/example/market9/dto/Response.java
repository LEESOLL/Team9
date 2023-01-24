package com.example.market9.dto;

import lombok.Getter;

@Getter
public class Response {
    String requestContent ;
    String userName ;
    Long postId ;
    public Response(String requestContent, String userName, Long postId) {
        this.requestContent = requestContent;
        this.userName = userName;
        this.postId = postId;
    }
}
