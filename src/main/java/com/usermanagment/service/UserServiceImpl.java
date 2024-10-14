package com.usermanagment.service;

import com.usermanagment.dto.UserDto;
import com.usermanagment.entity.User;
import com.usermanagment.exception.UserNotFoundException;
import com.usermanagment.mapper.UserMapper;
import com.usermanagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User usr = userRepository.save(user);
        return userMapper.toDto(usr);
    }

    public List<UserDto> getAllUsers() {

        List<User> userDtoList = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        userDtoList.forEach(user -> userDtos.add(userMapper.toDto(user)));

        return userDtos;
    }

    public UserDto getUserById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return userMapper.toDto(user);
        } else {
            throw new UserNotFoundException("User with id [" + id + "] not found");
        }
    }


    public UserDto updateUser(Long id, UserDto userDetails) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(userDetails.getUsername());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());

        User usr = userRepository.save(user);
        return userMapper.toDto(usr);
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
