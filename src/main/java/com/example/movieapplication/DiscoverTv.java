package com.example.movieapplication;

import com.example.movieapplication.models.Show;

import java.util.ArrayList;

public class DiscoverTv {

    private Integer page;
    private Integer total_results;
    private Integer total_pages;
    private Integer page_results;
    private ArrayList<Show> results;

    public DiscoverTv() {
        this.results = new ArrayList<Show>();
    }

    public DiscoverTv(Integer page, Integer total_results, Integer total_pages) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = new ArrayList<Show>();
        this.page_results = 0;
    }

    public Integer getPage_results() {
        return page_results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<Show> getResults() {
        return results;
    }

    public void setResults(ArrayList<Show> results) {
        this.results = results;
        this.page_results = results.size();
    }
}
