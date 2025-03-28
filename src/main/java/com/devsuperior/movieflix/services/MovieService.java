package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;
	
	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findMoviesByGenre(Pageable pageable, Long id) {
		Page<Movie> page = repository.searchByGenre(id, pageable);
		return page.map(x -> new MovieCardDTO(x.getId(), x.getTitle(), x.getSubTitle(), x.getYear(), x.getImgUrl()));
	}
	
	@Transactional(readOnly = true)
	public MovieDetailsDTO findMovieById(Long id) {
		Movie movie = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		return new MovieDetailsDTO(movie.getId(), movie.getTitle(), movie.getSubTitle(), movie.getYear(), movie.getImgUrl(), movie.getSynopsis(), null);
	}
}
