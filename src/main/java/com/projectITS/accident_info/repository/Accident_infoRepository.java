package com.projectITS.accident_info.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectITS.accident_info.entity.Accident_info;

public interface Accident_infoRepository extends JpaRepository<Accident_info, Long> {
    
}
