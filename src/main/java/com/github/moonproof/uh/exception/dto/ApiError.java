package com.github.moonproof.uh.exception.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ApiError {
    private String errorMessage;
}
