package com.example.movieapplication;

import com.example.movieapplication.models.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ArrayList<Show> getAll() {
        return discoverTvService.getDiscoverTvs();
    }

    @GetMapping("/page={pageNumber}")
    public DiscoverTv getPageResults(@PathVariable Integer pageNumber) {
        return discoverTvService.callApi(pageNumber);
    }

    @GetMapping("/search={showName}")
    public ArrayList<Show> findShow(@PathVariable String showName) {
        return discoverTvService.findShow(showName);
    }

    @GetMapping("/filter")
    public ArrayList<Show> filterByAlphabet() {
        return discoverTvService.filterByAlphabet();
    }

};
