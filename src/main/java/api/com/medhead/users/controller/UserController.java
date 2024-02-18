package api.com.medhead.users.controller;


import api.com.medhead.users.model.Patient;
import api.com.medhead.users.model.User;
import api.com.medhead.users.payload.request.RegisterInfoRequest;
import api.com.medhead.users.payload.response.MessageResponse;
import api.com.medhead.users.security.service.PatientService;
import api.com.medhead.users.security.service.UserService;
import jakarta.validation.Valid;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping(UserController.PATH)
public class UserController {
    public static final String PATH = "/api/users";

    @Autowired
    private UserService userService;
    @Autowired
    private PatientService patientService;

    @GetMapping("/all")
    public List<User> getUsersList() {
        return userService.getUsersList();
    }

    @GetMapping("/{userId}")
    public Patient getPatient(@PathVariable int userId) {
        return patientService.getPatient(userId);
    }

    @GetMapping("/email/{email}")
    public Optional<User> getRegisteredUser(@PathVariable String email) {
        return userService.getRegisteredUser(email);
    }

    @PostMapping("/patient")
    public ResponseEntity<?> registerPatientInfo(@Valid @RequestBody RegisterInfoRequest registerInfoRequest) throws JSONException, IOException, InterruptedException {
        Patient p = patientService.registerPatientInfo(registerInfoRequest);
        if (p.getLatitude() == 0) {
            return ResponseEntity.badRequest().body(new MessageResponse("The address could not be geolocalized"));
        }
        return ResponseEntity.ok(new MessageResponse("Infos registered successfully!"));
    }

}
