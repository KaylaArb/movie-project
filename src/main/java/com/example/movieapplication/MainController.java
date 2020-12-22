package com.example.movieapplication;

import com.example.movieapplication.models.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/get")
    public ArrayList<Show> getTvShows() {
        return discoverTvService.getTvShows();
    }
}
