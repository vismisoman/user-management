package com.example.user_management;
import com.usermanagment.controller.UserController;
import com.usermanagment.dto.UserDto;
import com.usermanagment.exception.UserNotFoundException;
import com.usermanagment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws UserNotFoundException {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("test");
        userDto.setFirstName("test0");
        userDto.setLastName("test1");
        userDto.setEmail("test@test.com");
        userDto.setPhoneNumber("34324324");
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.createUser(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void createUser_ShouldReturnInternalServerError_WhenExceptionThrown() throws UserNotFoundException {
        when(userService.createUser(any(UserDto.class))).thenThrow(new UserNotFoundException("User not found"));

        ResponseEntity<UserDto> response = userController.createUser(new UserDto());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() throws UserNotFoundException {
        UserDto user1 = new UserDto();
        UserDto user2 = new UserDto();
        List<UserDto> userList = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(userList);

        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void getUserById_ShouldReturnUser_WhenFound() throws UserNotFoundException {
        UserDto userDto = new UserDto();
        when(userService.getUserById(anyLong())).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void getUserById_ShouldReturnNotFound_WhenUserDoesNotExist() throws UserNotFoundException {
        when(userService.getUserById(anyLong())).thenThrow(new UserNotFoundException("User not found"));

        ResponseEntity<UserDto> response = userController.getUserById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws UserNotFoundException {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");
        when(userService.updateUser(anyLong(), any(UserDto.class))).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.updateUser(1L, userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void updateUser_ShouldReturnNotFound_WhenUserDoesNotExist() throws UserNotFoundException {
        when(userService.updateUser(anyLong(), any(UserDto.class))).thenThrow(new UserNotFoundException("User not found"));

        ResponseEntity<UserDto> response = userController.updateUser(1L, new UserDto());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteUser_ShouldReturnNoContent_WhenDeletedSuccessfully() throws UserNotFoundException {
        doNothing().when(userService).deleteUser(anyLong());

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteUser_ShouldReturnNotFound_WhenUserDoesNotExist() throws UserNotFoundException {
        doThrow(new UserNotFoundException("User not found")).when(userService).deleteUser(anyLong());

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

