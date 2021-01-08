package com.example.movieapplication;

import com.example.movieapplication.models.Show;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;

@Service
public class DiscoverTvService {

    private String apiKey;
    private String url;
    private RestTemplate restTemplate;
    private DiscoverTv discoverTvs;

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

    public DiscoverTv getDiscoverTvs() {
        return discoverTvs;
    }

    public DiscoverTv getAllTvShows() {
        ArrayList<Show> showList = new ArrayList<>();
        DiscoverTv discoverTv = callApi(1);
        showList.addAll(discoverTv.getResults());
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
        DiscoverTv pageResults = new DiscoverTv(number,discoverTvs.getTotal_results(),discoverTvs.getTotal_pages());
        ArrayList<Show> showList = new ArrayList<>();
        int num = number == 1 ? 0 : number * 10;
        for(int i = num; i < num + 20;i++) {
            showList.add(discoverTvs.getResults().get(i));
        }
        pageResults.setResults(showList);
        return pageResults;
    }

    public ArrayList<Show> findShow(String name) {
        ArrayList<Show> result = discoverTvs
                .getResults()
                .stream()
                .filter(show -> show.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return result;
    }

    public DiscoverTv filterByAlphabet() {
        ArrayList<Show> result = discoverTvs
                .getResults()
                .stream()
                .sorted(Show::compareTo)
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
