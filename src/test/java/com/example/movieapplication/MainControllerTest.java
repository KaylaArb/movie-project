package com.example.movieapplication;

import com.example.movieapplication.models.DiscoverTv;
import com.example.movieapplication.models.Show;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// integration tests to test the HTTP calls

//@WebMvcTest(MainController.class) // focuses only on MVC components (controllers, components, services, repositories)
@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    private MockMvc mvc; // simulate HTTP requests

    @Autowired
    private ObjectMapper objectMapper; // maps to and from JSON

    DiscoverTvService discoverTvService;

    @Autowired
    public void setDiscoverTvService(DiscoverTvService discoverTvService) {
        this.discoverTvService = discoverTvService;
    }


    @Test
    void getAll() throws Exception {
        ResultActions resultActions = this.mvc.perform(get("/api/get_all"))
                .andExpect(status().isOk());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        DiscoverTv response = objectMapper.readValue(contentAsString, DiscoverTv.class);// deserialization
        assertEquals(response.getTotal_pages(), 500);
        assertNotNull(response.getResults());
        assert(!response.getResults().isEmpty());
        assertNotNull(response);
    }

    @Test
    void getPage() throws Exception {
        ResultActions resultActions = this.mvc.perform(get("/api/page=2"))
                .andExpect(status().isOk());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        DiscoverTv response = objectMapper.readValue(contentAsString, DiscoverTv.class); // deserialization
        assertEquals(response.getPage(), 2);
        assertEquals(response.getPage_results(), 20);
    }

    @Test
    void findShow() throws Exception {
        String searchValue = "mandalorian";
        ResultActions resultActions = this.mvc.perform(get("/api/search=" + searchValue))
                .andExpect(status().isOk());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        ArrayList<Show> response = objectMapper.readValue(contentAsString, new TypeReference<ArrayList<Show>>() {}); // deserialization
        for (Show show : response) {
            assertTrue(show.getName().toLowerCase().contains(searchValue));
        }
    }

    @Test
    void filterByAscending() throws Exception {
        DiscoverTv ascending = discoverTvService.filterByAscending();
        String expected = objectMapper.writeValueAsString(ascending);

        ResultActions expectedActions = this.mvc.perform(get("/api/filter/ascending")).andExpect(status().isOk());
        String actual = expectedActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertNotNull(actual);
        assertEquals(expected, actual); // cannot compare lists unless they are the same because lists are only equal by identity (hashcodes), not contents. This is why we converted them both to Strings.
    }

    @Test
    void filterByADescending() throws Exception {
        DiscoverTv descending = discoverTvService.filterByDescending();
        String expected = objectMapper.writeValueAsString(descending);

        ResultActions resultActions = this.mvc.perform(get("/api/filter/descending"))
                .andExpect(status().isOk());
        String actual = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void filterByTopRating() throws Exception {
        DiscoverTv topRated = discoverTvService.filterByTopRating();
        String expected = objectMapper.writeValueAsString(topRated);
        System.out.println(expected);

        ResultActions resultActions = this.mvc.perform(get("/api/filter/high_rating"))
                .andExpect(status().isOk());
        String actual = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void filterByLowRating() throws Exception {
        DiscoverTv lowRated = discoverTvService.filterByLowRating();
        String expected = objectMapper.writeValueAsString(lowRated);
        System.out.println(expected);

        ResultActions resultActions = this.mvc.perform(get("/api/filter/low_rating"))
                .andExpect(status().isOk());
        String actual = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getFavourites() throws Exception {
        ResultActions resultActions = this.mvc.perform(get("/api/filter/favourites"))
                .andExpect(status().isOk());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        ArrayList<Show> response = objectMapper.readValue(contentAsString, new TypeReference<ArrayList<Show>>() {}); // deserialization

        assertNotNull(response);
        assertEquals(response.size(), 17);
    }

    @Test
    void getPopularity() throws Exception {
        DiscoverTv popularity = discoverTvService.filterByPopularity();
        String expected = objectMapper.writeValueAsString(popularity);
        System.out.println(expected);

        ResultActions resultActions = this.mvc.perform(get("/api/filter/popularity"))
                .andExpect(status().isOk());
        String actual = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}