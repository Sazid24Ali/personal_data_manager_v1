package com.pds_mark1.personal_data_manager_v1.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
@CrossOrigin
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

    @GetMapping("/get/{userName}/{password}")
    public ResponseEntity<?> getpassword(@PathVariable String userName,@PathVariable String password) {
        String loginData = ServiceObject.getDataByUserName(userName);
        System.out.println("Given Data  = "+userName+"\n Data Got = "+loginData);
        Map<String, String> body = new HashMap<>();
        
        if (loginData != null) {
            String obj[] = loginData.split(",");
            
            if(obj[0].equals(password)){
                System.out.println("UserId:"+obj[1]);
                 
                 body.put("msg","Login Success");
                 body.put("userId", obj[1]);
                return new ResponseEntity<>(body, HttpStatus.OK);
            }
            body.put("msg","Login Failed");
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
            
        }
        body.put("msg","User Not Found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    // @GetMapping("/get/{userid}")
    public ResponseEntity<?> getLoginDetails(@PathVariable Integer userid) {
        LoginDetails obj = ServiceObject.getLoginDetailsByuserID(userid);
        if (obj != null) {
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/insert/{userId}")
    public ResponseEntity<?> insertLoginDetails(@PathVariable Integer userId, @RequestBody LoginDetails loginDetails) {
        UserDetails exisitingUserDetails = utilityObject.getuserData(userId);
        Map<String, String> body = new HashMap<>();
        //loginDetails.setPassword(passwordEncoder.encode(loginDetails.getPassword()));// Encoding Password
        if (exisitingUserDetails == null) {
            body.put("msg", "User Not Found <br> Register First ");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
        loginDetails.setUser(exisitingUserDetails);
        System.out.println("From insert function "+loginDetails);
        try {
            ServiceObject.insertLgDetails(loginDetails);
            body.put("msg", " Login Credentials for " + exisitingUserDetails.getLastName() + " are Inserted Succesfully ");
            return new ResponseEntity<>(body,HttpStatus.OK);
        // }catch(DataIntegrityViolationException e){
        //         body.put("msg", " Don't Try Buddy ");
        //         return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        // } 
        }
        catch (Exception e) {
            System.out.println(e);
        }
        body.put("msg", " UserName Already Taken <br> OR ... <sub>(Don't Play Wth Url)</sub>");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

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
     * Commented This Mapping Because the Login Cred Should not be deleted until THe User
     * Object is Present
     * So , THis Function is present to delete the Login Cred When the User tables
     * Details Are Deleted
     * This dunction is called in UsersControllerUtility Class
     * So, No mapping is given Here
     */
    public ResponseEntity<?> removeLoginDetails(@PathVariable Integer userId) {
        String response = ServiceObject.deleteByuserid(userId);
        if (response != null) {
            return new ResponseEntity<>("Removed User From Login Details", HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Found ", HttpStatus.NOT_FOUND);

    }

}
