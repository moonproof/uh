package com.github.moonproof.uh.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.moonproof.uh.domain.User;
import com.github.moonproof.uh.domain.UserStatus;
import com.github.moonproof.uh.dto.*;
import com.github.moonproof.uh.exception.UserNotFoundException;
import com.github.moonproof.uh.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserRepo userRepo, ObjectMapper objectMapper) {
        this.userRepo = userRepo;
        this.objectMapper = objectMapper;
    }

    @Override
    public UserResponseDto getUser(long id) {
        Optional<User> user = userRepo.findById(id);

        return user.stream()
                .map(u -> objectMapper.convertValue(u, UserResponseDto.class))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found with ID='" + id + "'"));
    }

    @Override
    public IdDto createUser(UserRequestDto userRequestDto) {
        User user = objectMapper.convertValue(userRequestDto, User.class);
        user.setUserStatus(UserStatus.ONLINE);
        User savedUser = userRepo.save(user);
        return new IdDto(savedUser.getId());
    }

    @Override
    public UserResponseStatusDto changeStatus(long id, UserRequestStatusDto requestStatus) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID='" + id + "'"));
        UserResponseStatusDto userResponseStatusDto = new UserResponseStatusDto();
        userResponseStatusDto.setUserId(user.getId());
        userResponseStatusDto.setOldStatus(user.getUserStatus().getText());
        userResponseStatusDto.setNewStatus(requestStatus.getStatus());
        if (isValidUpdateStatus(user.getUserStatus(), requestStatus)) {
            user.setUserStatus(UserStatus.getStatusByText(requestStatus.getStatus()));
            userRepo.save(user);
        }
        return userResponseStatusDto;
    }

    private boolean isValidUpdateStatus(UserStatus userStatus, UserRequestStatusDto userRequestStatusDto) {
        UserStatus requestedStatus = UserStatus.getStatusByText(userRequestStatusDto.getStatus());
        return userStatus != requestedStatus;
    }
}
