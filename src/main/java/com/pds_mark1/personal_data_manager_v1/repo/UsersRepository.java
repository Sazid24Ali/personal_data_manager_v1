package com.pds_mark1.personal_data_manager_v1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pds_mark1.personal_data_manager_v1.entities.UserDetails;
import java.util.List;



@Repository
public interface UsersRepository extends JpaRepository<UserDetails,Integer>{

    List<UserDetails> findByEmail(String email);
    
    
}
