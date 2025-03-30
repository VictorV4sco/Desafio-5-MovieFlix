package com.devsuperior.movieflix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

	@Autowired
	private MovieService service;
	
	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_VISITOR')")
	@GetMapping
	public ResponseEntity<Page<MovieCardDTO>> getALLMoviesOrByGenre(Pageable pageable, @Valid @RequestParam(name = "genreId", required = false, defaultValue = "") Long genre_id) {
		return new ResponseEntity<>(service.findMoviesByGenre(pageable, genre_id), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_VISITOR')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieDetailsDTO> getMovieById(@Valid @PathVariable Long id) {
		return new ResponseEntity<>(service.findMovieById(id), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_VISITOR')")
	@GetMapping(value = "/{id}/reviews")
	public ResponseEntity<List<ReviewDTO>> findByMovieId(@PathVariable Long id) {
		return new ResponseEntity<>(service.findByMovieId(id), HttpStatus.OK);
	}
}
