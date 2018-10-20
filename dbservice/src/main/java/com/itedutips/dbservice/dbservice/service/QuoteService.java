package com.itedutips.dbservice.dbservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itedutips.dbservice.dbservice.model.Quote;
import com.itedutips.dbservice.dbservice.repository.QuoteRepository;


@Service
public class QuoteService {
	
	@Autowired
	private QuoteRepository quoteRepository;
	public List<Quote> getAllQuotes()
	{
		List<Quote> quotes= new ArrayList<>();
		quoteRepository.findAll()
					   .forEach(quotes::add);
		return quotes;
	}
	
	public void  addQuote(Quote quote)
	{
		System.out.println("Add quote................");
		quoteRepository.save(quote);
	}
	
	public Quote getQuote(Integer id)
	{
		return quoteRepository.findOne(id);
	}
	
	public void updateQuote(Integer id, Quote quote)
	{
		quoteRepository.save(quote);
	}
	public void deleteQuote(Integer id)
	{
		quoteRepository.delete(id); 
	}

}
