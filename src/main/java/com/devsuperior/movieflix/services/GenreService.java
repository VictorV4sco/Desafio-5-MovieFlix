package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.repositories.GenreRepository;

@Service
public class GenreService {

	@Autowired
	private GenreRepository genreRepository;
	
	public List<GenreDTO> findAllGenres() {
		List<Genre> list = genreRepository.findAll();
		return list.stream().map(x -> new GenreDTO(x.getId(), x.getName())).collect(Collectors.toList());
	}
}
