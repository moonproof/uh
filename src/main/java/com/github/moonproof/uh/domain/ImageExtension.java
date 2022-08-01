package com.github.moonproof.uh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ImageExtension {
    JPEG("jpeg");

    private final String extension;
}
