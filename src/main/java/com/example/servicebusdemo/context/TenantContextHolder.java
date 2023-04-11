package com.example.servicebusdemo.context;

public class TenantContextHolder {

    private static final ThreadLocal<String> tenantIdHolder = new ThreadLocal<>();

    public static void setTenantId(String tenantId) {
        tenantIdHolder.set(tenantId);
    }

    public static String getTenantId() {
        return tenantIdHolder.get();
    }

    public static void clear() {
        tenantIdHolder.remove();
    }
}
