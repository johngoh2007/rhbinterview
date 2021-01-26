package com.rhb.interview.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhb.interview.demo.repository.entity.MoviesEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieResponse extends AbstractRestResponse{
    private Long id;

    private String title;

    private String category;

    private Float rating;

    public MovieResponse() {
    }

    public MovieResponse(MoviesEntity moviesEntity) {
        this.id = moviesEntity.getId();
        this.title = moviesEntity.getTitle();
        this.category = moviesEntity.getCategory();
        this.rating = moviesEntity.getRating();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
