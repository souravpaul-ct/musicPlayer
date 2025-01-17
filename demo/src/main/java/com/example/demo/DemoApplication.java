package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.NoSuchElementException;
import java.time.LocalDate;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
		MusicLibrary library = new MusicLibrary();
		PlaybackManager manager = new PlaybackManager(library);
		Scanner scanner = new Scanner(System.in);

		try {
			while (true) {
				System.out.println("Main Menu:");
				System.out.println("1. Add Songs");
				System.out.println("2. Play a Song");
				System.out.println("3. Get Top 5 Songs");
				System.out.println("4. Get Top 5 Songs by Artist");
				System.out.println("5. Get Top 5 Songs of the Last 5 Days");
				System.out.println("6. Get the list of Songs which are played less than (e.g., x) times");
				System.out.println("7. Exit");
				System.out.print("Please choose an option: ");
				int choice = Integer.parseInt(scanner.nextLine());

				switch (choice) {
					case 1:
						// Add songs code
						boolean validInput = false;

						while (!validInput) {
							try {
								System.out.println("Enter the number of songs you want to add:");
								int numSongs = Integer.parseInt(scanner.nextLine());

								if (numSongs <= 0) {
									System.out.println("Please enter a positive number.");
									continue;
								}

								for (int i = 0; i < numSongs; i++) {
									System.out.println("Enter song name:");
									String songName = scanner.nextLine();
									System.out.println("Enter artist name:");
									String artistName = scanner.nextLine();
									LocalDate date = null;


									boolean validDate = false;
									while (!validDate) {
										System.out.println("Enter date (dd/mm/yyyy) or press enter to use today's date:");
										String dateString = scanner.nextLine();

										if (dateString.isEmpty()) {
											date = LocalDate.now();  // Using current date
											validDate = true;
										} else {
											try {
												date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
												validDate = true;  // Validating date format, so exit the loop
											} catch (DateTimeParseException e) {
												System.out.println("Invalid date format. Please enter the date in dd/mm/yyyy format.");
											}
										}
									}

									library.addSong(songName, artistName, date);
								}

								validInput = true;
							} catch (NumberFormatException e) {
								System.out.println("Invalid input. Please enter a valid number of songs.");
							}
						}

						break;

					case 2:
						// Play song code
						System.out.println("Enter the name of the song to play:");
						String songName = scanner.nextLine();
						System.out.println("Enter the artist of the song to play:");
						String artistName = scanner.nextLine();
						manager.playSong(songName, artistName);
						break;

					case 3:
						// Get top 10 songs code
						System.out.println("Top 5 Songs: " + manager.getTop5Songs());
						break;

					case 4:
						// Get top 10 songs by artist code
						System.out.println("Enter artist name to retrieve top 5 songs:");
						artistName = scanner.nextLine();
						System.out.println("Top 5 Songs by " + artistName + ": " + manager.getTop5Songs(artistName));
						break;

					case 5:
						// Get top 5 songs of the last 5 days code
						System.out.println("Top 5 Songs for the Last 5 Days: " + manager.getTop5SongsOfLast5Days());
						break;

					case 6:
						// get the list of songs which are played less than x times
						System.out.println("Enter the number of plays : ");
						int x = Integer.parseInt(scanner.nextLine());

						System.out.println("\nSongs played less than " + x + " times:");
						for (Song song : manager.getSongsPlayedLessThan(x)) {
							System.out.println(song);
						}


					case 7:
						// Exit the program
						System.out.println("Exiting...");
						scanner.close();
						return;

					default:
						System.out.println("Invalid choice. Please try again.");
						break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
