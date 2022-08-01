package com.github.moonproof.uh.controller;

import com.github.moonproof.uh.dto.UriDto;
import com.github.moonproof.uh.exception.dto.ApiError;
import com.github.moonproof.uh.service.FileSaver;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/image", produces = "application/json", consumes = "multipart/form-data")
public class ImageController {

    private final FileSaver imageSaver;

    public ImageController(FileSaver imageSaver) {
        this.imageSaver = imageSaver;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное сохранение картинки", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UriDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Не поддержимваемый формат картинки", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
            }),
            @ApiResponse(responseCode = "500", description = "На сервере что-то пошло не так", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
            })
    })
    @PostMapping
    public Object save(@RequestParam("picture") MultipartFile picture) {
        return ResponseEntity.ok(imageSaver.save(picture));
    }

}
