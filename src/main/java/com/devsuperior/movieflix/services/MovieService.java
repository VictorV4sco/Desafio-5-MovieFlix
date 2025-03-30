package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findMoviesByGenre(Pageable pageable, Long id) {
		Pageable sortedPageable = PageRequest.of(
	            pageable.getPageNumber(),
	            pageable.getPageSize(),
	            Sort.by("title").ascending()
	        );
		
		Page<Movie> page = repository.searchByGenre(id, sortedPageable);
		return page.map(x -> new MovieCardDTO(x.getId(), x.getTitle(), x.getSubTitle(), x.getYear(), x.getImgUrl()));
	}
	
	@Transactional(readOnly = true)
	public MovieDetailsDTO findMovieById(Long id) {
		Movie movie = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		return new MovieDetailsDTO(movie.getId(), movie.getTitle(), movie.getSubTitle(), movie.getYear(), movie.getImgUrl(), movie.getSynopsis(),
				new GenreDTO(movie.getGenre().getId(), movie.getGenre().getName())
				);
	}
	
//	@Transactional(readOnly = true)
//    public MovieDetailsDTO findMovieById(Long id) {
//        return repository.findMovieDetailsById(id);
//    }
	
	@Transactional
	public List<ReviewDTO> findByMovieId(Long movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return reviews.stream().map(review -> new ReviewDTO(
            review.getId(),
            review.getText(),
            review.getMovie().getId(),
            review.getUser().getId(),
            review.getUser().getName(),
            review.getUser().getEmail()
        )).collect(Collectors.toList());
    }
	
}
