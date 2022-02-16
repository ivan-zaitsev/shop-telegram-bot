package ua.ivan909020.admin.models.entities;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ADMIN("Administrator"),
    MODER("Moderator");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getAuthority() {
        return name();
    }

}
