package com.pds_mark1.personal_data_manager_v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.pds_mark1.personal_data_manager_v1.entities.EducationDetails;
import com.pds_mark1.personal_data_manager_v1.entities.UserDetails;
import com.pds_mark1.personal_data_manager_v1.exceptions_handler.MaxFileSizeExceededException;
import com.pds_mark1.personal_data_manager_v1.service.Implementation.EducationDetailsServiceImpl;

@RestController
@RequestMapping("/edu")
public class EducationDetailsController {

    @Autowired
    EducationDetailsServiceImpl ServiceObject;

    UtilityForController utilityObject;

    public EducationDetailsController(UtilityForController utilityObject) {
        this.utilityObject = utilityObject;
    }

    @GetMapping("/getAll/{userID}")
    public ResponseEntity<?> getAlleduDetails(@PathVariable Integer userID) {
        List<EducationDetails> listOfeduDetails = ServiceObject.getAllEducDetailsByuserid(userID);
        if (listOfeduDetails != null) {
            System.out.println("Inside If ");
            return new ResponseEntity<>(listOfeduDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>("No Education Records Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/insert/{userID}")
    public ResponseEntity<?> insertEduDetails(@PathVariable Integer userID, @RequestParam String eduDetails,
            @RequestParam("certificate") MultipartFile certificate) {
        

        System.out.println(eduDetails);
        UserDetails exisitingUserDetails = utilityObject.getuserData(userID);
        if (exisitingUserDetails == null) {
            return new ResponseEntity<>("User Not Found  \n Register First ", HttpStatus.NOT_FOUND);
        }
        try {
            EducationDetails toInsert = utilityObject.insertEducationDetails(userID,eduDetails, certificate);
            toInsert.setUser(exisitingUserDetails);
            ServiceObject.insertEducDetails(toInsert);
            return new ResponseEntity<>(toInsert.getEduQualification() + " was  Inserted Successfully ", HttpStatus.OK);

        } catch (MaxFileSizeExceededException e) {
            return new ResponseEntity<>("Maximum file size exceeded " + UtilityForController.SIZE_IN_KB + "KB",
                    HttpStatus.PAYLOAD_TOO_LARGE);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("The Given Tiltle alredy Exists",
                    HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("UnHandled Error Raised "+e,
                    HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update/{eduQual}/{userID}")
    public ResponseEntity<?> updateEduDetails(@PathVariable String eduQual, @PathVariable Integer userID,
            @RequestParam String eduDetails, @RequestParam("certificate") MultipartFile certificate) {

        UserDetails exisitingUserDetails = utilityObject.getuserData(userID);
        EducationDetails existingEduDetails = ServiceObject.getRecordByeduDetailsAndUserId(eduQual, userID);
        System.out.println("Education Details : " + existingEduDetails);

        if (existingEduDetails == null || exisitingUserDetails == null) {
            return new ResponseEntity<>("Data Not Found ( Check The Eduaction Qualification and UserID ) ", HttpStatus.NOT_FOUND);
        }
        try {
            EducationDetails toUpdate = utilityObject.objectMapper.readValue(eduDetails,EducationDetails.class);
            EducationDetails updatedEducationDetails = utilityObject.updateEducationDetails(existingEduDetails, toUpdate, certificate);
            updatedEducationDetails.setUser(exisitingUserDetails);
            String updatedMessage = ServiceObject.updateEducDetails(updatedEducationDetails);
            return  new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } catch (MaxFileSizeExceededException e) {
            return new ResponseEntity<>("Maximum file size exceeded " + UtilityForController.SIZE_IN_KB + "KB",
                    HttpStatus.PAYLOAD_TOO_LARGE);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("TryIng To Change the eduQual Which Already Exist ", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception Occured " + e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{eduQual}/{userID}")
    public ResponseEntity<?> deleteEduDetails(@PathVariable String eduQual, @PathVariable Integer userID){
        try {
            ServiceObject.deleteRecordByeduQualAndUserId(eduQual, userID);
            return new ResponseEntity<>("Deleted The " + eduQual + " Succesfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Data Not Found ( Check The Title and UserID ) " , HttpStatus.NOT_FOUND);
        }
    }
}
