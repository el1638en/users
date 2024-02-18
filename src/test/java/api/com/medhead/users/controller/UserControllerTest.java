package api.com.medhead.users.controller;

import api.com.medhead.users.payload.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class UserControllerTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer("postgres:15-alpine");


    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);

    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    protected String getAccessToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jackson.michell@test.com");
        loginRequest.setPassword("password");
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post(AuthController.PATH + "/signin")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("accessToken").toString();
    }


    @BeforeEach
    public void setup_test() {
    }

    @Test
    public void getUsersList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(UserController.PATH + "/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

//    @Test
//    public void getPatient() {
//        when(patientRepository.findByUserId(p1.getUser().getId())).thenReturn(p1);
//        Patient p = userController.getPatient(u1.getId());
//        assertEquals("Doctor", p.getLastName());
//    }
//
//    @Test
//    public void getRegisteredUser() {
//        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(u1));
//        Optional<User> user = userService.getRegisteredUser(u1.getEmail());
//        assertEquals("bluebox@badwolfbay.com", user.get().getEmail());
//    }
//
//    @Test
//    public void registerPatientInfo() throws JSONException, IOException, InterruptedException {
//        when(patientRepository.findByEmail(u1.getEmail())).thenReturn(p1);
//        when(patientRepository.save(any())).thenReturn(p1);
//        Patient patient = patientService.registerPatientInfo(registerInfoRequest);
//        assertEquals(51.532844, patient.getLatitude());
//        assertEquals(-0.194594, patient.getLongitude());
//    }
}
