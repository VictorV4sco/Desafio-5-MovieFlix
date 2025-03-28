package com.devsuperior.movieflix.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	@Query("SELECT m FROM Movie m WHERE (:id IS NULL OR m.genre.id = :id)")
	Page<Movie> searchByGenre(Long id, Pageable pageable);
	
	@Query("SELECT new com.devsuperior.movieflix.dto.MovieDetailsDTO(m.id, m.title, m.subTitle, m.year, m.imgUrl, m.synopsis, " +
	           "new com.devsuperior.movieflix.dto.GenreDTO(m.genre.id, m.genre.name)) " +
	           "FROM Movie m WHERE m.id = :id")
	    MovieDetailsDTO findMovieDetailsById(@Param("id") Long id);
}
