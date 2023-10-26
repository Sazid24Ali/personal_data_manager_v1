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
@Table(name="PDS_LoginCred")
@Data
public class LoginDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userID",unique = true)// ( To Generate Only One Instance Of the Login Cred )
    private UserDetails user;
    
    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
/* 
    @Column(nullable = false)
    private String accountStatus; */

    @Override
    public String toString() {
        return "LoginDetails [id=" + id + ", user=" + user + ", username=" + username + ", password=" + password;
    }

}
