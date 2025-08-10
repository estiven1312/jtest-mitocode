package org.springframework.samples.petclinic.service.clinicService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.service.UserServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("should save user with roles")
    void shouldSaveUser() {
        // Given
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEnabled(true);

        Role role = new Role();
        role.setName("ROLE_USER");

        Role role2 = new Role();
        role2.setName("ADMIN");

        user.setRoles(Set.of(role, role2));

        // When
        userService.saveUser(user);

        // Then
        verify(userRepository).save(user);
        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_USER")));
        assertTrue(user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")));
    }
    @Test
    void shouldThrowExceptionWhenUserHasNoRoles() {
        // Given
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEnabled(true);
        user.setRoles(Set.of());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    void shouldAddRolePrefixIfMissing() {
        // Given
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEnabled(true);

        Role role = new Role();
        role.setName("USER");

        user.setRoles(Set.of(role));

        // When
        userService.saveUser(user);

        // Then
        verify(userRepository).save(user);
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_USER")));
    }

    @Test
    void shouldThrowExceptionWhenRoleIsNull() {
        // Given
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEnabled(true);
        user.setRoles(null);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

}
