package com.example.market9.requisition.entity;

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

    public static class PermissionAuthority {
        public static final String ACCEPT = "AUTH_ACCEPT";
        public static final String WAITING = "AUTH_WAITING";
        public static final String REFUSE = "AUTH_REFUSE";
    }
}
