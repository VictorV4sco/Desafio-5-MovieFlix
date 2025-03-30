package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;

	@Autowired
	private AuthService authService;

	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {

		Review review = new Review();
		review.setText(dto.getText());

		Movie movie = new Movie();
		movie.setId(dto.getMovieId());
		review.setMovie(movie);

		User userLogado = authService.authenticated();
		User novoUser = new User();
		novoUser.setId(userLogado.getId());
		novoUser.setName(userLogado.getName());
		novoUser.setEmail(userLogado.getEmail());
		review.setUser(novoUser);

		review = repository.save(review);

		return new ReviewDTO(review.getId(), review.getText(), review.getMovie().getId(), review.getUser().getId(),
				review.getUser().getName(), review.getUser().getEmail());

	}

}
