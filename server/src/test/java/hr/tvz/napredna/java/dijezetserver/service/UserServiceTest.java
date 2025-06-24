package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.model.UserRefreshToken;
import hr.tvz.napredna.java.dijezetserver.model.UserRole;
import hr.tvz.napredna.java.dijezetserver.repository.UserRefreshTokenRepository;
import hr.tvz.napredna.java.dijezetserver.repository.UserRepository;
import hr.tvz.napredna.java.dijezetserver.request.UserRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest extends BaseTest {
    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private UserRefreshTokenRepository userRefreshTokenRepository;

    @Test
    void shouldGetUsers() {
        when(userRepository.findAll()).thenReturn(USERS);

        var users = userService.getAll();
        assertEquals(users.size(), USERS.size());
    }

    @Test
    void shouldGetUserByUsername(){
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(USER));

        var user = userService.getByUserName(anyString());
        assertEquals(user.getUsername(), USER.getUsername());
        assertEquals(user.getRole(), USER.getRole());
    }

    @Test
    void shouldSaveUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(USER);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        userService.create(USER_REQUEST);

        verify(userRepository, times(1)).save(userCaptor.capture());
        var user = userCaptor.getValue();
        assertEquals(user.getUsername(), USER_REQUEST.getUsername());
    }

    @Test
    void shouldThrowExceptionIfUserExists() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(USER));
        assertThrows(IllegalArgumentException.class, () -> userService.create(USER_REQUEST), "Username " + USER_REQUEST.getUsername() + " already exists" );
    }

    @Test
    void shouldUpdateUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(USER));
        when(userRepository.save(any())).thenReturn(USER);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        userService.update(USER_REQUEST);

        verify(userRepository, times(1)).save(userCaptor.capture());
        var user = userCaptor.getValue();
        assertEquals(user.getUsername(), USER_REQUEST.getUsername());
    }

    @Test
    void shouldThrowExceptionIfUserDoesNotExistOnUpdate() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.update(USER_REQUEST), "Username " + USER_REQUEST.getUsername() + " does not exist");
    }

    @Test
    void shouldDeleteUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER));
        doNothing().when(userRepository).delete(USER);

        userService.delete(USER.getId());
        verify(userRepository, times(1)).delete(any());
    }

    @Test
    void shouldThrowExceptionIfUserDoesNotExistOnDelete() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.delete(USER.getId()), "Username " + USER_REQUEST.getUsername() + " does not exist");
    }

    @Test
    void shouldGetActiveRefreshToken() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(USER));
        when(userRefreshTokenRepository.findByUser(any())).thenReturn(Optional.of(USER_REFRESH_TOKEN_ACTIVE));
        String token = userService.getRefreshToken(USER.getUsername());

        verify(userRefreshTokenRepository, times(1)).findByUser(any());
        verify(userRefreshTokenRepository, never()).delete(any());
        assertEquals(token, USER_REFRESH_TOKEN_ACTIVE.getRefreshToken());
    }

    @Test
    void shouldReplaceExpiredToken() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(USER));
        when(userRefreshTokenRepository.findByUser(any())).thenReturn(Optional.of(USER_REFRESH_TOKEN_EXPIRED));

        String token = userService.getRefreshToken(USER.getUsername());

        verify(userRefreshTokenRepository, times(1)).findByUser(any());
        verify(userRefreshTokenRepository, times(1)).delete(any());
        assertNotEquals(token, USER_REFRESH_TOKEN_EXPIRED.getRefreshToken());
    }

    @Test
    void shouldCreateNewTokenWhenNoneExist() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(USER));
        when(userRefreshTokenRepository.findByUser(any())).thenReturn(Optional.empty());

        String token = userService.getRefreshToken(USER.getUsername());

        verify(userRefreshTokenRepository, times(1)).findByUser(any());
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldThrowExceptionIfUserDoesNotExistOnGetRefreshToken() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getRefreshToken(USER.getUsername()), "User with username " + USER.getUsername() + " does not exist");
    }

    @Test
    void shouldGetUserByRefreshToken() {
        when(userRefreshTokenRepository.findByRefreshToken(any())).thenReturn(Optional.of(USER_REFRESH_TOKEN_ACTIVE));

        var user = userService.getByRefreshToken(USER_REFRESH_TOKEN_ACTIVE.getRefreshToken());

        verify(userRefreshTokenRepository, times(1)).findByRefreshToken(any());
        assertEquals(user.getUsername(), USER.getUsername());
    }
}
