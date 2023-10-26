package com.pds_mark1.personal_data_manager_v1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pds_mark1.personal_data_manager_v1.entities.LoginDetails;

import jakarta.transaction.Transactional;



@Repository
@Transactional
public interface LoginDetailsRepository extends JpaRepository<LoginDetails,Integer> {
    
    void deleteByUser_userID(Integer userID);
    LoginDetails findByUser_userID(Integer userID);
    String findPasswordByUsername(String username);
    LoginDetails findByUsername(String username);
       
    
}
