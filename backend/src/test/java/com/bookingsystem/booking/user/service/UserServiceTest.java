package com.bookingsystem.booking.user.service;

import com.bookingsystem.booking.shared.auth.tokens.RefreshTokenService;
import com.bookingsystem.booking.shared.crypto.PasswordHasher;
import com.bookingsystem.booking.user.api.dtos.response.UserDTO;
import com.bookingsystem.booking.user.data.UserRepository;
import com.bookingsystem.booking.user.domain.entities.User;
import com.bookingsystem.booking.user.domain.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHasher passwordHasher;

    @Mock
    private RefreshTokenService refreshTokenService;

    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {

    }

    @InjectMocks
    private UserService userService; // This is what I want to test

    @Nested
    @DisplayName("Get all users test")
    class GetAllUserTest {

        @Test
        @DisplayName("Should get all users")
        void shouldGetAllUserSuccessfully() {
            // Given
            User u1 = new User();
            u1.setId(1L);
            u1.setUsername("Prompt");
            u1.setEmail("test@gmail.com");
            u1.setRole(Role.USER);

            User u2 = new User();
            u2.setId(2L);
            u2.setUsername("Alice");
            u2.setEmail("alice@gmail.com");
            u2.setRole(Role.ADMIN);

            when(userRepository.findAll()).thenReturn(List.of(u1, u2));

            // When
            List<UserDTO> result = userService.getAllUser();

            // Then
            assertNotNull(result);
            assertEquals(2, result.size());

            assertEquals(1L, result.get(0).id());
            assertEquals("Prompt", result.get(0).username());
            assertEquals("test@gmail.com", result.get(0).email());
            assertEquals(Role.USER, result.get(0).role());

            verify(userRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return empty list when no users")
        void shouldReturnEmptyList() {
            // Given
            when(userRepository.findAll()).thenReturn(List.of());

            // When
            List<UserDTO> result = userService.getAllUser();

            // Then
            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(userRepository, times(1)).findAll();
        }
    }
}