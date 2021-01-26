package com.rhb.interview.demo.service.impl;

import com.rhb.interview.demo.dto.*;
import com.rhb.interview.demo.repository.entity.MoviesEntity;
import com.rhb.interview.demo.repository.repository.MoviesRepository;
import com.rhb.interview.demo.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class MoviesServiceImpl implements MoviesService {
    private MoviesRepository moviesRepository;

    @Autowired
    public MoviesServiceImpl(MoviesRepository moviesRepository){
        this.moviesRepository = moviesRepository;
    }

    /**
     * Create movie api
     * this api will validate request as well as rating to make sure they are within the allowed range
     * it is annotated as transactional as we are saving into db. If there's issue a rollback will happen
     * @param movieRequest
     * @param bindingResult
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<VoidResponse> createMovie(MovieRequest movieRequest, BindingResult bindingResult) {
        VoidResponse response = new VoidResponse();
        if(bindingResult.hasErrors()){
            response.setErrors(getErrorsFromBind(bindingResult));
            return ResponseEntity.badRequest().body(response);
        }

        //Validate rating
        try{
            validateRating(movieRequest.getRating());
        } catch (InvalidRating e){
            response.setErrors(e.getErrors());
            return ResponseEntity.badRequest().body(response);
        }

        try {
            moviesRepository.save(new MoviesEntity(movieRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e){
            response.setError(e.getCause().toString());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get movies list api
     * it will just return ok even if it is empty
     * @return
     */
    @Override
    public ResponseEntity<MoviesResponse> getMovies() {
        List<Movie> movies = new ArrayList<>();
        moviesRepository.findAll().iterator().forEachRemaining(moviesEntity -> movies.add(new Movie(moviesEntity)));
        MoviesResponse response = new MoviesResponse();
        response.setMovies(movies);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a single movie
     * if movie is not found we will throw 404
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<MovieResponse> getMovie(String id) {
        Optional<MoviesEntity> moviesEntity = moviesRepository.findById(Long.valueOf(id));
        if(moviesEntity.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new MovieResponse(moviesEntity.get()));
    }

    /**
     * Update a movie
     * similar validation to create except it will also check for id and whether the movie is present
     * annotated with transactional to manage save transaction
     * @param id
     * @param movieRequest
     * @param bindingResult
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<MovieResponse> updateMovie(String id, MovieRequest movieRequest , BindingResult bindingResult) {
        //if is somehow missing
        if(id==null||id.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

        MovieResponse response = new MovieResponse();
        if(bindingResult.hasErrors()){
            response.setErrors(getErrorsFromBind(bindingResult));
            return ResponseEntity.badRequest().body(response);
        }

        //Validate rating
        try{
            validateRating(movieRequest.getRating());
        } catch (InvalidRating e){
            response.setErrors(e.getErrors());
            return ResponseEntity.badRequest().body(response);
        }

        Optional<MoviesEntity> moviesEntity = moviesRepository.findById(Long.valueOf(id));
        if(moviesEntity.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        try {
            MoviesEntity update =moviesEntity.get();
            update.setTitle(movieRequest.getTitle());
            update.setCategory(movieRequest.getCategory());
            update.setRating(Float.valueOf(movieRequest.getRating()));
            moviesRepository.save(update);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e){
            response.setError(e.getCause().toString());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete a movie
     * if movie is not found throw 404
     * annotated with transactional to manage transaction
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<VoidResponse> deleteMovie(String id) {
        Optional<MoviesEntity> moviesEntity = moviesRepository.findById(Long.valueOf(id));
        if(moviesEntity.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        try {
            moviesRepository.delete(moviesEntity.get());
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e){
            VoidResponse response = new VoidResponse();
            response.setError(e.getCause().toString());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateRating(String ratingInput){
        Float[] rating = {0.5f,1.0f,1.5f,2.0f,2.5f,3.0f,3.5f,4.0f,4.5f,5f};
        if(Arrays.stream(rating).noneMatch(aFloat -> aFloat.equals(Float.valueOf(ratingInput)))){
            List<Map<String,String>> errors = new ArrayList<>(){{
                add(new HashMap<>(){{
                    put("rating","Allowed rating 0.5,1.0,1.5,2.0,2.5,3.0,3.5,4.0,4.5,5");
                }});
            }};
            throw new InvalidRating(errors);
        }
    }

    private List<Map<String,String>> getErrorsFromBind(BindingResult result){
        List<Map<String,String>> errors = new ArrayList<>();
        result.getFieldErrors().iterator().forEachRemaining(fieldError -> {
            Map<String,String> error = new HashMap<>();
            error.put(fieldError.getField(),fieldError.getDefaultMessage());
            errors.add(error);
        });
        return errors;
    }
}
