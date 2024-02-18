package api.com.medhead.users.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "patients",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "ssnumber"),
                @UniqueConstraint(columnNames = "email")
        })
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "postcode")
    private String postCode;
    @Column(name = "longitude")
    private double longitude = 0;
    @Column(name = "latitude")
    private double latitude = 0;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "birthdate")
    private LocalDate birthdate;
    @Column(name = "ssnumber")
    private String socialSecurityNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Patient(String email) {
        this.email = email;
    }

    public Patient(Integer id, String firstName, String lastName, String address, String city, String postCode, double longitude, double latitude, String phone, String email, LocalDate birthdate, String socialSecurityNumber, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.postCode = postCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phone = phone;
        this.email = email;
        this.birthdate = birthdate;
        this.socialSecurityNumber = socialSecurityNumber;
        this.user = user;
    }

    public Patient() {
    }
}
