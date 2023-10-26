package com.pds_mark1.personal_data_manager_v1.service;

import java.util.List;

import com.pds_mark1.personal_data_manager_v1.entities.EducationDetails;

public interface EducationDetailsService {
    public EducationDetails getEducDetails(Integer id);
    public List<EducationDetails> getAllEducDetailsByuserid(Integer id);
    public String insertEducDetails(EducationDetails EducDetailsObject);
    public String updateEducDetails(EducationDetails EducDetailsObject);
    public String deleteEducDetails(Integer Id);
    public String deleteAllByuserid(Integer userId);

}
