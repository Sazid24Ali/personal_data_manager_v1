package com.pds_mark1.personal_data_manager_v1.controller.Utlities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds_mark1.personal_data_manager_v1.entities.EducationDetails;
import com.pds_mark1.personal_data_manager_v1.entities.ExtraDocs_Notes;
import com.pds_mark1.personal_data_manager_v1.entities.UserDetails;
import com.pds_mark1.personal_data_manager_v1.exceptions_handler.MaxFileSizeExceededException;
import com.pds_mark1.personal_data_manager_v1.service.Implementation.EducationDetailsServiceImpl;
import com.pds_mark1.personal_data_manager_v1.service.Implementation.ExtraDocs_NotesServiceImpl;
import com.pds_mark1.personal_data_manager_v1.service.Implementation.LoginDetailsServiceImpl;
import com.pds_mark1.personal_data_manager_v1.service.Implementation.SocialProfilesServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

/*
    UTILITY CLASS WHICH WILL BE USED IN THE CONTROLLER CLASSES 
    IN ORDER TO INSERT THE DOCUMENT DATA

 */

@Controller
public class UtilityForController {
    public static int SIZE_IN_KB = 490;// Maximum file size in bytes is 500 KB
    private static int MAX_FILE_SIZE = (SIZE_IN_KB + 10) * 1024;
    public ObjectMapper objectMapper = new ObjectMapper();
    /*
     * "The ( SIZE_IN_KB + 10 ) WHen A user Inserts picture or document which is
     * greater than 490 KB
     * He/She Will Think They have Cracked the System
     * ( Let them Think In that Way as it will make him happy ) NO LOSS "
     */

    @Autowired
    private EducationDetailsServiceImpl eduService;
    @Autowired
    private ExtraDocs_NotesServiceImpl xNotesService;
    @Autowired
    private LoginDetailsServiceImpl loginService;
    @Autowired
    private SocialProfilesServiceImpl sProfilesService;

    public UtilityForController(EducationDetailsServiceImpl eduService, ExtraDocs_NotesServiceImpl xNotesService,
            LoginDetailsServiceImpl loginService, SocialProfilesServiceImpl sProfilesService) {
        this.eduService = eduService;
        this.xNotesService = xNotesService;
        this.loginService = loginService;
        this.sProfilesService = sProfilesService;
    }

    /* To Set The User In other Entites */
    public UserDetails getuserData(Integer id) {
        UserDetails userData = null;
        try {
            userData = loginService.getuserData(id);
        } catch (Exception e) {
            System.out.println("UserController Utilities : getuserData " + e);
        }
        return userData;
    }

    public void removeData(Integer userId) {
        try {
            System.out.println(
                    loginService.deleteByuserid(userId) + "\n" +
                    // Add here the deleteAllByuserid Classes
                            eduService.deleteAllByuserid(userId) + "\n" +
                            xNotesService.deleteAllByuserid(userId) + "\n" +
                            sProfilesService.deleteAllByuserid(userId) + "\n");
        } catch (Exception e) {
            System.out.println("The Exceptio Ocured In user utily Class : " + e);
        }
        return;
    }

    public UserDetails insertUserDetails(String userDataString, MultipartFile profilePic) throws Exception {
        handleFileUpload(profilePic);// Check The File Size is in the limits
        UserDetails userObject = new UserDetails();
        UserDetails userData = objectMapper.readValue(userDataString, UserDetails.class);

        userObject.setFirstName(userData.getFirstName());
        userObject.setLastName(userData.getLastName());
        userObject.setEmail(userData.getEmail());
        userObject.setDateOfBirth(userData.getDateOfBirth());
        userObject.setProfilePicture(profilePic.getBytes());

        return userObject;
    }

    public UserDetails updateUserDetails(UserDetails existingUser, UserDetails updatedUser,
            MultipartFile profilePic) throws Exception {

        handleFileUpload(profilePic);// Check The File Size is in the limits
        String message = "";

        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
            message += " added FirstName";
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
            message += " added LasName";
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
            message += " added Email";
        }
        if (updatedUser.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
            message += " added DOB";
        }
        if (profilePic.getSize() > 0) {
            existingUser.setProfilePicture(profilePic.getBytes());
            message += " added ProfilePIc";
        }
        System.out.println("\n\n\n Updated the Followin In UserDetails \n" + message);

        return existingUser;

    }

    public ExtraDocs_Notes insertExtraDocs_Notes(Integer userID,String xDocs_NotesString, MultipartFile picture) throws Exception {
        handleFileUpload(picture);// Check The File Size is in the limits
        ExtraDocs_Notes xDocs_NotesObject = new ExtraDocs_Notes();
        ExtraDocs_Notes xDocs_Notes = objectMapper.readValue(xDocs_NotesString, ExtraDocs_Notes.class);
         ExtraDocs_Notes existingDocs_Notes = xNotesService.getRecordByTitleAndUserId(xDocs_Notes.getTitle(), userID);
        System.out.println("Notes and Docs :" + existingDocs_Notes);

        if (existingDocs_Notes != null) {
            throw new NullPointerException();
        }

        xDocs_NotesObject.setTitle(xDocs_Notes.getTitle());
        xDocs_NotesObject.setDescription(xDocs_Notes.getDescription());
        xDocs_NotesObject.setPicture(picture.getBytes());
        xDocs_NotesObject.setUser(xDocs_Notes.getUser());

        return xDocs_NotesObject;
    }

    public ExtraDocs_Notes updateExtraDocs_Notes(ExtraDocs_Notes existingDocs_Notes, ExtraDocs_Notes updatedDocs_Notes,
            MultipartFile picture) throws Exception {
        handleFileUpload(picture);// Check The File Size is in the limits
        String message = "";

        if (updatedDocs_Notes.getTitle() != null) {
            existingDocs_Notes.setTitle(updatedDocs_Notes.getTitle());
            message += " added Title";
        }
        if (updatedDocs_Notes.getDescription() != null) {
            existingDocs_Notes.setDescription(updatedDocs_Notes.getDescription());
            message += " added Description";
        }
        if (picture.getSize() > 0) {
            existingDocs_Notes.setPicture(picture.getBytes());
            message += " added Document/Picture";
        }

        System.out.println("\n\n\n Updated the Following Details In ExtraDocsAndNotes \n" + message);

        return existingDocs_Notes;

    }

    public EducationDetails insertEducationDetails(Integer userID,String eduDetailsString, MultipartFile certificate)
            throws Exception {
        handleFileUpload(certificate); // Check The File Size is in the limits
        EducationDetails eduObject = new EducationDetails();
        EducationDetails eduDetails = objectMapper.readValue(eduDetailsString, EducationDetails.class);
        System.out.println(eduDetails.getGraduationYear());
        EducationDetails existingEduDetails = eduService.getRecordByeduDetailsAndUserId(eduDetails.getEduQualification(),userID);
        //System.out.println("Education Details : " + existingEduDetails);

        if (existingEduDetails != null) {
            // Throwing this error as we should not add  multiple records of EduQualification for a same user 
            throw new  NullPointerException();
        }
        eduObject.setEduQualification(eduDetails.getEduQualification());
        eduObject.setSchoolInstitution(eduDetails.getSchoolInstitution());
        eduObject.setGpaOrGrades(eduDetails.getGpaOrGrades());
        eduObject.setUser(eduDetails.getUser());
        eduObject.setGraduationYear(eduDetails.getGraduationYear());
        eduObject.setCertificates(certificate.getBytes());

        return eduObject;
    }

    public EducationDetails updateEducationDetails(EducationDetails existingEducationDetails,
            EducationDetails updatedEducationDetails, MultipartFile certificate) throws Exception {
        handleFileUpload(certificate); // Check The File Size is in the limits
        String message = "";
        System.out.println(updatedEducationDetails);

        if (updatedEducationDetails.getEduQualification() != null) {
            existingEducationDetails.setEduQualification(updatedEducationDetails.getEduQualification());
            message += " added Edu Qualification";
        }
        if (updatedEducationDetails.getSchoolInstitution() != null) {
            existingEducationDetails.setSchoolInstitution(updatedEducationDetails.getSchoolInstitution());
            message += " added School/Instution ";
        }
        if (updatedEducationDetails.getGpaOrGrades() != null) {
            existingEducationDetails.setGpaOrGrades(updatedEducationDetails.getGpaOrGrades());
            message += " added Gpa/Grades ";
        }
        if (updatedEducationDetails.getGraduationYear() != null) {
            existingEducationDetails.setGraduationYear(updatedEducationDetails.getGraduationYear());
            message += " added Graudation Year";
        }
        if (certificate.getSize() > 0) {
            existingEducationDetails.setCertificates(certificate.getBytes());
        }

        System.out.println("\n\n\n Updated the Following Details In Education Details \n" + message);
        return existingEducationDetails;
    }

    private void handleFileUpload(MultipartFile file) {
        /*
         * Check the file size here
         * It is static Because the data we upload pics and documents in ExtraDocuments
         * also and also in EducationDetails
         */
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new MaxFileSizeExceededException(
                    "Maximum file size exceeded \nUpload file Less Than  " + (SIZE_IN_KB) + "KB");

        }
    }

}
