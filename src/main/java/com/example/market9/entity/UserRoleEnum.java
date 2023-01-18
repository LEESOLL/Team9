package com.example.market9.entity;

public enum UserRoleEnum {
    /*ROLE_USER,
    ROLE_SELLER,
    ROLE_ADMIN*/
    //이넘은 쉼표로 이어져야한다 ...
    USER(Authority.USER),  // 사용자 권한
    ADMIN(Authority.ADMIN),  // 관리자 권한
    SELLER(Authority.SELLER); //원준님 최고 !!
    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String SELLER = "ROLE_SELLER";
    }
}
