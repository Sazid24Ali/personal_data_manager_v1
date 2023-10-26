package com.pds_mark1.personal_data_manager_v1.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pds_mark1.personal_data_manager_v1.entities.SocialProfiles;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface SocialProfilesRepository extends JpaRepository<SocialProfiles,Integer>{
    void deleteAllByUser_userID(Integer userID);
    Integer findIdBysocialPlatformName(String platformName);
    @Query("SELECT sp.socialPlatformName FROM SocialProfiles sp WHERE sp.user.id = :userId")
    List<String> findAllsocialPlatformNameByUser_userID(@Param("userId")Integer userId);
    List<SocialProfiles> findAllByUser_userID(Integer userId);
    @Query("SELECT sp FROM SocialProfiles sp WHERE sp.user.id = :userId and sp.socialPlatformName = :platformName")
    SocialProfiles findBysocialPlatformNameAndUser_userID(@Param("userId")Integer userId,@Param("platformName")String platformName);
}
