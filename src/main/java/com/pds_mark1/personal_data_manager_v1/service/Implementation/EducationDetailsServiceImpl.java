package com.pds_mark1.personal_data_manager_v1.service.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pds_mark1.personal_data_manager_v1.entities.EducationDetails;
import com.pds_mark1.personal_data_manager_v1.repo.EducationDetailsRepository;
import com.pds_mark1.personal_data_manager_v1.service.EducationDetailsService;


@Service
public class EducationDetailsServiceImpl implements EducationDetailsService {
    @Autowired
    EducationDetailsRepository eDetailsRepository;

    public EducationDetailsServiceImpl(EducationDetailsRepository eDetailsRepository) {
        this.eDetailsRepository = eDetailsRepository;
    }

    @Override
    public EducationDetails getEducDetails(Integer id) {
        return eDetailsRepository.findById(id).get();

    }

      @Override
    public List<EducationDetails> getAllEducDetailsByuserid(Integer userid) {
        List<EducationDetails> listOdEdu = eDetailsRepository.findAllByUser_userID(userid);
        if(listOdEdu.isEmpty()){
            return null;
        }
        return listOdEdu;
        
    }
    @Override
    public String insertEducDetails(EducationDetails EducDetailsObject) {
        eDetailsRepository.save(EducDetailsObject);

        return EducDetailsObject.getSchoolInstitution() + " Details were inserted Successfully";
    }

    @Override
    public String updateEducDetails(EducationDetails EducDetailsObject) {
        eDetailsRepository.save(EducDetailsObject);

        return EducDetailsObject.getSchoolInstitution() + " Details were Updated Successfully";
    }

    public EducationDetails getRecordByeduDetailsAndUserId(String eduQual,Integer userID){
        return eDetailsRepository.findByeduQualificationAndUser_userID(eduQual, userID);
    }

    @Override
    public String deleteEducDetails(Integer Id) {
        EducationDetails eDetailsObject = getEducDetails(Id);
        eDetailsRepository.deleteById(Id);

        return eDetailsObject.getSchoolInstitution() + " Details Were Removed ";
    }

    @Override
    public String deleteAllByuserid(Integer userId) {
        String listofeduDetails = eDetailsRepository.findAlleduQualificationByUser_userID(userId).stream().collect(Collectors.joining(","));
        if(listofeduDetails.isEmpty()){
            return "No Entries Found in Education Details ";
        }
        eDetailsRepository.deleteAllByUser_userID(userId);
        return "\n"+listofeduDetails+" Education Details Were Removed Successfully \n";   
    }

  
    public void deleteRecordByeduQualAndUserId(String eduQual,Integer userID){
        EducationDetails obj = getRecordByeduDetailsAndUserId(eduQual, userID);
        if(obj == null){
            throw new NullPointerException();
        }
        eDetailsRepository.deleteByeduQualificationAndUser_userID(eduQual, userID);
    }
}