package hr.tvz.napredna.java.dijezetserver.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    OPERATOR,
    USER;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
