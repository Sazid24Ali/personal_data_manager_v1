package com.pds_mark1.personal_data_manager_v1.service.Implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pds_mark1.personal_data_manager_v1.entities.LoginDetails;
import com.pds_mark1.personal_data_manager_v1.entities.UserDetails;
import com.pds_mark1.personal_data_manager_v1.repo.LoginDetailsRepository;
import com.pds_mark1.personal_data_manager_v1.service.LoginDetailsService;

@Service
public class LoginDetailsServiceImpl implements LoginDetailsService {
    @Autowired
    LoginDetailsRepository lDetailsRepoObject;

    @Autowired
    UserData userObject; /* To Get The userData And SetIn the OtherEntites  */ 

    public LoginDetailsServiceImpl(LoginDetailsRepository lDetailsRepoObject) {
        this.lDetailsRepoObject = lDetailsRepoObject;
    }

    @Override
    public LoginDetails getLgDetails(Integer id) {
        Optional<LoginDetails> lDetailsObject = lDetailsRepoObject.findById(id);
        return lDetailsObject.get();
    }

    @Override
    public String insertLgDetails(LoginDetails LgDetailsObject) {
        lDetailsRepoObject.save(LgDetailsObject);

        return LgDetailsObject.getUsername() + " is Inserted Succesfully";
    }

    @Override
    public String updateLgDetails(LoginDetails LgDetailsObject) {
        lDetailsRepoObject.save(LgDetailsObject);

        return LgDetailsObject.getUsername() + " was Updated Succesfully";

    }

    @Override
    public String deleteByuserid(Integer userId) {
        try {
            LoginDetails lDetailsObject = getLoginDetailsByuserID(userId);
            lDetailsRepoObject.deleteByUser_userID(userId);

        return "\n"+lDetailsObject.getUsername() + " was Removed Sucessfully From Login Cred ";
            
        } catch (Exception e) {
            
        }
        return "\n"+"No Enteries Found in Login Cred";
    }

    @Override
    public LoginDetails getLoginDetailsByuserID(Integer userId) {
        try {
            LoginDetails lDetailsObject = lDetailsRepoObject.findByUser_userID(userId);
        return lDetailsObject;
        } catch (Exception e) {
            
        }
        return null;
    }
    public LoginDetails getLoginDetailsByusename(String username) {
        try {
            LoginDetails lDetailsObject = lDetailsRepoObject.findByUsername(username);
        return lDetailsObject;
        } catch (Exception e) {
            
        }
        return null;
    }
    
    public String getpasswordByusername(String username){
        return lDetailsRepoObject.findPasswordByUsername(username);
    }

    /* To Get User Data THis Function is Used In UserControllerUtilities  */
    public UserDetails getuserData(Integer id) {
        UserDetails userData = null;
        try {
            userData = userObject.getuserData(id);
        } catch (Exception e) {
            System.out.println("Login Service Impl" + e);
        }
        return userData;
    }

}
