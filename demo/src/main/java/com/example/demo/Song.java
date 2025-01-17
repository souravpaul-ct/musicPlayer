package com.example.demo;
import java.util.*;
import java.time.*;


public class Song {
    private String name;
    private String artist;
    private LocalDate dateAdded;
    private int playCount;

    public Song(String name, String artist, LocalDate dateAdded){
        this.name = name;
        this.artist = artist;
        this.dateAdded = dateAdded;
        this.playCount = 0;
    }

    public String getName(){
        return name;
    }

    public String getArtist(){
        return artist;
    }

    public LocalDate getDateAdded(){
        return dateAdded;
    }

    public int getPlayCount(){
        return playCount;
    }

    public void incrementPlayCount(){
        this.playCount++;
    }

    @Override
    public String toString(){
        return name + " by " + artist + ": " + playCount;
    }
}