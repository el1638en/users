package api.com.medhead.users.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterInfoRequest {
    @NotEmpty
    @NotBlank
    @Size(max = 100)
    private String firstName;
    @NotEmpty
    @NotBlank
    @Size(max = 100)
    private String lastName;
    @NotEmpty
    @NotBlank
    private String address;
    @NotEmpty
    @NotBlank
    @Size(max = 100)
    private String city;
    @NotEmpty
    @NotBlank
    @Size(max = 10)
    private String postCode;
    private double longitude = 0;
    private double latitude = 0;
    @Size(max = 100)
    private String phone;
    @Size(max = 100)
    private String birthdate;
    @NotEmpty
    @NotBlank
    @Size(max = 100)
    private String nhsNumber;
    @NotBlank
    @NotEmpty
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
