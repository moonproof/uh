package com.github.moonproof.uh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
    private long id;
    private String urlPicture;
    private String name;
    private String email;
}
