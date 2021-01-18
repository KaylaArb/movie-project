package com.example.movieapplication;

import com.example.movieapplication.models.DiscoverTv;
import com.example.movieapplication.models.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class MainController {

    DiscoverTvService discoverTvService;

    @Autowired
    public void setDiscoverTvService(DiscoverTvService discoverTvService) {
        this.discoverTvService = discoverTvService;
    }

    @GetMapping("/get_all")
    public DiscoverTv getAll() {
        return discoverTvService.getDiscoverTvs();
    }

    @GetMapping("/page={pageNumber}")
    public DiscoverTv getPage(@PathVariable Integer pageNumber) {
        return discoverTvService.getPage(pageNumber);
    }

    @GetMapping("/search={showName}")
    public ArrayList<Show> findShow(@PathVariable String showName) {
        return discoverTvService.findShow(showName);
    }

    @GetMapping("/filter/ascending")
    public DiscoverTv filterByAscending() {
        return discoverTvService.filterByAscending();
    }

    @GetMapping("/filter/descending")
    public DiscoverTv filterByADescending() {
        return discoverTvService.filterByDescending();
    }

    @GetMapping("/filter/high_rating")
    public DiscoverTv filterByTopRating() {
        return discoverTvService.filterByTopRating();
    }

    @GetMapping("/filter/low_rating")
    public DiscoverTv filterByLowRating() {
        return discoverTvService.filterByLowRating();
    }

    @GetMapping("/filter/favourites")
    public ArrayList<Show> getFavourites() {
        return discoverTvService.kaylaFavourites();
    }

    @GetMapping("/filter/popularity")
    public DiscoverTv getPopularity() {
        return discoverTvService.filterByPopularity();
    }


}
