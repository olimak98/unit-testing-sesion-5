package com.google.play.entity;

public class MovieBuilder {

	private Movie movie;
	
	private MovieBuilder() {}
	
	
	public static MovieBuilder getMovie() {
		MovieBuilder builder = new MovieBuilder();
		builder.movie = new Movie();
		builder.movie.setName("Interestellar");
		builder.movie.setNetPrice(10.0);
		builder.movie.setStock(10);
		
		return builder;
		
	}
	
	
	public static MovieBuilder getMovieOtherName() {
		MovieBuilder builder = new MovieBuilder();
		builder.movie = new Movie();
		builder.movie.setName("Interestellar");
		builder.movie.setNetPrice(10.0);
		builder.movie.setStock(10);
		
		return builder;
		
	}
	
	
	public MovieBuilder noStock() {
		movie.setStock(0);
		return this;
	}
	
	
	public Movie now() {
		return movie;
	}
	
	
	
}
