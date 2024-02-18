package api.com.medhead.users.security.service;


import api.com.medhead.users.model.Patient;
import api.com.medhead.users.payload.request.RegisterInfoRequest;
import api.com.medhead.users.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.Character.isDigit;

@Service
public class PatientService {
    private static final String GEO_API_KEY = "2ed374a9-4d97-4d99-b892-a8063032d84b";
    private static final String GEO_API_URL = "https://graphhopper.com/api/1/geocode?q=";
    @Autowired
    private PatientRepository patientRepository;

    public Patient getPatient(int userId) {
        return patientRepository.findByUserId(userId);
    }

    public Patient findPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient setUpPatient(RegisterInfoRequest registerInfoRequest) {
        Patient p = findPatientByEmail(registerInfoRequest.getEmail());
        p.setFirstName(registerInfoRequest.getFirstName());
        p.setLastName(registerInfoRequest.getLastName());
        p.setAddress(registerInfoRequest.getAddress());
        p.setCity(registerInfoRequest.getCity());
        p.setPhone(registerInfoRequest.getPhone());
        p.setPostCode(registerInfoRequest.getPostCode());
        p.setSocialSecurityNumber(registerInfoRequest.getNhsNumber());
        //convert String to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        String date = registerInfoRequest.getBirthdate();
        LocalDate birthdate = LocalDate.parse(date, formatter);
        p.setBirthdate(birthdate);
        return p;
    }

    public String addressWithNumber(JSONObject object, String patientAddress) throws JSONException {
        String houseNb = object.get("housenumber").toString();
        String substringAddress = patientAddress.substring(Math.max(patientAddress.length() - 2, 0));
        if (substringAddress.equalsIgnoreCase("St")) {
            patientAddress = houseNb + " " + patientAddress.substring(0, patientAddress.length() - 2) + "Street";
        } else {
            patientAddress = houseNb + " " + patientAddress;
        }
        return patientAddress;
    }

    public String addressWithoutNumber(JSONObject object, String patientAddress) {
        String substringAddress = patientAddress.substring(Math.max(patientAddress.length() - 2, 0));
        if (substringAddress.equalsIgnoreCase("St")) {
            patientAddress = patientAddress.substring(0, patientAddress.length() - 2) + "Street";
        }
        return patientAddress;
    }

    public Patient registerPatientInfo(RegisterInfoRequest registerInfoRequest) throws IOException, InterruptedException, JSONException { // JSONException,
        Patient p = setUpPatient(registerInfoRequest);

        String patientAddress = URLEncoder.encode(p.getAddress() + " " + p.getCity());
        String urlForGeolocalization = GEO_API_URL + patientAddress + "&key=" + GEO_API_KEY;

        JSONObject objectForCoordinates = getRouteObject(urlForGeolocalization);

        JSONArray hits = objectForCoordinates.getJSONArray("hits");
        String pAddress = "";
        for (int i = 0; i < hits.length(); i++) {
            JSONObject object = hits.getJSONObject(i);
            if (object.has("housenumber") && object.has("street") && isDigit(p.getAddress().charAt(0))) {
                pAddress = addressWithNumber(object, p.getAddress());
            } else if (object.has("street")) {
                pAddress = addressWithoutNumber(object, p.getAddress());
            }
            if (object.has("street") && pAddress.contains(object.get("street").toString()) && object.get("city").equals(p.getCity())) {
                JSONObject point = (JSONObject) object.get("point");
                String lat = point.get("lat").toString();
                String lon = point.get("lng").toString();
                if (lon.length() > 9) {
                    lon = lon.substring(0, 9);
                }
                if (lat.length() > 9) {
                    lat = lat.substring(0, 9);
                }
                p.setLatitude(Double.valueOf(lat));
                p.setLongitude(Double.valueOf(lon));
                patientRepository.save(p);
                return p;
            }
        }
        return p;
    }

    private JSONObject getRouteObject(String url) throws IOException, JSONException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;

        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject object = new JSONObject(response.body());
        return object;
    }
}
