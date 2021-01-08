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
    private ArrayList<Show> discoverTvs;

    public DiscoverTvService(@Value("${API_KEY}") String apiKey, @Value("${URL}") String url, RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.url = url;
        this.restTemplate = restTemplate;
        this.discoverTvs = getAllTvShows();
    }

    public DiscoverTv callApi(Integer page) {
        String ApiUrl = url + apiKey + "&page=" + page;
        return restTemplate.getForObject(ApiUrl, DiscoverTv.class);
    }

    public ArrayList<Show> getAllTvShows() {
        ArrayList<Show> showList = new ArrayList<>();
        DiscoverTv discoverTv = callApi(1);
        showList.addAll(discoverTv.getResults());
        while (discoverTv.getPage() < 5) {
            discoverTv.setPage(discoverTv.getPage() + 1);
            discoverTv = callApi(discoverTv.getPage());
            showList.addAll(discoverTv.getResults());
        }
        System.out.println("Size of playlist = " + showList.size());
        discoverTv.setResults(showList);
        return showList;
    }

    public ArrayList<Show> getDiscoverTvs() {
        return discoverTvs;
    }

    public ArrayList<Show> findShow(String name) {
        System.out.println("Over here?");
        ArrayList<Show> result = discoverTvs
                .stream()
                .filter(show -> show.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("This worked here");
        return result;
    }

    public ArrayList<Show> filterByAlphabet() {
        ArrayList<Show> result = discoverTvs
                .stream()
                .sorted()
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return result;
    }
}
