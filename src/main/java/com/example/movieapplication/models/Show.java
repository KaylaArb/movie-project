package com.example.movieapplication.models;

public class Show {

    private String name;
    private Integer id;
    private String first_air_date;
    private String overview;
    private Number vote_average;
    private Integer vote_count;
    private String[] origin_country;
    private Integer[] genre_ids;
    private String original_language;
    private String poster_path;

    public Show() {

    }

    public Show(String name, Integer id, String first_air_date, String overview, Number vote_average, Integer vote_count, String[] origin_country, Integer[] genre_ids, String original_language, String poster_path) {
        this.name = name;
        this.id = id;
        this.first_air_date = first_air_date;
        this.overview = overview;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.origin_country = origin_country;
        this.genre_ids = genre_ids;
        this.original_language = original_language;
        this.poster_path = poster_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Number getVote_average() {
        return vote_average;
    }

    public void setVote_average(Number vote_average) {
        this.vote_average = vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public String[] getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String[] origin_country) {
        this.origin_country = origin_country;
    }

    public Integer[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(Integer[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }
}
