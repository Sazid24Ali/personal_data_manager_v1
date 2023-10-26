package com.pds_mark1.personal_data_manager_v1.entities;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="PDS_EduDetails")
@Data
public class EducationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userID",nullable = false)
    private UserDetails user;

    @Column(nullable = false,unique = true) // Since we usethis in delete and update operation
    private String eduQualification;

    @Column(nullable = false)
    private String schoolInstitution;

    @Column(nullable = false)
    private Integer graduationYear = -1; /// Kept a default value as it was raising an Data Integrity error 

    @Column(nullable = false)
    private Double gpaOrGrades;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] certificates;

    @Override
    public String toString() {
        return "EducationDetails [id=" + id + ", user=" + user + ", eduQualification=" + eduQualification
                + ", schoolInstitution=" + schoolInstitution + ", graduationYear=" + graduationYear + ", gpaOrGrades="
                + gpaOrGrades + ", certificates=" + Arrays.toString(certificates) + "]";
    }

}
