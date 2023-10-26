package com.pds_mark1.personal_data_manager_v1.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pds_mark1.personal_data_manager_v1.entities.EducationDetails;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface EducationDetailsRepository extends JpaRepository<EducationDetails,Integer>{
    void deleteAllByUser_userID(Integer userID);
    List<EducationDetails> findAllByUser_userID(Integer userID);
    @Query("SELECT edu.eduQualification FROM EducationDetails edu WHERE edu.user.id = :userId")
    List<String> findAlleduQualificationByUser_userID(@Param("userId")Integer userId);
    EducationDetails findByeduQualificationAndUser_userID(String eduQual,Integer userID);
    void deleteByeduQualificationAndUser_userID(String eduQual,Integer userID);

}
