package api.com.medhead.users.service;


import api.com.medhead.users.model.ERole;
import api.com.medhead.users.model.Patient;
import api.com.medhead.users.model.Role;
import api.com.medhead.users.model.User;
import api.com.medhead.users.payload.request.SignupRequest;
import api.com.medhead.users.security.service.AuthService;
import api.com.medhead.users.security.service.PatientService;
import api.com.medhead.users.security.service.RoleService;
import api.com.medhead.users.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private RoleService roleService;
    @Mock
    private UserService userService;
    @Mock
    private PatientService patientService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private SignupRequest signupRequest = new SignupRequest();
    private User user = new User();
    private Patient patient = new Patient();
    private Role role = new Role(ERole.ROLE_USER);

    @BeforeEach
    void setup_test(){
        signupRequest.setUsername("besoindevacaces@test.com");
        signupRequest.setPassword("password");

        user.setEmail(signupRequest.getUsername());
        user.setPassword(signupRequest.getPassword());

        patient.setEmail(user.getEmail());
    }

    @Test
    void signupUser() {
        when(userService.save(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("hashedPasswordWithLotsOfEncoding");
        when(roleService.findByName(any())).thenReturn(Optional.ofNullable(role));
        when(patientService.save(any())).thenReturn(patient);
        authService.signupUser(signupRequest);
    }
}
