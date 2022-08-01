package com.github.moonproof.uh.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.moonproof.uh.domain.User;
import com.github.moonproof.uh.domain.UserStatus;
import com.github.moonproof.uh.dto.*;
import com.github.moonproof.uh.exception.UserNotFoundException;
import com.github.moonproof.uh.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private User user;
    private UserResponseDto userResponseDto;
    private UserRequestDto userRequestDto;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void initTestData() {
        user = new User(1, "/c/d/f/f.jpeg", "name", "email", UserStatus.ONLINE);
        userResponseDto = new UserResponseDto(user.getId(), user.getUrlPicture(), user.getName(), user.getEmail());
        userRequestDto = new UserRequestDto(user.getUrlPicture(), user.getName(), user.getEmail());
    }

    @Test
    void getUserPositive() {
        when(objectMapper.convertValue(user, UserResponseDto.class))
                .thenReturn(userResponseDto);
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));

        UserResponseDto userResp = userService.getUser(user.getId());

        verify(objectMapper, times(1)).convertValue(user, UserResponseDto.class);
        verify(userRepo, times(1)).findById(any());
        assertEquals(user.getUrlPicture(), userResp.getUrlPicture());
        assertEquals(user.getName(), userResp.getName());
        assertEquals(user.getEmail(), userResp.getEmail());
        assertEquals(user.getId(), userResp.getId());
    }

    @Test
    void getUserNotFound() {
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(10));

        verify(userRepo, times(1)).findById(any());
    }

    @Test
    void createUserPositive() {
        IdDto idDto = new IdDto(user.getId());
        when(objectMapper.convertValue(userRequestDto, User.class)).thenReturn(user);
        when(userRepo.save(any())).thenReturn(user);

        IdDto actualIdDto = userService.createUser(userRequestDto);

        assertEquals(idDto, actualIdDto);
    }

    @Test
    void changeStatusPositive() {
        UserRequestStatusDto userRequestStatusDto = new UserRequestStatusDto();
        userRequestStatusDto.setStatus(UserStatus.OFFLINE.getText());

        User userAnotherStatus = new User(user.getId(), user.getUrlPicture(),
                user.getName(), user.getEmail(),
                UserStatus.getStatusByText(userRequestStatusDto.getStatus()));

        UserResponseStatusDto userResponseStatusDto = new UserResponseStatusDto();
        userResponseStatusDto.setOldStatus(user.getUserStatus().getText());
        userResponseStatusDto.setNewStatus(userAnotherStatus.getUserStatus().getText());
        userResponseStatusDto.setUserId(user.getId());

        when(userRepo.findById(any())).thenReturn(Optional.of(user));
        when(userRepo.save(any())).thenReturn(userAnotherStatus);

        UserResponseStatusDto actualUserStatusResponse = userService.changeStatus(user.getId(), userRequestStatusDto);
        assertEquals(userResponseStatusDto, actualUserStatusResponse);
    }

    @Test
    void changeStatusSame() {
        UserRequestStatusDto userRequestStatusDto = new UserRequestStatusDto();
        userRequestStatusDto.setStatus(UserStatus.ONLINE.getText());

        User userAnotherStatus = new User(user.getId(), user.getUrlPicture(),
                user.getName(), user.getEmail(),
                UserStatus.getStatusByText(userRequestStatusDto.getStatus()));

        UserResponseStatusDto userResponseStatusDto = new UserResponseStatusDto();
        userResponseStatusDto.setOldStatus(user.getUserStatus().getText());
        userResponseStatusDto.setNewStatus(userAnotherStatus.getUserStatus().getText());
        userResponseStatusDto.setUserId(user.getId());

        when(userRepo.findById(any())).thenReturn(Optional.of(user));

        UserResponseStatusDto actualUserStatusResponse = userService.changeStatus(user.getId(), userRequestStatusDto);
        assertEquals(userResponseStatusDto, actualUserStatusResponse);
    }

    @Test
    void changeStatusNotFound() {
        UserRequestStatusDto userRequestStatusDto = new UserRequestStatusDto();
        userRequestStatusDto.setStatus(UserStatus.OFFLINE.getText());

        when(userRepo.findById(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.changeStatus(user.getId(), userRequestStatusDto));
    }
}