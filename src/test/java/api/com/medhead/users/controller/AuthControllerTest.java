package api.com.medhead.users.controller;

import api.com.medhead.users.payload.request.LoginRequest;
import api.com.medhead.users.payload.request.SignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private SignupRequest signupRequest;
    private String username;
    private String password;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer("postgres:15-alpine");


    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);

    }

    @BeforeEach
    void setup_test() {
        this.username = "test@test.com";
        this.password = "password";
        signupRequest = new SignupRequest();
        signupRequest.setUsername(username);
        signupRequest.setPassword(password);
    }

    @Test
    public void registerUser() throws Exception {
        // GIVEN

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders
                .post(AuthController.PATH+"/signup")
                .content(objectMapper.writeValueAsString(signupRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
        // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void authenticateUser() throws Exception {
        // GIVEN

        mockMvc.perform(MockMvcRequestBuilders
                .post(AuthController.PATH+"/signup")
                .content(objectMapper.writeValueAsString(signupRequest)));

        // WHEN
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(signupRequest.getUsername());
        loginRequest.setPassword(signupRequest.getPassword());

        mockMvc.perform(MockMvcRequestBuilders
                .post(AuthController.PATH+"/signin")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
        // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void authenticateUnknownUser() throws Exception {
        // GIVEN

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("unknown_user@test.com");
        loginRequest.setPassword("unknown_password");

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders
                .post(AuthController.PATH+"/signin")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
        // THEN
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

}
