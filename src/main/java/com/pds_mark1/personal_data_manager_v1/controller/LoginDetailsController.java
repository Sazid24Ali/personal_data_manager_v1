package com.pds_mark1.personal_data_manager_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import org.springframework.security.crypto.password.PasswordEncoder;

import com.pds_mark1.personal_data_manager_v1.controller.Utlities.UtilityForController;
import com.pds_mark1.personal_data_manager_v1.entities.LoginDetails;
import com.pds_mark1.personal_data_manager_v1.entities.UserDetails;

import com.pds_mark1.personal_data_manager_v1.service.Implementation.LoginDetailsServiceImpl;

@RestController
@RequestMapping("/login")
public class LoginDetailsController {
    @Autowired
    private LoginDetailsServiceImpl ServiceObject;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    UtilityForController utilityObject; // To Get UserData
    // ( Which Will Be Used In Insert And Update Methods To set the Foreign Key )

    public LoginDetailsController(UtilityForController utilityObject) {
        this.utilityObject = utilityObject;
    }

    // @GetMapping("/get/{userid}")
    public ResponseEntity<?> getLoginDetails(@PathVariable Integer userId) {
        LoginDetails obj = ServiceObject.getLoginDetailsByuserID(userId);
        if (obj != null) {
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/insert/{userId}")
    public ResponseEntity<?> insertLoginDetails(@PathVariable Integer userId, @RequestBody LoginDetails loginDetails) {
        UserDetails exisitingUserDetails = utilityObject.getuserData(userId);
        //loginDetails.setPassword(passwordEncoder.encode(loginDetails.getPassword()));// Encoding Password
        if (exisitingUserDetails == null) {
            return new ResponseEntity<>("User Not Found  \n Register First ", HttpStatus.NOT_FOUND);
        }
        loginDetails.setUser(exisitingUserDetails);
        // System.out.println("From insert function "+loginDetails);
        try {
            ServiceObject.insertLgDetails(loginDetails);
            return new ResponseEntity<>(
                    " Login Credentials for " + exisitingUserDetails.getLastName() + " are Inserted Succesfully ",
                    HttpStatus.OK);
        } catch (Exception e) {
        }

        return new ResponseEntity<>(" User Already Exist ", HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateLoginDetails(@PathVariable Integer userId, @RequestBody LoginDetails loginDetails) {
        UserDetails exisitingUserDetails = utilityObject.getuserData(userId);

        if (exisitingUserDetails == null) {
            return new ResponseEntity<>("UserId Not Found ", HttpStatus.NOT_FOUND);
        }
        Integer LoginId = ServiceObject.getLoginDetailsByuserID(userId).getId();
        System.out.println(LoginId + " This is Login Id");
        loginDetails.setUser(exisitingUserDetails);
        loginDetails.setId(LoginId);
        System.out.println("From Update function " + loginDetails);
        ServiceObject.updateLgDetails(loginDetails);
        return new ResponseEntity<>(
                " Login Credentials for " + exisitingUserDetails.getLastName() + " Data Updated Succesfully ",
                HttpStatus.OK);
    }

    // Remove Login cred
    // @DeleteMapping("/delete/{userId}") // Deletion
    /*
     * Commented This Mapping Because the Login Cred Should not be until THe User
     * Object ois Present
     * So , THis Function is present to delete the Login Cred When the User tables
     * Details Are Deleted
     * This dunction is called in UsersControllerUtility Class
     */
    public ResponseEntity<?> removeLoginDetails(@PathVariable Integer userId) {
        String response = ServiceObject.deleteByuserid(userId);
        if (response != null) {
            return new ResponseEntity<>("Removed User From Login Details", HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Found ", HttpStatus.NOT_FOUND);

    }

}
