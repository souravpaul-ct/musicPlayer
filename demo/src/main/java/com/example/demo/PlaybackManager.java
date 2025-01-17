package com.example.demo;
import java.util.*;
import java.util.NoSuchElementException;
import java.time.LocalDate;

 public class PlaybackManager {
     private MusicLibrary library;
     private PriorityQueue<Song> topSongs;
     private Map<String, PriorityQueue<Song>> artistTopSongs;

     public PlaybackManager(MusicLibrary library) {
         this.library = library;
         this.topSongs = new PriorityQueue<>(Comparator.comparingInt(Song::getPlayCount));
         this.artistTopSongs = new HashMap<>();
     }

     public void playSong(String songName, String artistName) throws NoSuchElementException {
         Song targetSong = null;
         String songNameLower = songName.toLowerCase();
         String artistNameLower = artistName.toLowerCase();

         for (Song song : library.getAllSongs()) {
             if (song.getName().toLowerCase().equals(songNameLower) &&
                     song.getArtist().toLowerCase().equals(artistNameLower)) {
                 targetSong = song;
                 break;
             }
         }


         if (targetSong == null) {
             System.out.println("Song not found: " + songName + " by " + artistName);
             return;
         }

         targetSong.incrementPlayCount();
         updateTopSongs(targetSong);
         updateArtistTopSongs(targetSong);
         System.out.println("Played: " + songName + " by " + artistName);
     }

     private void updateTopSongs(Song song) {
         if (topSongs.contains(song)) {
             topSongs.remove(song); // so here we are removing the old entry with outdated play count
         }
         topSongs.offer(song);

         if (topSongs.size() > 5) {
             topSongs.poll();
         }
     }

     private void updateArtistTopSongs(Song song) {
         artistTopSongs.putIfAbsent(song.getArtist().toLowerCase(),
                 new PriorityQueue<>(Comparator.comparingInt(Song::getPlayCount)));

         PriorityQueue<Song> artistQueue = artistTopSongs.get(song.getArtist().toLowerCase());

         if (artistQueue.contains(song)) {
             artistQueue.remove(song); // Remove the outdated entry
         }
         artistQueue.offer(song);
         if (artistQueue.size() > 5) {
             artistQueue.poll();
         }
     }

     public List<Song> getTop5Songs() {
         PriorityQueue<Song> queue = new PriorityQueue<>(Comparator.comparingInt(Song::getPlayCount).reversed());

         for (Song song : library.getAllSongs()) {
             queue.offer(song);
             if (queue.size() > 5) {
                 queue.poll();
             }
         }

         if (queue.isEmpty()) {
             System.out.println("No songs available in the library.");
             return new ArrayList<>();
         }

         List<Song> result = new ArrayList<>(queue);
         result.sort(Comparator.comparingInt(Song::getPlayCount).reversed());
         return result;
     }

     public List<Song> getTop5Songs(String artistName) throws NoSuchElementException {
         if (!artistTopSongs.containsKey(artistName.toLowerCase())) {
             System.out.println("No songs found for the artist: " + artistName);
             return new ArrayList<>();
         }

         PriorityQueue<Song> queue = new PriorityQueue<>(Comparator.comparingInt(Song::getPlayCount).reversed());
         for (Song song : artistTopSongs.get(artistName)) {
             queue.offer(song);
             if (queue.size() > 5) {
                 queue.poll();
             }
         }

         if (queue.isEmpty()) {
             System.out.println("No top songs available for the artist: " + artistName);
             return new ArrayList<>();
         }

         List<Song> result = new ArrayList<>(queue);
         result.sort(Comparator.comparingInt(Song::getPlayCount).reversed());
         return result;
     }

     public List<Song> getTop5SongsOfLast5Days() {
         List<Song> top5SongsLast5Days = new ArrayList<>();
         LocalDate currentDate = LocalDate.now();

         for (int i = 0; i < 5; i++) {
             LocalDate targetDate = currentDate.minusDays(i);

             // We are Using a min-heap to track the top 5 songs for the given day
             PriorityQueue<Song> dayQueue = new PriorityQueue<>(Comparator.comparingInt(Song::getPlayCount));

             for (Song song : library.getAllSongs()) {
                 if (song.getDateAdded().equals(targetDate)) {
                     dayQueue.offer(song);
                     // If the size exceeds 5, remove the smallest play count song
                     if (dayQueue.size() > 5) {
                         dayQueue.poll();
                     }
                 }
             }

             // Here we are adding  the songs from the day's queue to the result list
             top5SongsLast5Days.addAll(dayQueue);
         }

         
         if (top5SongsLast5Days.isEmpty()) {
             System.out.println("No songs played in the last 5 days.");
             return new ArrayList<>();
         }

         top5SongsLast5Days.sort(Comparator.comparingInt(Song::getPlayCount).reversed());
         return top5SongsLast5Days;

     }

     public List<Song> getSongsPlayedLessThan(int x) {
         List<Song> filteredSongs = new ArrayList<>();
         for (Song song : library.getAllSongs()) {
             if (song.getPlayCount() < x) {
                 filteredSongs.add(song);
             }
         }
         return filteredSongs;
     }
 }
