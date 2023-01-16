package com.example.market9.entity;

public enum RoleType {
    /*ROLE_USER,
    ROLE_SELLER,
    ROLE_ADMIN*/

    USER(Authority.USER),  // 사용자 권한
    ADMIN(Authority.ADMIN);  // 관리자 권한
   /* SELLER(Authority.SELLER); ? ....*/
    private final String authority;

    RoleType(String authority) {
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
