package com.github.moonproof.uh.validator;

import com.github.moonproof.uh.exception.file.IncorrectImageFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ImageExtensionValidatorTest {

    @InjectMocks
    private ImageExtensionValidator extensionValidator;

    @Test
    void validatePositive() {
        String jpeg = "jpeg";
        extensionValidator.validate(jpeg);
    }

    @Test
    void validateNegative() {
        String docx = "docx";
        assertThrows(IncorrectImageFormatException.class, () -> extensionValidator.validate(docx));
    }
}