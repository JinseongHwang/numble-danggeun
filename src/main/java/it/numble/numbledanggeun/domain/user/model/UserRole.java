package it.numble.numbledanggeun.domain.user.model;

import it.numble.numbledanggeun.infrastructure.error.exception.UserDefineException;
import java.util.Arrays;

public enum UserRole {

    ROLE_USER("user"), ROLE_ADMIN("admin");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.role;
    }

    public static UserRole of(String compactRole) {
        return Arrays.stream(UserRole.values())
            .filter(role -> role.toString().equals(compactRole))
            .findFirst()
            .orElseThrow(() -> new UserDefineException("Invalid UserRole"));
    }
}
