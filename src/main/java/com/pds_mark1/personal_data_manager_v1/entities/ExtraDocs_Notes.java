package com.pds_mark1.personal_data_manager_v1.entities;


import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "PDS_XDocsNotes")
@Data
public class ExtraDocs_Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userID",nullable = false)
    private UserDetails user;

    @Column(nullable = false,unique = true) // Since We are in update and delete.
    private String title;

    @Column(nullable = false)
    private String description;

    /* @Column(nullable = false)
    private Integer importanceLevel; */

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] picture;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date lastUpdatedOn;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
        lastUpdatedOn = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedOn = new Date();
    }

    @Override
    public String toString() {
        return "ExtraDocs_Notes [id=" + id + ", user=" + user + ", title=" + title + ", description=" + description
                + ", picture=" + Arrays.toString(picture) + ", creationDate=" + creationDate + ", lastUpdatedOn="
                + lastUpdatedOn + "]";
    }

    

}
