package com.pds_mark1.personal_data_manager_v1.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name="PDS_Users")
@Data
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private Timestamp dateOfBirth;
    
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] profilePicture;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime  dateJoined;

    @PrePersist
    protected void onCreate() {
        dateJoined = LocalDateTime.now();
    }

    @Override
    public String toString() {
      return "UserDetails [userID=" + userID + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
          + email + ", dateOfBirth=" + dateOfBirth + ", dateJoined=" + dateJoined + "]";
    }

    

}
