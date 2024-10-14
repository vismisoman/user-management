package com.usermanagment.service;

import com.usermanagment.dto.UserDto;
import com.usermanagment.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto userDto) throws UserNotFoundException;
    public List<UserDto> getAllUsers()throws UserNotFoundException;
    public UserDto getUserById(Long id) throws UserNotFoundException;
    public UserDto updateUser(Long id, UserDto userDetails)throws UserNotFoundException;
    public void deleteUser(Long id)throws UserNotFoundException;
}
