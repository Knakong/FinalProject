package com.skilldistillery.paseo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.paseo.entities.Walk;

public interface WalkRepository extends JpaRepository<Walk, Integer>{

	
	List<Walk> findByUser_IdAndEnabled(int id, boolean enabled);
	
	Walk findByUserId(int userId);
	
	Walk findById(int id);
	

	
}
