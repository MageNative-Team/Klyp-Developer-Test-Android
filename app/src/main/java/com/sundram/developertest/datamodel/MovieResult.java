package com.sundram.developertest.datamodel;

import androidx.lifecycle.LiveData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResult {

    @SerializedName("page")
    private Integer page_no;

    @SerializedName("total_pages")
    private Integer total_pages;

    @SerializedName("total_results")
    private Integer total_results   ;

    @SerializedName("results")
    private List<Movie> movie_list;

    public Integer getPage_no() {
        return page_no;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public List<Movie> getMovie_list() {
        return movie_list;
    }
}
