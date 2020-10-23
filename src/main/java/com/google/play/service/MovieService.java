package com.google.play.service;

import java.util.ArrayList;
import java.util.List;

import com.google.play.collaborator.ICentralRisk;
import com.google.play.dao.IBillDAO;
import com.google.play.entity.Bill;
import com.google.play.entity.Movie;
import com.google.play.entity.User;

public class MovieService {

	private IBillDAO billDAO;
	
	private ICentralRisk centralRisk;
	
	private IUserService userService;
	
	private IMailService mailService;
	
	private IBillService billService;
	
	
	public Bill rentMovie(User user,List<Movie> movies) throws Exception {

		for (Movie movie : movies) {
			if(movie.getStock()	<=	0) {
				throw new Exception("No movie");
			}
		}
		
		if(centralRisk.getRiskByCustomer(user.getTypeDocument(), user.getNumDocument())) {
			throw new Exception("High Risk Customer");
		}

		double discount 	= 0.0;
		double netPrice		= 0.0;
		double grossPrice	= 0.0;

		Bill 	bill = new Bill();

		for (int numMovies = 0; numMovies < movies.size(); numMovies++) {
			grossPrice	+= movies.get(numMovies).getNetPrice();
		}

		switch (movies.size()) {
		case 1:
			discount = 0;
			break;
		
		case 2:
			discount = grossPrice*0.20;
			break;

		case 3:
			discount = grossPrice*0.30;
			break;

		default:
			break;
		}

		if(movies.size()>3) {
			discount = grossPrice*0.50;
		}

		netPrice	= grossPrice - discount;

		bill.setNetPrice(netPrice);

		
		billDAO.save(bill);
		
		return bill;

	}
	
	
	public void notifyNewMovies() {
		List<User> users 	= userService.getAll();
		List<Movie> movies 	= getNewMovies(); 
		
		for (User user : users) {
			
			mailService.sendEmail(user,movies);
		}
	}
	
	
	
	private List<Movie> getNewMovies() {
		
		List<Movie> movies = new ArrayList<Movie>();
		
		for (int numMovie = 0; numMovie < 3; numMovie++) {
			Movie movie = new Movie();
			movie.setName("Interestellar");
			movie.setNetPrice(10.0);
			movie.setStock(10);
			
			movies.add(movie);
		}
		return movies;
	}
	
	
	public void refundMoney(Bill bill) {
		
		Bill billRefund = new Bill();
		billRefund.setNetPrice(bill.getNetPrice());
		billRefund.setNumMovies(bill.getNumMovies());
		billRefund.setNumBill(10059444);
		
		billService.refundMoney(billRefund);
		
		
		
	}

}
