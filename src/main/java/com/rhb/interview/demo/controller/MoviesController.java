package com.rhb.interview.demo.controller;

import com.rhb.interview.demo.dto.MovieRequest;
import com.rhb.interview.demo.dto.MovieResponse;
import com.rhb.interview.demo.dto.MoviesResponse;
import com.rhb.interview.demo.dto.VoidResponse;
import com.rhb.interview.demo.service.MoviesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/movies")
public class MoviesController {

    //Service layer
    private final MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @PostMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Create a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VoidResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Unknown Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VoidResponse.class))})})
    public ResponseEntity<VoidResponse> create(@Valid @RequestBody MovieRequest request, BindingResult bindingResult) {
        return moviesService.createMovie(request, bindingResult);
    }

    @GetMapping(value = {"", "/"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get a list of movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MoviesResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Unknown Server Error",
                    content = @Content)})
    public ResponseEntity<MoviesResponse> getList() {
        return moviesService.getMovies();
    }

    @GetMapping(value = {"/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Unknown Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class))})})
    public ResponseEntity<MovieResponse> getMovie(@PathVariable(value = "id") String id) {
        return moviesService.getMovie(id);
    }

    @PutMapping(value = {"/{id}"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Unknown Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class))})})
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable(value = "id") String id, @Valid @RequestBody MovieRequest request, BindingResult bindingResult) {
        return moviesService.updateMovie(id, request, bindingResult);
    }

    @DeleteMapping(value = {"/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Delete a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Unknown Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class))})})
    public ResponseEntity<VoidResponse> deleteMovie(@PathVariable(value = "id") String id) {
        return moviesService.deleteMovie(id);
    }

}
