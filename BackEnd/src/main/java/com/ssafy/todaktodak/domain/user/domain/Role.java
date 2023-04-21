package com.ssafy.todaktodak.domain.user.domain;

public enum Role {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String grantedAuthority;

    Role(String grantedAuthority) {
        this.grantedAuthority = grantedAuthority;

    }

    public String getGrantedAuthority() {
        return grantedAuthority;
    }

}
