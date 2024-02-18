package api.com.medhead.users.service;


import api.com.medhead.users.model.ERole;
import api.com.medhead.users.model.Role;
import api.com.medhead.users.repository.RoleRepository;
import api.com.medhead.users.security.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    Role r = new Role(ERole.ROLE_ADMIN);

    @Test
    void findByName() {
        when(roleRepository.findByName(any())).thenReturn(Optional.ofNullable(r));
        Optional<Role> role = roleService.findByName(ERole.ROLE_ADMIN);
        assertEquals(r.getName(), role.get().getName());
    }
}
