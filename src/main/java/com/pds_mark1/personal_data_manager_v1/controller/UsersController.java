package com.pds_mark1.personal_data_manager_v1.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pds_mark1.personal_data_manager_v1.controller.Utlities.UtilityForController;
import com.pds_mark1.personal_data_manager_v1.entities.UserDetails;
import com.pds_mark1.personal_data_manager_v1.exceptions_handler.MaxFileSizeExceededException;
import com.pds_mark1.personal_data_manager_v1.service.UsersService;
//import com.pds_mark1.personal_data_manager_v1.service.Implementation.UsersServiceImpl;


@RestController
@RequestMapping("/user")
@CrossOrigin
public class UsersController {
    @Autowired
    UsersService ServiceObject;
    //UsersServiceImpl ServiceObject;
    /*
     * Here We can use the Service InferFace Object OR ServiceImpl Class 
     * If We use Service Interface THis is called Loose Coupling
     * If we use ServiceImpl Class It is called Tight Coupling 
     * I am Using Service Interface Object in this UsersController only In other Controller Classes I am Using UserServiceImpl  
     */
    
    UtilityForController utilityObject;

    public UsersController(UtilityForController utilityObject) {
        this.utilityObject = utilityObject;
    }

    // Dislpay
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable("id") Integer id) {
        //To Retrive The UserData Using Id
        System.out.println("Retriving User Data ");
        
        try {
            // Map<String, String> body = new HashMap<>();
            Map<String, UserDetails> body = new HashMap<>();
            UserDetails obj = ServiceObject.getUser(id);
            body.put("userData",obj);
            // body.put("userData","To Send");
            // System.out.println("Found The Data "+obj);
            // return new ResponseEntity<>(obj, HttpStatus.OK);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("\n\n AN Exception Occured From UserController getUserDetails \n\n"+e);
        }
        Map<String, String> body = new HashMap<>();
        body.put("msg", "User Not Found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    
    // Remove User
    @DeleteMapping("/delete/{userId}") // Deletion
    public ResponseEntity<?> deleteUserDetails(@PathVariable Integer userId) {
        // Perform user deletion here
        utilityObject.removeData(userId);
        boolean success = ServiceObject.deleteUser(userId);
        if (success) {
            System.out.println("Succssully deleted From UserController : deleteUserDetails");
            
            return ResponseEntity.ok("Successfully Deleted \n");
        }
        System.out.println("\n\nSometing went Wrong { From : UserCOntroller deleteUserDetails }\n\n");
        return new ResponseEntity<>("UserNot Found", HttpStatus.NOT_FOUND);

    }

    //Insert User
    @PostMapping("/register")
    //Insert A New User
    public ResponseEntity<?> registerUserDetails(
            @RequestParam(name = "profilePic" , required = false) MultipartFile profilePic,
            @RequestParam("userData") String userData) {

        Map<String, String> body = new HashMap<>();
        try {
            UserDetails userObject = utilityObject.insertUserDetails(userData, profilePic);
            int userID = ServiceObject.insertUser(userObject);
            if (userID != 0) {
                body.put("msg","Inserted Data");
                body.put("userId",String.format("%d",userID));
                return new ResponseEntity<>(body, HttpStatus.OK);
            }
            body.put("msg","Email Already In Use");
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        } catch (MaxFileSizeExceededException e) {
            body.put("msg","File Is Too Large \n Upload Limit :"+ UtilityForController.SIZE_IN_KB + "KB");
            return new ResponseEntity<>(body,HttpStatus.PAYLOAD_TOO_LARGE);
        } catch (Exception e) {
            System.out.println(e);
            body.put("msg","Unable to Process Your Request <br> Please Try After SomeTime <br>"+e);
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
    }

    //Update User Details
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUserDetails(
            @PathVariable Integer userId,
            @RequestParam("profilePic") MultipartFile profilePic,
            @RequestParam("userData") String userData) {
                //To Update the User Details
        UserDetails existingUser = ServiceObject.getUser(userId);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            UserDetails toupdate = utilityObject.objectMapper.readValue(userData,UserDetails.class);

            UserDetails userWithSameEmail = ServiceObject.getUserByEmail(toupdate.getEmail());
            if (userWithSameEmail != null &&
                    !userWithSameEmail.getUserID().equals(userId)) {
                return new ResponseEntity<>("Email address is already in use by another user",HttpStatus.BAD_REQUEST);
            }
            UserDetails updatedDetails = utilityObject.updateUserDetails(existingUser, toupdate, profilePic);
            boolean success = ServiceObject.updateUser(updatedDetails);
            if (success) {
                return ResponseEntity.ok("Updated The  Data\n" + updatedDetails);
            }

        } catch (Exception e) {
            System.out.println("Update User Exception ");
            e.printStackTrace();
        }
        return new ResponseEntity<>("Unable to Process Your Request ", HttpStatus.BAD_REQUEST);

    }

}
