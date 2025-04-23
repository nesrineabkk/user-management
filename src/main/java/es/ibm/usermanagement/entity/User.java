package es.ibm.usermanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(length = 16, nullable = false, unique = true)
    @JsonProperty("uuid")
    private String uuid;

    @NotBlank
    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;

    @Min(0)
    @JsonProperty("age")
    private int age;

    @JsonProperty("subscribed")
    private boolean subscribed;

    @NotBlank
    @JsonProperty("postal_code")
    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "created_at", updatable = false)
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public User() {
        // No-args constructor
    }

    public User(String uuid, String firstName, String lastName, int age, boolean subscribed, String postalCode, LocalDateTime createdAt) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.subscribed = subscribed;
        this.postalCode = postalCode;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        this.uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
