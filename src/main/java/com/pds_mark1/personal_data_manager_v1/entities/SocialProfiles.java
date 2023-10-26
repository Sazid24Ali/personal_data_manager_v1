package com.pds_mark1.personal_data_manager_v1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PDS_SocialProf")
@Data
public class SocialProfiles {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userID",nullable = false)
    private UserDetails user;

    @Column(nullable = false,unique = true)
    private String socialPlatformName;

    @Column(nullable = false)
    private String socialPlatformUsername;

    @Column(nullable = false)
    private String socialPlatformURL;

    @Override
    public String toString() {
        return "SocialProfiles [id=" + id + ", user=" + user + ", socialPlatformName=" + socialPlatformName
                + ", socialPlatformUsername=" + socialPlatformUsername + ", socialPlatformURL=" + socialPlatformURL
                + "]";
    }

    
}
