package com.ynov.grosieuxClement.models;

import com.ynov.grosieuxClement.controllers.UserController;
import com.ynov.grosieuxClement.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class UserTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById() {
        // Créer un mock User
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setFullname("Test User");
        mockUser.setPassword("password");
        mockUser.setValid(true);
        mockUser.setCode(123456);

        // Configurer le mock pour retourner le mockUser lorsque getUserById est appelé
        when(userService.getUserById(1L)).thenReturn(Optional.of(mockUser));

        // Appeler la méthode à tester
        Optional<User> result = userService.getUserById(1L);

        // Vérifier que le résultat est le mockUser
        assertEquals(mockUser, result.get());
    }
}