package com.example.demo;

import java.time.LocalDate;
import java.util.*;


public class MusicLibrary {
    private Map<String, List<Song>> artistSongsMap;
    private List<Song> allSongs;

    public MusicLibrary(){
        artistSongsMap = new HashMap<>();
        allSongs = new ArrayList<>();
    }


    public void addSong(String songName, String artistName, LocalDate date) throws IllegalArgumentException
    {

        if(songName == null || songName.isEmpty() || artistName == null || artistName.isEmpty())
        {
            System.out.println("Song name and artist name cannot be null or empty.");
            return;
        }

        String songNameLower = songName.toLowerCase();
        String artistNameLower = artistName.toLowerCase();

        Song newSong = new Song(songName, artistName, date);
        allSongs.add(newSong);
        artistSongsMap.putIfAbsent(artistNameLower, new ArrayList<>());
        artistSongsMap.get(artistNameLower).add(newSong);
    }

    public List<Song> getSongByArtist(String artistName) throws NoSuchElementException{
        String artistNameLower = artistName.toLowerCase();

        if(!artistSongsMap.containsKey(artistNameLower)) {
            throw new NoSuchElementException("No songs found for the artist: " + artistName);
        }

        return artistSongsMap.get(artistNameLower);
    }

    public List<Song> getAllSongs(){
        return allSongs;
    }

}