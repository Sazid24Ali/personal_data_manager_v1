package com.pds_mark1.personal_data_manager_v1.service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pds_mark1.personal_data_manager_v1.entities.ExtraDocs_Notes;
import com.pds_mark1.personal_data_manager_v1.repo.ExtraDocs_NotesRepository;
import com.pds_mark1.personal_data_manager_v1.service.ExtraDocs_NotesService;

@Service
public class ExtraDocs_NotesServiceImpl implements ExtraDocs_NotesService {
    @Autowired
    ExtraDocs_NotesRepository eDocs_NotesRepositoryObject;


    public ExtraDocs_NotesServiceImpl(ExtraDocs_NotesRepository eDocs_NotesRepositoryObject) {
        this.eDocs_NotesRepositoryObject = eDocs_NotesRepositoryObject;
    }

    @Override
    public ExtraDocs_Notes getXDocs_Notes(Integer id) {
        // ExtraDocs_Notes eDocs_Notes_ = eDocs_NotesRepositoryObject.getById(id);
        Optional<ExtraDocs_Notes> eDocs_Notes = eDocs_NotesRepositoryObject.findById(id);

        return eDocs_Notes.get();
    }

     @Override
    public List<ExtraDocs_Notes> getAllXDocs_NotesByuserid(Integer userID) {
        List<ExtraDocs_Notes> listOfXdocs = eDocs_NotesRepositoryObject.findAllByUser_userID(userID);
        if (listOfXdocs.isEmpty()){
            return null;
        }  
        return listOfXdocs;
    }

    @Override
    public String insertXDocs_Notess(ExtraDocs_Notes XDocs_NotesObject) {
        eDocs_NotesRepositoryObject.save(XDocs_NotesObject);

        return XDocs_NotesObject.getTitle() + " is inserted Successfully";
    }

    @Override
    public String updateXDocs_Notes(ExtraDocs_Notes XDocs_NotesObject) {
        eDocs_NotesRepositoryObject.save(XDocs_NotesObject);

        return XDocs_NotesObject.getTitle() + " was Updated Successfully";
    }

    @Override
    public String deleteXDocs_Notes(Integer Id) {
        ExtraDocs_Notes eDocs_Notes = getXDocs_Notes(Id);
        eDocs_NotesRepositoryObject.deleteById(Id);

        return eDocs_Notes.getTitle() + " was Removed succesfully";
    }

    @Override
    public String deleteAllByuserid(Integer userId) {
        List<String> listofxNotesDocs = eDocs_NotesRepositoryObject.findAllTitleByUser_userID(userId);
        if(listofxNotesDocs.isEmpty()){
            return "No Entries Found in Extra Notes And Documents";
        }
        eDocs_NotesRepositoryObject.deleteAllByUser_userID(userId);
        return "\n"+listofxNotesDocs+" Notes And Documents Were Removed Successfully ";
        
    }
    
    public ExtraDocs_Notes getRecordByTitleAndUserId(String title, Integer userId){
        return eDocs_NotesRepositoryObject.findByTitleAndUser_userID(title, userId);
    }
    public void deleteRecordByTitleAndUserId(String title, Integer userId) {
        ExtraDocs_Notes obj = getRecordByTitleAndUserId(title, userId);
        if (obj == null){
            throw new NullPointerException();
            //To CheckThe Existence [ It will Raise An error Which Will be Caught in Controller Class] 
        }
        eDocs_NotesRepositoryObject.deleteByTitleAndUser_userID(title, userId);
    }
    
}
