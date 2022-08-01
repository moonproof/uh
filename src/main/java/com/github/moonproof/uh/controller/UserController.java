package com.github.moonproof.uh.controller;

import com.github.moonproof.uh.dto.*;
import com.github.moonproof.uh.exception.dto.ApiError;
import com.github.moonproof.uh.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user", produces = "application/json", consumes = "application/json")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponse(responseCode = "200", description = "Успешное создание юзера", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = IdDto.class))
    })
    @PostMapping
    public Object createUser(@RequestBody UserRequestDto userRequestDto) {
        IdDto userId = userService.createUser(userRequestDto);
        return ResponseEntity.ok(userId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение юзера", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Юзер не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
            })
    })
    @GetMapping("/{id}")
    public Object getUser(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная смена статуса", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseStatusDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Несуществующий тип статуса", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "Юзер не существует"),
    })
    @PatchMapping("/{id}")
    public Object changeStatus(@PathVariable long id, @RequestBody UserRequestStatusDto userRequestStatusDto) {
        return ResponseEntity.ok(userService.changeStatus(id, userRequestStatusDto));
    }
}
