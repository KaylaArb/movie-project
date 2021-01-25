package com.example.movieapplication;

import com.example.movieapplication.models.DiscoverTv;
import com.example.movieapplication.models.Show;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DiscoverTvService {

    private String apiKey;
    private String url;
    private RestTemplate restTemplate;
    private DiscoverTv discoverTvs;
    private static final int PAGE_SIZE = 20;

    public DiscoverTvService(@Value("${API_KEY}") String apiKey, @Value("${URL}") String url, RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.url = url;
        this.restTemplate = restTemplate;
        this.discoverTvs = getAllTvShows();
    }

    public DiscoverTv getDiscoverTvs() {
        return discoverTvs;
    }

    private DiscoverTv callApi(Integer page) {
        String ApiUrl = url + apiKey + "&page=" + page;
        return restTemplate.getForObject(ApiUrl, DiscoverTv.class);
    }

    private DiscoverTv getAllTvShows() {
        DiscoverTv discoverTv = callApi(1);
        ArrayList<Show> showList = new ArrayList<>(discoverTv.getResults());
        while (discoverTv.getPage() < discoverTv.getTotal_pages()) {
            discoverTv.setPage(discoverTv.getPage() + 1);
            discoverTv = callApi(discoverTv.getPage());
            showList.addAll(discoverTv.getResults());
        }
        System.out.println("Size of playlist = " + showList.size());
        discoverTv.setResults(showList);
        return discoverTv;
    }

    public DiscoverTv getPage(Integer number) {
        int num = number == 1 ? 0 : number * PAGE_SIZE;
        ArrayList<Show> showList = new ArrayList<>(this.discoverTvs.getResults().subList(num, num + PAGE_SIZE));
        return new DiscoverTv(number, this.discoverTvs.getTotal_results(), this.discoverTvs.getTotal_pages(), showList);
    }

    public ArrayList<Show> findShow(String name) {
        return discoverTvs
                .getResults()
                .stream()
                .filter(show -> show.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public DiscoverTv filterByAscending() {
        ArrayList<Show> result = discoverTvs
                .getResults()
                .stream()
                .sorted(Show::compareTo)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        discoverTvs.setResults(result);
        return discoverTvs;
    }

    public DiscoverTv filterByDescending() {
        ArrayList<Show> result = discoverTvs
                .getResults()
                .stream()
                .sorted(Collections.reverseOrder(Show::compareTo))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        discoverTvs.setResults(result);
        return discoverTvs;
    }

    public DiscoverTv filterByTopRating() {
        ArrayList<Show> result = discoverTvs
                .getResults()
                .stream()
                .sorted((show1, show2) -> Double.compare(show2.getVote_average().doubleValue(), show1.getVote_average().doubleValue()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        discoverTvs.setResults(result);
        return discoverTvs;
    }

    public DiscoverTv filterByLowRating() {
        ArrayList<Show> result = discoverTvs
                .getResults()
                .stream()
                .sorted(Comparator.comparingDouble(show -> show.getVote_average().doubleValue()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        discoverTvs.setResults(result);
        return discoverTvs;
    }

    public ArrayList<Show> kaylaFavourites() {
        ArrayList<Show> favourites = new ArrayList<>();
        favourites.addAll(findShow("His Dark Material"));
        favourites.addAll(findShow("Attack on Titan"));
        favourites.addAll(findShow("Keeping Up with the Kardashians"));
        favourites.addAll(findShow("The Mandalorian"));
        favourites.addAll(findShow("Re:ZERO -Starting Life in Another World-"));
        favourites.addAll(findShow("Made In Abyss"));
        favourites.addAll(findShow("Jersey Shore"));
        favourites.addAll(findShow("Mindhunter"));
        favourites.addAll(findShow("The Boys"));
        return favourites;
    }

    public DiscoverTv filterByPopularity() {
        ArrayList<Show> result = discoverTvs
                .getResults()
                .stream()
                .sorted((show1, show2) -> Integer.compare(show2.getVote_count(), show1.getVote_count()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        discoverTvs.setResults(result);
        return discoverTvs;
    }


}
