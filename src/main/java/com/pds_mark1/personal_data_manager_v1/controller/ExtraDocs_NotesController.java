package com.pds_mark1.personal_data_manager_v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.pds_mark1.personal_data_manager_v1.entities.ExtraDocs_Notes;
import com.pds_mark1.personal_data_manager_v1.entities.UserDetails;
import com.pds_mark1.personal_data_manager_v1.exceptions_handler.MaxFileSizeExceededException;
import com.pds_mark1.personal_data_manager_v1.service.Implementation.ExtraDocs_NotesServiceImpl;

@RestController
@RequestMapping("/XNotesDocs")
@CrossOrigin
public class ExtraDocs_NotesController {
    @Autowired
    ExtraDocs_NotesServiceImpl ServiceObject;

    // To Get UserData ( Which Will Be Used In Insert And Update Methods To set the
    // Foreign Key )
    UtilityForController utilityObject;

    public ExtraDocs_NotesController(UtilityForController utilityObject) {
        this.utilityObject = utilityObject;
    }

    @GetMapping("/getAll/{userID}")
    public ResponseEntity<?> getAllXNotesDocs(@PathVariable Integer userID) {
        List<ExtraDocs_Notes> listOfNandDocs = ServiceObject.getAllXDocs_NotesByuserid(userID);
        if (listOfNandDocs != null) {
            System.out.println("Inside If ");
            return new ResponseEntity<>(listOfNandDocs, HttpStatus.OK);
        }
        return new ResponseEntity<>("No Doctuments Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/insert/{userID}")
    public ResponseEntity<?> insertXNotesDocs(@PathVariable Integer userID, @RequestParam String xDocs_Notes,
            @RequestParam("picture") MultipartFile picture) {
        UserDetails exisitingUserDetails = utilityObject.getuserData(userID);
        if (exisitingUserDetails == null) {
            return new ResponseEntity<>("User Not Found  \n Register First ", HttpStatus.NOT_FOUND);
        }

        try {
            ExtraDocs_Notes toInsert = utilityObject.insertExtraDocs_Notes(userID,xDocs_Notes, picture);
            toInsert.setUser(exisitingUserDetails); // Setting The USerData
            ServiceObject.insertXDocs_Notess(toInsert);
            return new ResponseEntity<>(toInsert.getTitle() + " was  Inserted Successfully ", HttpStatus.OK);
        } catch (MaxFileSizeExceededException e) {
            return new ResponseEntity<>("Maximum file size exceeded " + UtilityForController.SIZE_IN_KB + "KB",
                    HttpStatus.PAYLOAD_TOO_LARGE);
        }catch (NullPointerException e) {
            return new ResponseEntity<>("The Title Already Exists " + e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("The Given Tiltle alredy Exists",
                    HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update/{title}/{userID}")
    public ResponseEntity<?> updateXNotesDocs(@PathVariable String title, @PathVariable Integer userID,
            @RequestParam String xDocs_Notes, @RequestParam("picture") MultipartFile picture) {
                System.out.println("Title : "+title);
        UserDetails exisitingUserDetails = utilityObject.getuserData(userID);
        ExtraDocs_Notes existingDocs_Notes = ServiceObject.getRecordByTitleAndUserId(title, userID);
        System.out.println("Notes and Docs :" + existingDocs_Notes);

        if (existingDocs_Notes == null || exisitingUserDetails == null) {
            return new ResponseEntity<>("Data Not Found ( Check The Title and UserID ) ", HttpStatus.NOT_FOUND);
        }
        try {
            ExtraDocs_Notes toUpdate = utilityObject.objectMapper.readValue(xDocs_Notes, ExtraDocs_Notes.class);

            ExtraDocs_Notes updatedExtraDocs_Notes = utilityObject.updateExtraDocs_Notes(existingDocs_Notes,
                    toUpdate, picture);
            updatedExtraDocs_Notes.setUser(exisitingUserDetails);// Setting The User Details ( Safety )
            String updatedMessage = ServiceObject.updateXDocs_Notes(updatedExtraDocs_Notes);
            return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } catch (MaxFileSizeExceededException e) {
            return new ResponseEntity<>("Maximum file size exceeded " + UtilityForController.SIZE_IN_KB + "KB",
                    HttpStatus.PAYLOAD_TOO_LARGE);
        } catch (DataIntegrityViolationException e){
            return new ResponseEntity<>("TryIng To Change the Title Which Already Exist ", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Exception Occured " + e, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{title}/{userID}")
    public ResponseEntity<?> deleteXNotesDocs(@PathVariable String title, @PathVariable Integer userID) {
        try {
            ServiceObject.deleteRecordByTitleAndUserId(title, userID);
            return new ResponseEntity<>("Deleted The " + title + " Succesfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Data Not Found ( Check The Title and UserID ) " , HttpStatus.NOT_FOUND);
        }
    }

}