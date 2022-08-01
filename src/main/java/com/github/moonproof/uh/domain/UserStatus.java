package com.github.moonproof.uh.domain;

import com.github.moonproof.uh.exception.InvalidUserStatusException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserStatus {
    OFFLINE("Offline"),
    ONLINE("Online");

    private final String text;

    public static UserStatus getStatusByText(String text) {
        return Arrays.stream(UserStatus.values())
                .filter(u -> u.getText().equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new InvalidUserStatusException("User status '" + text + "' does not exist"));
    }
}
