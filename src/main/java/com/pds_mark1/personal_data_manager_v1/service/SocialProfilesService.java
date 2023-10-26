package com.pds_mark1.personal_data_manager_v1.service;

import java.util.List;

import com.pds_mark1.personal_data_manager_v1.entities.SocialProfiles;

public interface SocialProfilesService {
    public SocialProfiles getSProfile(Integer id);
    public List<SocialProfiles> getAllSocialProfilesByuserId(Integer userId);
    public String getAllSProfileNames(Integer userid);
    public String insertSProfile (SocialProfiles SProfileObject);
    public String updateSProfiler (SocialProfiles SProfileObject);
    public String deleteSProfile (Integer Id);
    public String deleteAllByuserid(Integer userId);
    //public Integer getIdByplatformName(String platformName);//Not needed Extactly as Id Is Send From Angular 
}
