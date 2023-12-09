package com.pds_mark1.personal_data_manager_v1.service;



import com.pds_mark1.personal_data_manager_v1.entities.UserDetails;

public interface UsersService {
    public UserDetails getUser(Integer id);
    public int insertUser (UserDetails usersObject);
    public boolean updateUser (UserDetails usersObject);
    public boolean deleteUser (Integer Id);
    public UserDetails getUserByEmail(String email);

    
}
