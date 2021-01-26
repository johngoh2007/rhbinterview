package com.rhb.interview.demo.dto;

import java.util.List;
import java.util.Map;

public class InvalidRating extends RuntimeException{
    private List<Map<String,String>> errors;

    public InvalidRating(List<Map<String, String>> errors) {
        this.errors = errors;
    }

    public List<Map<String, String>> getErrors() {
        return errors;
    }

    public void setErrors(List<Map<String, String>> errors) {
        this.errors = errors;
    }
}
