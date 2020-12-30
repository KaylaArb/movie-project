package com.example.movieapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return discoverTvService.getAllTvShows();
    }

    @GetMapping("/page={pageNumber}")
    public DiscoverTv getPageResults(@PathVariable Integer pageNumber) {
        return discoverTvService.callApi(pageNumber);
    }
}
