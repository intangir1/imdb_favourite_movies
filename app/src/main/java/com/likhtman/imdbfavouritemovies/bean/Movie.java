package com.likhtman.imdbfavouritemovies.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Movie implements Serializable {
    private String imdbID;
    private int id;
    private String name;
    private String plot;
    private String url;
    private float rate;
    private int date;
    private transient Bitmap poster;
    private int seen;

    public Movie() {
    }

    public Movie(String name) {
        this.name = name;
    }

    public Movie(int id, String name) {
        setId(id);
        setName(name);
    }

    public Movie(String name, int date) {
        setName(name);
        setDate(date);
    }

    public Movie(int id, String name, int date, float rate, int seen) {
        setId(id);
        setName(name);
        setDate(date);
        setRate(rate);
        setSeen(seen);
    }

    public Movie(int id, String name, int date, String url, float rate, int seen) {
        setId(id);
        setName(name);
        setDate(date);
        setUrl(url);
        setRate(rate);
        setSeen(seen);
    }

    public Movie(String name, String plot, String url, float rate, int date, int seen) {
        setName(name);
        setPlot(plot);
        setUrl(url);
        setDate(date);
        setRate(rate);
        setSeen(seen);
    }

    public Movie(String name, String plot, String url, float rate, int date, int seen, String imdbID) {
        setName(name);
        setPlot(plot);
        setUrl(url);
        setDate(date);
        setRate(rate);
        setSeen(seen);
        setImdbID(imdbID);
    }

    public Movie(int id, String name, String plot, String url, float rate, int date, int seen) {
        setId(id);
        setName(name);
        setPlot(plot);
        setUrl(url);
        setDate(date);
        setRate(rate);
        setSeen(seen);
    }

    public Movie(int id, String name, String plot, String url, float rate, int date, int seen, String imdbID) {
        setId(id);
        setName(name);
        setPlot(plot);
        setUrl(url);
        setDate(date);
        setRate(rate);
        setSeen(seen);
        setImdbID(imdbID);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public int isSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }

    public Bitmap getPoster() {
        return poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        //movieForAdding.getName().equals(allMovies.get(i).getName()
        if (!this.getName().equals(movie.getName())) return false;
        if (this.getDate() != movie.getDate()) return false;
        return true;
    }
}
