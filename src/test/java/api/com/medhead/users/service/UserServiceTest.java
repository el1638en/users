package api.com.medhead.users.service;


import api.com.medhead.users.model.ERole;
import api.com.medhead.users.model.Role;
import api.com.medhead.users.model.User;
import api.com.medhead.users.repository.UserRepository;
import api.com.medhead.users.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    User u1 = new User(2,  "password", "jayden.reid@test.com");
    User u2 = new User(4, "password","test@test.com");

    Role role = new Role(ERole.ROLE_USER);
    Set<Role> roles  = new HashSet<>();
    List<User> userList=new ArrayList<>();

    @BeforeEach
    void setup_test(){
        userList.add(u1);
        userList.add(u2);

        roles.add(role);
    }

    @Test
    void getUsersList() {
        when(userRepository.findAll()).thenReturn(userList);
        List<User>users = userService.getUsersList();
        assertEquals("test@test.com", users.get(1).getEmail());
    }

    @Test
    void getRegisteredUser() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.ofNullable(u2));
        assertEquals(4, userService.getRegisteredUser(u2.getEmail()).get().getId());
    }

    @Test
    void existsByEmail() {
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);
        assertEquals(true, userService.existsByEmail(u2.getEmail()));
    }

    @Test
    void save() {
        when(userRepository.save(any())).thenReturn(u2);
        u2.setRoles(roles);
        User u = userService.save(u2);
        assertEquals(role.getName(), u.getRoles().iterator().next().getName());
    }
}
