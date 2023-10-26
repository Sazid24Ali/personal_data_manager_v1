package com.pds_mark1.personal_data_manager_v1.service.Implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pds_mark1.personal_data_manager_v1.entities.UserDetails;
import com.pds_mark1.personal_data_manager_v1.repo.UsersRepository;
import com.pds_mark1.personal_data_manager_v1.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository usersRepoObject;

    public UsersServiceImpl(UsersRepository usersRepoObject) {
        this.usersRepoObject = usersRepoObject;
    }

    @Override
    public UserDetails getUser(Integer id) {
        Optional<UserDetails> objUsers = usersRepoObject.findById(id);
        return objUsers.get();
    }

    @Override
    public boolean insertUser(UserDetails usersObject) {
        
        try {
            usersRepoObject.save(usersObject);
            System.out.println("User Added Succesfully \nUser Id : " + usersObject.getUserID() + "\nName : "
                + usersObject.getLastName());
        return true;
            
        } catch (Exception e) {
            System.out.println("USerServiceImpl Error Raised : "+e);
        }
        return false;
        
    }

    @Override
    public boolean updateUser(UserDetails usersObject) {
        
        usersRepoObject.save(usersObject);
        System.out.println("User\'s Updated Succesfully \nUser Id : " + usersObject.getUserID() + "\nName : "
                + usersObject.getLastName());
        return true;

    }

    @Override
    public boolean deleteUser(Integer Id) {
        try {
            UserDetails usersObject = getUser(Id);
            usersRepoObject.deleteById(Id);
            System.out.println(
                    usersObject.getLastName() + " was remove successfully \nUser Id : " + usersObject.getUserID());
            return true;

        } catch (Exception e) {
            System.out.println("\n\nException  : " + e);
        }

        return false;
    }


    @Override
    public UserDetails getUserByEmail(String email) {
        try {
            return usersRepoObject.findByEmail(email).get(0);   
        } catch (Exception e) {
            return null;
        }
    }

}
