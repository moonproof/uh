package com.github.moonproof.uh.controller;

import com.github.moonproof.uh.domain.UserStatus;
import com.github.moonproof.uh.dto.*;
import com.github.moonproof.uh.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser() {
        UserRequestDto userReq = new UserRequestDto();
        userReq.setEmail("email@ff.ru");
        userReq.setName("fdf");
        userReq.setUrlPicture("fsdfsdf");

        IdDto idDto = new IdDto(1);

        when(userService.createUser(userReq)).thenReturn(idDto);

        Object userRes = userController.createUser(userReq);

        assertEquals(ResponseEntity.ok(idDto), userRes);
    }

    @Test
    void getUser() {
        UserResponseDto userResponseDto = new UserResponseDto(1, "fdf/fdf", "name", "email");

        when(userService.getUser(1)).thenReturn(userResponseDto);

        Object user = userController.getUser(1);

        assertEquals(ResponseEntity.ok(userResponseDto), user);
    }

    @Test
    void changeStatus() {
        int id = 1;
        UserRequestStatusDto userRequestStatusDto = new UserRequestStatusDto();
        userRequestStatusDto.setStatus(UserStatus.ONLINE.getText());
        UserResponseStatusDto userResponseStatusDto = new UserResponseStatusDto();
        userResponseStatusDto.setUserId(id);
        userResponseStatusDto.setNewStatus(userRequestStatusDto.getStatus());
        userResponseStatusDto.setOldStatus(UserStatus.OFFLINE.getText());

        when(userService.changeStatus(id, userRequestStatusDto)).thenReturn(userResponseStatusDto);

        Object resp = userController.changeStatus(id, userRequestStatusDto);

        assertEquals(ResponseEntity.ok(userResponseStatusDto), resp);
    }
}