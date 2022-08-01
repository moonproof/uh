package com.github.moonproof.uh.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserResponseStatusDto {
    private String newStatus;
    private String oldStatus;
    private long userId;
}
