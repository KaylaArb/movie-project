package com.example.movieapplication.models;

import java.util.ArrayList;

public class DiscoverTv {

    private Integer page;
    private Integer total_results;
    private Integer total_pages;
    private Integer page_results;
    private ArrayList<Show> results;

    public DiscoverTv() {
        this.results = new ArrayList<>();
    }

    public DiscoverTv(Integer page, Integer total_results, Integer total_pages, ArrayList<Show> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
        this.page_results = results.size();
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

    @Override
    public String toString() {
        return "DiscoverTv{" +
                "page=" + page +
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                ", page_results=" + page_results +
                ", results=" + results +
                '}';
    }
}
