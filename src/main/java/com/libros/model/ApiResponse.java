package com.libros.model;

import com.libros.dto.LibroApi;

import java.util.List;

public class ApiResponse {
    private List<LibroApi> results;

    public List<LibroApi> getResults() {
        return results;
    }

    public void setResults(List<LibroApi> results) {
        this.results = results;
    }
}