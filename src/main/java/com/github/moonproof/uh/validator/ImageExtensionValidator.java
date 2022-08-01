package com.github.moonproof.uh.validator;

import com.github.moonproof.uh.exception.file.IncorrectImageFormatException;
import com.github.moonproof.uh.domain.ImageExtension;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ImageExtensionValidator implements ExtensionValidator {
    @Override
    public void validate(String extension) {
        if (!isCorrectExtension(extension)) {
            throw new IncorrectImageFormatException("Image format '" + extension + " does not supported");
        }
    }

    private boolean isCorrectExtension(String extension) {
        return Arrays.stream(ImageExtension.values())
                .anyMatch((e) -> e.getExtension().equals(extension));
    }
}
