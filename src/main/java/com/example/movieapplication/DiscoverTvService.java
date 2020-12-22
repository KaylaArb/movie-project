package com.example.movieapplication;

import com.example.movieapplication.models.Show;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class DiscoverTvService {

    private String apiKey;
    private String url;
    private RestTemplate restTemplate;
    private ArrayList<Show> tvShows;


    public DiscoverTvService(@Value("${API_KEY}") String apiKey, @Value("${URL}") String url, RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.url = url;
        this.restTemplate = restTemplate;
        this.tvShows = setTvShows();
    }

    private DiscoverTv callApi(Integer page) {
        String ApiUrl = url + apiKey + "&page=" + page;
        DiscoverTv discoverTv = restTemplate.getForObject(ApiUrl, DiscoverTv.class);
        return discoverTv;
    }

    private ArrayList<Show> setTvShows() {
        ArrayList<Show> showList = new ArrayList<>();
        DiscoverTv discoverTv = callApi(1);
        showList.addAll(discoverTv.getResults());
        while (discoverTv.getPage() < discoverTv.getTotal_pages()) {
            discoverTv.setPage(discoverTv.getPage() + 1);
            discoverTv = callApi(discoverTv.getPage());
            showList.addAll(discoverTv.getResults());
        }
        System.out.println("Size of playlist = " + showList.size());
        return showList;
    }

    public ArrayList<Show> getTvShows() {
        return tvShows;
    }
}
