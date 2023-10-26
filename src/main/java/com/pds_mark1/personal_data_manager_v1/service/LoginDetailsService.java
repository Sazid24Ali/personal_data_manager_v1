package com.pds_mark1.personal_data_manager_v1.service;

import com.pds_mark1.personal_data_manager_v1.entities.LoginDetails;

public interface LoginDetailsService {
    public LoginDetails getLgDetails(Integer id);
    public LoginDetails getLoginDetailsByuserID(Integer userId);
    public String insertLgDetails (LoginDetails LgDetailsObject);
    public String updateLgDetails (LoginDetails LgDetailsObject);
    public String deleteByuserid(Integer userId);
    
}
