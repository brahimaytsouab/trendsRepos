package com.example.trend.model;

import java.util.ArrayList;
import java.util.List;

public class Language {
    private String name;
    private List<String> repos = new ArrayList<String>();
    private int totalRepo=0;

    public Language(String name, List<String> repos, int totalRepo) {
        this.name = name;
        this.repos = repos;
        this.totalRepo = totalRepo;
    }


    public Language() {
    }

    public String getName() {
        return name;
    }

    public List<String> getRepos() {
        return repos;
    }

    public int getTotalRepo() {
        return totalRepo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRepos(List<String> repos) {
        this.repos = repos;
    }

    public void setTotalRepo(int totalRepo) {
        this.totalRepo = totalRepo;
    }

    public void addRepo(String repo) {
        this.repos.add(repo);
        this.totalRepo ++;
    }
}
