package com.rhb.interview.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhb.interview.demo.controller.MoviesController;
import com.rhb.interview.demo.dto.MovieRequest;
import com.rhb.interview.demo.repository.entity.MoviesEntity;
import com.rhb.interview.demo.repository.repository.MoviesRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is the test cases written against H2DB
 * chose h2 just incase postman were not used to test the application at least this will prove that the api works
 * better test would be written in mockito hence there's no need to run against h2db where we can mock the services
 */
@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MoviesController controller;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    @Transactional
    public static void setup(@Autowired MoviesRepository moviesRepository) {
        System.out.println("Setup data");
        for (int i = 0; i < 10; i++) {
            MoviesEntity mockData = new MoviesEntity();
            mockData.setId((long) i);
            mockData.setTitle("Test " + i);
            mockData.setCategory("Test " + i);
            mockData.setRating(0.5f);
            moviesRepository.save(mockData);
        }
    }

    @AfterAll
    @Transactional
    public static void cleanUp(@Autowired MoviesRepository moviesRepository) {
        System.out.println("test clean up");
        moviesRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }

    @Test
    void test_create() throws Exception {
        //Request
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setTitle("test create");
        movieRequest.setCategory("romance");
        movieRequest.setRating("0.5");

        String request = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.writeValueAsString(movieRequest);
        } catch (JsonProcessingException e) {
            fail(e.getCause());
        }
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/movies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void test_create_null() throws Exception {
        //Request
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setTitle(null);
        movieRequest.setCategory("romance");
        movieRequest.setRating("0.5");

        String request = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.writeValueAsString(movieRequest);
        } catch (JsonProcessingException e) {
            fail(e.getCause());
        }
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/movies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void test_create_invalid_rating() throws Exception {
        //Request
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setTitle("test");
        movieRequest.setCategory("romance");
        movieRequest.setRating("0.55");

        String request = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.writeValueAsString(movieRequest);
        } catch (JsonProcessingException e) {
            fail(e.getCause());
        }
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/movies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void test_get_list() throws Exception {
        //Request
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/movies")
                )
                .andExpect(jsonPath("$.movies", hasSize(10)));
    }

    @Test
    void test_get() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/movies/1")
                )
                .andExpect(jsonPath("$.title", is("Test 1")));
    }

    @Test
    void test_get_not_found() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/movies/1")
                )
                .andExpect(status().isOk());
    }

    @Test
    void test_update() throws Exception {
        System.out.println("test update");
        //Request
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setTitle("test update");
        movieRequest.setCategory("romance");
        movieRequest.setRating("0.5");

        String request = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.writeValueAsString(movieRequest);
        } catch (JsonProcessingException e) {
            fail(e.getCause());
        }

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/movies/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isOk());
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/movies/1")
                )
                .andExpect(jsonPath("$.title", is("test update")));
    }

    @Test
    void test_update_null() throws Exception {
        System.out.println("test update");
        //Request
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setTitle(null);
        movieRequest.setCategory("romance");
        movieRequest.setRating("0.5");

        String request = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.writeValueAsString(movieRequest);
        } catch (JsonProcessingException e) {
            fail(e.getCause());
        }

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/movies/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void test_update_invalid_rating() throws Exception {
        System.out.println("test update");
        //Request
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setTitle("Not null");
        movieRequest.setCategory("romance");
        movieRequest.setRating("0.55");

        String request = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.writeValueAsString(movieRequest);
        } catch (JsonProcessingException e) {
            fail(e.getCause());
        }

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/movies/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void test_update_not_found() throws Exception {
        //Request
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setTitle("Not null");
        movieRequest.setCategory("romance");
        movieRequest.setRating("0.5");

        String request = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.writeValueAsString(movieRequest);
        } catch (JsonProcessingException e) {
            fail(e.getCause());
        }

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/movies/100000")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void test_delete() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/movies/1")
                )
                .andExpect(status().isOk());
    }

    @Test
    void test_delete_not_found() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/movies/100000")
                )
                .andExpect(status().isNotFound());
    }
}
