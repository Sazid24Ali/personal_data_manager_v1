package com.pds_mark1.personal_data_manager_v1.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pds_mark1.personal_data_manager_v1.entities.UserDetails;
import com.pds_mark1.personal_data_manager_v1.repo.UsersRepository;


/*
 * This Class Is defined To get UsedData ONly in ither entites 
 * In order maintain the foreign Key Releation 
 */
@Service
public class UserData {
    @Autowired
    UsersRepository userObject;
    
    public UserData(UsersRepository userObject) {
        this.userObject = userObject;
    }
    public UserDetails getuserData(Integer id){
        UserDetails data = userObject.findById(id).get();
        return data;
    }
    
}
