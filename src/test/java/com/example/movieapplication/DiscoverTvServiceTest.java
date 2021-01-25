package com.example.movieapplication;

import com.example.movieapplication.models.DiscoverTv;
import com.example.movieapplication.models.Show;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscoverTvServiceTest {

    DiscoverTvService discoverTvService;

    @Autowired
    public void setDiscoverTvService(DiscoverTvService discoverTvService) {
        this.discoverTvService = discoverTvService;
    }

    @Test
    void getDiscoverTvs() {
        DiscoverTv result = discoverTvService.getDiscoverTvs();
        assertNotNull(result.getResults());
        assertEquals(result.getTotal_pages(), 500);
        assertEquals(result.getPage(), 500);
    }

    @Test
    void getPage() {
        DiscoverTv result = discoverTvService.getPage(5);
        assertNotNull(result.getResults());
        assertEquals(result.getPage_results(), 20);
        assertEquals(result.getPage(), 5);
    }

    @Test
    void findShow() {
        ArrayList<Show> results = discoverTvService.findShow("vikings");
        assertNotNull(results);
        for (Show show : results) {
            assertTrue(show.getName().toLowerCase().contains("vikings"));
        }
    }

    @Test
    void filterByAscending() {
        DiscoverTv result = discoverTvService.filterByAscending();
        assertNotNull(result.getResults());
        assertEquals(result.getTotal_pages(), 500);
    }

    @Test
    void filterByDescending() {
        DiscoverTv result = discoverTvService.filterByDescending();
        assertNotNull(result.getResults());
        assertEquals(result.getTotal_pages(), 500);
    }

    @Test
    void filterByTopRating() {
        DiscoverTv result = discoverTvService.filterByTopRating();
        Number firstShowRating = result.getResults().get(0).getVote_average();
        assertNotNull(result.getResults());
        assertEquals(result.getTotal_pages(), 500);
        assertEquals(firstShowRating, 10);
    }

    @Test
    void filterByLowRating() {
        DiscoverTv result = discoverTvService.filterByLowRating();
        Number firstShowRating = result.getResults().get(0).getVote_average();
        assertEquals(result.getTotal_pages(), 500);
        assertNotNull(result.getResults());
        assertEquals(firstShowRating, 0);
    }

    @Test
    void kaylaFavourites() {
        ArrayList<Show> results = discoverTvService.kaylaFavourites();
        boolean hasJerseyShore = false;
        for (Show show : results) {
            if (show.getName().toLowerCase().contains("jersey shore")) {
                hasJerseyShore = true;
                break;
            }
        }
        assertTrue(hasJerseyShore);
        assertEquals(results.size(), 15);
    }

    @Test
    void filterByPopularity() {
        DiscoverTv result = discoverTvService.filterByPopularity();
        String firstShow = result.getResults().get(0).getName();
        assertNotNull(result.getResults());
        assertEquals(result.getTotal_pages(), 500);
        assertEquals(firstShow.toLowerCase(), "game of thrones");
    }
}