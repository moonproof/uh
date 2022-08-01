package com.github.moonproof.uh.service;

import com.github.moonproof.uh.dto.UriDto;
import com.github.moonproof.uh.exception.file.FileNotSavedException;
import com.github.moonproof.uh.validator.ImageExtensionValidator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ImageSaverImplTest {

    @Mock
    private ImageExtensionValidator extensionValidator;
    @InjectMocks
    private ImageSaverImpl imageSaver;

    @Test
    void saveContentTypeEmpty() {
        MultipartFile multipartFile = new MockMultipartFile("name", "name", "", new byte[]{1});

        assertThrows(FileNotSavedException.class, () -> imageSaver.save(multipartFile));
    }

    @Test
    @SneakyThrows
    void saveIncorrectPath() {
        MultipartFile multipartFile = new MockMultipartFile("name", "name", "image/jpeg", new byte[]{1});
        Field field = imageSaver.getClass().getDeclaredField("dirPath");
        field.setAccessible(true);
        field.set(imageSaver, "src/test/resources/WhatAboutIt");
        assertThrows(FileNotSavedException.class, () -> imageSaver.save(multipartFile));
    }

    @Test
    @SneakyThrows
    void savePositive() {
        String dirPath = "src/main/resources/images";
        String jpeg = "jpeg";
        MultipartFile multipartFile = new MockMultipartFile("name", "name", "image/" + jpeg, new byte[]{1});

        Field field = imageSaver.getClass().getDeclaredField("dirPath");
        field.setAccessible(true);
        field.set(imageSaver, dirPath);

        String uriMatcher = ".+" + dirPath + ".+" + jpeg;
        UriDto actualUri = imageSaver.save(multipartFile);

        assertTrue(actualUri.getUri().matches(uriMatcher));
    }
}