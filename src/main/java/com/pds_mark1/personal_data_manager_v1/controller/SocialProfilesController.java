package com.pds_mark1.personal_data_manager_v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pds_mark1.personal_data_manager_v1.controller.Utlities.UtilityForController;
import com.pds_mark1.personal_data_manager_v1.entities.SocialProfiles;
import com.pds_mark1.personal_data_manager_v1.entities.UserDetails;
import com.pds_mark1.personal_data_manager_v1.service.Implementation.SocialProfilesServiceImpl;

@RestController
@RequestMapping("/social")
@CrossOrigin
public class SocialProfilesController {

    @Autowired
    SocialProfilesServiceImpl ServiceObject;

    UtilityForController utilityObject;

    public SocialProfilesController(UtilityForController utilityObject) {
        this.utilityObject = utilityObject;
    }

    @GetMapping("/getAll/{userID}")
    public ResponseEntity<?> getAllSocialProfiles(@PathVariable Integer userID) {
        List<SocialProfiles> obj = ServiceObject.getAllSocialProfilesByuserId(userID);
        if (obj != null) {
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/insert/{userID}")
    public ResponseEntity<?> insertSocialProfile(@PathVariable Integer userID,
            @RequestBody SocialProfiles socialProfiles) {
        UserDetails exisitingUserDetails = utilityObject.getuserData(userID);
        if (exisitingUserDetails == null) {
            return new ResponseEntity<>("User Not Found  \n Register First ", HttpStatus.NOT_FOUND);
        }
        socialProfiles.setUser(exisitingUserDetails);
        try {
            ServiceObject.insertSProfile(socialProfiles);
            return new ResponseEntity<>(
                    "Inserted the Profile " + socialProfiles.getSocialPlatformName() + " Successfully", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("/n/n/n An Exception Raised " + e + "\n\n");
        }

        return new ResponseEntity<>(" User Already Exist ", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/{platformName}/{userID}")
    public ResponseEntity<?> updateSocialProfile(@PathVariable Integer userID, @PathVariable String platformName,
            @RequestBody SocialProfiles socialProfiles) {
        UserDetails exisitingUserDetails = utilityObject.getuserData(userID);
        SocialProfiles exisitingSocialProfiles = ServiceObject.getRecordByPlatformAnduserId(userID,platformName);
        System.out.println("USer Data : "+exisitingUserDetails+"\n\nSocail Data"+exisitingSocialProfiles);
        if (exisitingUserDetails == null || exisitingSocialProfiles == null) {
            return new ResponseEntity<>("UserId Or the Platform Name Are Wrong ", HttpStatus.NOT_FOUND);
        }
        try {
            socialProfiles.setUser(exisitingUserDetails);
            socialProfiles.setId(exisitingSocialProfiles.getId()); // For security
            ServiceObject.updateSProfiler(socialProfiles);

            return new ResponseEntity<>("Updated The "+platformName+" Successfully ",HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("AN Exception Raised :  "+e,HttpStatus.BAD_REQUEST);
        }
        
    }

    @DeleteMapping("/delete/{platformName}/{userID}")
    public ResponseEntity<?> deleteSocialProfile(@PathVariable Integer userID, @PathVariable String platformName) {
        SocialProfiles getProfile = ServiceObject.getRecordByPlatformAnduserId(userID,platformName);
        if(getProfile == null){
            return new ResponseEntity<>("UserId Or the Platform Name Are Wrong ", HttpStatus.NOT_FOUND);
        }
        Integer getProfileId = getProfile.getId();
        try {
            String message = ServiceObject.deleteSProfile(getProfileId);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("AN Exception Raised :  "+e,HttpStatus.BAD_REQUEST);
        }
        
    }

}
