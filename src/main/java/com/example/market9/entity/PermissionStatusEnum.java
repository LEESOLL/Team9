package com.example.market9.entity;

public enum PermissionStatusEnum {

    ACCEPT(PermissionAuthority.ACCEPT),  // 수락
    WAITING(PermissionAuthority.WAITING),  // 대기
    REFUSE(PermissionAuthority.REFUSE); // 거절
    private final String permissionAuthority;

    PermissionStatusEnum(String authority) {
        this.permissionAuthority = authority;
    }

    public String getAuthority() {
        return this.permissionAuthority;
    }

    public static class PermissionAuthority {     ///시큐리티에서 ... 구분할려고..! 스트링값을 읽음... 11
        public static final String ACCEPT = "AUTH_ACCEPT";
        public static final String WAITING = "AUTH_WAITING";
        public static final String REFUSE = "AUTH_REFUSE";
    }
}
