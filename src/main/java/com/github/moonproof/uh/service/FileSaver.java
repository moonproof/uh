package com.github.moonproof.uh.service;

import com.github.moonproof.uh.dto.UriDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileSaver {
    UriDto save(MultipartFile multipartFile);
}
