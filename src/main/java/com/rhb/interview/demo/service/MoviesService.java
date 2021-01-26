package com.rhb.interview.demo.service;

import com.rhb.interview.demo.dto.MovieRequest;
import com.rhb.interview.demo.dto.MovieResponse;
import com.rhb.interview.demo.dto.MoviesResponse;
import com.rhb.interview.demo.dto.VoidResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface MoviesService {
    ResponseEntity<VoidResponse> createMovie(MovieRequest movieRequest, BindingResult bindingResult);
    ResponseEntity<MoviesResponse> getMovies();
    ResponseEntity<MovieResponse> getMovie(String id);
    ResponseEntity<MovieResponse> updateMovie(String id, MovieRequest movieRequest, BindingResult bindingResult);
    ResponseEntity<VoidResponse> deleteMovie(String id);
}
