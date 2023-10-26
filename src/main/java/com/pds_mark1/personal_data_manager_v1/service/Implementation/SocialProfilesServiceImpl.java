package com.pds_mark1.personal_data_manager_v1.service.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pds_mark1.personal_data_manager_v1.entities.SocialProfiles;
import com.pds_mark1.personal_data_manager_v1.repo.SocialProfilesRepository;
import com.pds_mark1.personal_data_manager_v1.service.SocialProfilesService;

@Service
public class SocialProfilesServiceImpl implements SocialProfilesService {
    @Autowired
    SocialProfilesRepository sProfileRepoObject;
    @Autowired
    UserData userObject;

    public SocialProfilesServiceImpl(SocialProfilesRepository sProfileRepoObject) {
        this.sProfileRepoObject = sProfileRepoObject;
    }

    @Override
    public SocialProfiles getSProfile(Integer id) {
        return sProfileRepoObject.findById(id).get();
    }
    
    @Override
    public String getAllSProfileNames(Integer userid) {
        List<String> sProfileNames = sProfileRepoObject.findAllsocialPlatformNameByUser_userID(userid);
        return sProfileNames.stream().collect(Collectors.joining(","));
    }

    public SocialProfiles getRecordByPlatformAnduserId(Integer userId,String platformName){
        SocialProfiles obj = sProfileRepoObject.findBysocialPlatformNameAndUser_userID(userId,platformName);
        if (obj==null){
            return null;
        }
        return obj;
    }

    @Override
    public String insertSProfile(SocialProfiles SProfileObject) {
        sProfileRepoObject.save(SProfileObject);

        return SProfileObject.getSocialPlatformName() + " is Added Successfully";
    }

    @Override
    public String updateSProfiler(SocialProfiles SProfileObject) {
        sProfileRepoObject.save(SProfileObject);

        return SProfileObject.getSocialPlatformName() + " was Updated Successfully";
    }

    @Override
    public String deleteSProfile(Integer Id) {
        SocialProfiles objProfiles = sProfileRepoObject.findById(Id).get();
        sProfileRepoObject.deleteById(Id);
        return objProfiles.getSocialPlatformName() + " was Removed Sucessfully";
    }

    @Override
    public String deleteAllByuserid(Integer userId) {
        String listOfSocailprofiles = getAllSProfileNames(userId);

        if (listOfSocailprofiles.isEmpty()) {
            return "\n"+"No Entries Found in Social Profiles";
        }
        sProfileRepoObject.deleteAllByUser_userID(userId);
        return "\n"+listOfSocailprofiles+" Accounts Were Removed Successfully \n" ;

    }

    @Override
    public List<SocialProfiles> getAllSocialProfilesByuserId(Integer userId) {
        List<SocialProfiles> listOfSProfiles = sProfileRepoObject.findAllByUser_userID(userId);
        if(listOfSProfiles.isEmpty()){
            return null;
        }
        return listOfSProfiles;
        
    }
    

   /*  @Override
    public Integer getIdByplatformName(String platformName) {
        Integer platformId = sProfileRepoObject.findIdBysocialPlatformName(platformName);
        return platformId;
    } */

}
