package com.github.moonproof.uh.service;

import com.github.moonproof.uh.dto.*;

public interface UserService {

    UserResponseDto getUser(long id);
    IdDto createUser(UserRequestDto userRequestDto);
    UserResponseStatusDto changeStatus(long id, UserRequestStatusDto requestStatus);

}
