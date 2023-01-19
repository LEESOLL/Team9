package com.example.market9.entity;

public enum PermissionStatusEnum {

    ACCEPT(Authority.ACCEPT),  // 수락
    WAITING(Authority.WAITING),  // 대기
    REFUSE(Authority.REFUSE); // 거절
    private final String authority;

    AuthorityStatusEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {     ///시큐리티에서 ... 구분할려고..! 스트링값을 읽음... 11
        public static final String ACCEPT = "AUTH_ACCEPT";
        public static final String WAITING = "AUTH_WAITING";
        public static final String REFUSE = "AUTH_REFUSE";
    }
}
