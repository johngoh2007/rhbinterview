package com.rhb.interview.demo.repository.entity;

import com.rhb.interview.demo.dto.MovieRequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MoviesEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;

    private String category;

    private Float rating;

    public MoviesEntity() {
    }

    public MoviesEntity(MovieRequest movieRequest) {
        this.title = movieRequest.getTitle();
        this.category = movieRequest.getCategory();
        this.rating = Float.valueOf(movieRequest.getRating());
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
