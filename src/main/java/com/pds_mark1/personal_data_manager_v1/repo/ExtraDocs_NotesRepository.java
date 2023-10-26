package com.pds_mark1.personal_data_manager_v1.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pds_mark1.personal_data_manager_v1.entities.ExtraDocs_Notes;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ExtraDocs_NotesRepository extends JpaRepository<ExtraDocs_Notes,Integer>{
    void deleteAllByUser_userID(Integer userID);
    List<ExtraDocs_Notes> findAllByUser_userID(Integer userID);
    @Query("SELECT xND.title FROM ExtraDocs_Notes xND WHERE xND.user.id = :userId")
    List<String> findAllTitleByUser_userID(@Param("userId")Integer userId);
    void deleteByTitleAndUser_userID(String title, Integer userId);
    ExtraDocs_Notes findByTitleAndUser_userID(String title,Integer userId);

}
