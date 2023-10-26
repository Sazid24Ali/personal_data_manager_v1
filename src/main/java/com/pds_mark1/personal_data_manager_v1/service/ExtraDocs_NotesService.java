package com.pds_mark1.personal_data_manager_v1.service;

import java.util.List;

import com.pds_mark1.personal_data_manager_v1.entities.ExtraDocs_Notes;

public interface ExtraDocs_NotesService {
    public ExtraDocs_Notes getXDocs_Notes (Integer id);
    public List<ExtraDocs_Notes> getAllXDocs_NotesByuserid (Integer userID);
    public String insertXDocs_Notess (ExtraDocs_Notes XDocs_NotesObject);
    public String updateXDocs_Notes (ExtraDocs_Notes XDocs_NotesObject);
    public String deleteXDocs_Notes (Integer Id);
    public String deleteAllByuserid(Integer userId);
}
