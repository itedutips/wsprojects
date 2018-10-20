package com.itedutips.dbservice.dbservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itedutips.dbservice.dbservice.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {
	
	Quote findByUserName(String username);

}
