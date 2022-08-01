package com.github.moonproof.uh.service;

import com.github.moonproof.uh.dto.UriDto;
import com.github.moonproof.uh.exception.file.FileNotSavedException;
import com.github.moonproof.uh.validator.ExtensionValidator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageSaverImpl implements FileSaver {

    private static final String SAVE_INTERNAL_ERROR = "File can not saved by some issue";

    @Value("${image.upload.path}")
    private String dirPath;

    private final ExtensionValidator extensionValidator;

    public ImageSaverImpl(ExtensionValidator extensionValidator) {
        this.extensionValidator = extensionValidator;
    }

    @Override
    public UriDto save(MultipartFile image) {
        validateExtension(image);

        String pictureExtension = getExtension(Objects.requireNonNull(image.getContentType()));
        String filePath = dirPath + "/" + UUID.randomUUID() + "." + pictureExtension;
        File picture = new File(filePath);

        try (InputStream is = new ByteArrayInputStream(image.getBytes())) {
            picture.createNewFile();
            IOUtils.copy(is, new FileOutputStream(picture));
        } catch (IOException e) {
            throw new FileNotSavedException(SAVE_INTERNAL_ERROR, e);
        }

        return new UriDto(picture.getAbsolutePath());
    }

    private void validateExtension(MultipartFile image) {
        String contentType = image.getContentType();

        if (ObjectUtils.isEmpty(contentType)) {
            throw new FileNotSavedException("It's not file");
        }

        extensionValidator.validate(getExtension(contentType));
    }

    private String getExtension(String contentType) {
        return contentType.substring(contentType.lastIndexOf("/") + 1);
    }
}
