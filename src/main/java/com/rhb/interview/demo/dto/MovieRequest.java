package com.rhb.interview.demo.dto;

import javax.validation.constraints.NotBlank;

public class MovieRequest {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotBlank(message = "Rating cannot be blank")
    private String rating;

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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
