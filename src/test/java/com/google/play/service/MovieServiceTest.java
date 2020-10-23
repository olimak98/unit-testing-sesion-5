package com.google.play.service;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.play.collaborator.ICentralRisk;
import com.google.play.dao.IBillDAO;
import com.google.play.entity.Bill;
import com.google.play.entity.BillBuilder;
import com.google.play.entity.Movie;
import com.google.play.entity.MovieBuilder;
import com.google.play.entity.User;
import com.google.play.entity.UserBuilder;

import static com.google.play.entity.BillBuilder.getBill;
import static com.google.play.entity.MovieBuilder.getMovie;
import static com.google.play.entity.UserBuilder.getUser;
import static org.hamcrest.CoreMatchers.*;

public class MovieServiceTest {


	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	public int 			 price  		 = 0;
	public Integer 		 price2 		 = 0;
	
	@Mock
	private ICentralRisk centralRisk;
	@Mock	
	private IBillDAO   	 billDAO;
	@Mock
	private IUserService userService;
	
	@InjectMocks
	public MovieService  movieService; 
	
	@Mock
	public IMailService  mailService;
	
	@Mock
	public IBillService  billService;
	
	private List<Movie>  movies	;
	

	@Before
	public void setup() {
		movies 				= new ArrayList<Movie>();
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void shouldRentMovie() throws Exception {
		//Arrange 
		double 	expected 	= 10.0;
		User 	user 		= getUser().now();
		Movie	movie1Interestellar = MovieBuilder.getMovie().now();
		Bill	bill;
		
		movies.add(movie1Interestellar);
		
		//Act
		bill = movieService.rentMovie(user, movies);
		
		//Assert
		ArgumentCaptor<Bill> captor = ArgumentCaptor.forClass(Bill.class);
		
		verify(billDAO).save(captor.capture());
		
		Bill billCapture = captor.getValue();
		
		Assert.assertEquals(expected, bill.getNetPrice(),0.1);
		
	}
	
	@Test
	public void shouldDiscount20PercentBy2Movies() throws Exception {
		//Arrange 
		double 	expected 			= 16.0;
		User 	user 				= getUser().now();
		Movie	movie1Interestellar = MovieBuilder.getMovie().now();
		Movie	movie2Insepction 	= MovieBuilder.getMovie().now();
		Bill	bill;
		
		movies.add(movie1Interestellar);
		movies.add(movie2Insepction);
		
		//Act
		bill = movieService.rentMovie(user, movies);
		
		//Assert
		Assert.assertEquals(expected, bill.getNetPrice(),0.1);
		
	}
	
	@Test
	public void shouldDiscount30PercentBy3Movies() throws Exception {
		//Arrange 
		double 	expected 				= 21.0;
		User 	user 					= getUser().now();
		Movie	movie1Interestellar 	= MovieBuilder.getMovie().now();
		Movie	movie2Insepction 		= MovieBuilder.getMovie().now();
		Movie	movie3TheWalkingDead 	= MovieBuilder.getMovie().now();
		Bill	bill;
		
		movies.add(movie1Interestellar);
		movies.add(movie2Insepction);
		movies.add(movie3TheWalkingDead);
		
		//Act
		bill = movieService.rentMovie(user, movies);
		
		//Assert
		Assert.assertEquals(expected, bill.getNetPrice(),0.1);
		
	}	
	
	
	@Test
	public void shouldDiscount50PercentMore3Movies() throws Exception {
		//Arrange 
		double 	expected 				= 20.0;
		User 	user 					= getUser().now();
		Movie	movie1Interestellar 	= getMovie().now();
		Movie	movie2Insepction 		= MovieBuilder.getMovie().now();
		Movie	movie3TheWalkingDead 	= MovieBuilder.getMovie().now();
		Movie	movie4TheWitcher 		= MovieBuilder.getMovie().now();
		Bill	bill;
		
		movies.add(movie1Interestellar);
		movies.add(movie2Insepction);
		movies.add(movie3TheWalkingDead);
		movies.add(movie4TheWitcher);
		
		//Act
		bill = movieService.rentMovie(user, movies);
		
		//Assert
		Assert.assertEquals(expected, bill.getNetPrice(),0.1);
		
	}
	
	
	@Test
	public void shouldRentMovieWeekend() {
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		
		DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
		
		System.out.println(df.format(calendar.getTime()));
		
		boolean isWeekend = df.format(calendar.getTime()).contains("sáb");
		
		Assume.assumeFalse(isWeekend);
		
		//Arrange
		 	
		//Act
		
		//Assert
		Assert.assertEquals(price, price2.intValue());
		
				
	}

	
	@Test(expected = Exception.class)
	public void noShouldRentMovieNoStock() throws Exception  {
		//Arrange
		User 	user 	= getUser().now();
		Movie  	movie	= getMovie().noStock().now();
		movies.add(movie);
		
		//Act
		movieService.rentMovie(user, movies);

	}
	
	@Test
	public void noSouldRentMovieHighRiskCutomer() throws Exception {
		//Arrange
		User user = getUser().highRisk().now();
		User userNoRisk 	= getUser().now();
		Bill bill;
		when(centralRisk.getRiskByCustomer(user.getTypeDocument(), user.getNumDocument()))
		.thenReturn(true);
		
		exception.expect(Exception.class);
		exception.expectMessage("High Risk Customer");
		//Act
		bill = movieService.rentMovie(user, movies);
		
	}
	
	
	@Test
	public void shouldNotifyNewMovies() {
		//Arrange
		List<User> users = new ArrayList<User>();
		
		User user 		 = getUser().now();
		User user2 		 = getUser().otherName("Johan").now();
		User user3		 = getUser().otherName("Vargas").highRisk().now();
		
		users.add(user);
		users.add(user2);
		//users.add(user3);
		//users.add(user);
		
		List<Movie> movies = new ArrayList<Movie>();
		movies.add(getMovie().now());
		movies.add(getMovie().now());
		movies.add(getMovie().now());
		
		when(userService.getAll()).thenReturn(users);
		
		//Act
		movieService.notifyNewMovies();
		
		//Assert
		verify(mailService).sendEmail(user,movies);
		//Mockito.verifyNoMoreInteractions(mailService);
		verify(mailService).sendEmail(user2,movies);
		
		
		verify(mailService,never()).sendEmail(user3,movies);
		//verify(mailService,Mockito.times(2)).sendEmail(user,movies);
		//verify(mailService,Mockito.atLeast(2)).sendEmail(user,movies);
		verify(mailService,Mockito.atMost(1)).sendEmail(user,movies);
		verify(mailService,Mockito.atLeastOnce()).sendEmail(user,movies);
		//verify(mailService,Mockito.atLeast(2)).sendEmail(Mockito.any(User.class),movies);
		
	}

	
	@Test
	public void noShouldRentMovieHighRiskCustomer2() throws Exception {
		//Arrange
		User user = getUser().highRisk().now();
		User userNoRisk 	= getUser().now();
		Bill bill;
		when(centralRisk.getRiskByCustomer(user.getTypeDocument(), user.getNumDocument()))
		//.thenReturn(true)
		.thenThrow(new Exception("Error"));
		
		exception.expect(Exception.class);
		exception.expectMessage("Error");
		//Act
		bill = movieService.rentMovie(user, movies);
		
	}
	
	@Test
	public void shouldRefoundMoney() {
		//Arrange
		Bill bill 			= getBill().now();
		Bill billRefound 	= getBill().otherNumBill(10059444).now();
		
		//Act
		movieService.refundMoney(bill);
		//Assert
		ArgumentCaptor<Bill> captor = ArgumentCaptor.forClass(Bill.class);
		
		verify(billService).refundMoney(captor.capture());
		
		Bill billRefoundCapture = captor.getValue();
		
		Assert.assertEquals(bill.getNetPrice(), billRefoundCapture.getNetPrice(),0.1);
		
		
		
		
	
	}
	

}
