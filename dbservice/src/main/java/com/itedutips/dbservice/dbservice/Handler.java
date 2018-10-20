package com.itedutips.dbservice.dbservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itedutips.dbservice.dbservice.model.Quote;
import com.itedutips.dbservice.dbservice.service.QuoteService;

@RestController
public class Handler {

	@Autowired
	private QuoteService quoteService;
	
	@RequestMapping("/quotes")
	public List<Quote> getAllQuotes()
	{
		return quoteService.getAllQuotes();
	}
	
	@RequestMapping("/quotes/{id}")
	public Quote getQuote(@PathVariable Integer id)
	{
		return quoteService.getQuote(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/quotes")
	public void addQuote(@RequestBody Quote quote)
	{
		quoteService.addQuote(quote);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/quotes/{id}")
	public void updateQuote(@RequestBody Quote quote,@PathVariable Integer id)
	{
		quoteService.updateQuote(id,quote);
	}
	
	
	@RequestMapping(method=RequestMethod.DELETE, value="/quotes/{id}")
	public void deleteQuote(@PathVariable Integer id)
	{
		quoteService.deleteQuote(id);
	}
	
}
