/*
 * Jason Woolard
 * Java 1 - Project 1
 * Full Sail University
 * Term 1312
 */

package com.jasonwoolard.java1project1.lib;

public class VideoGames {
	// Defining required enum for Video Game Titles (note to self: to be used externally hense public)
	public enum Games {
		BattleField4("United States", "ESRB:E", "Playstation 3", "2013-11-24"),
		FinalFantasyXV("United States", "ESRB:E", "Nintendo Wii", "2013-12-10"),
		Destiny("Canada", "ESRB", "Xbox 360", "2013-12-29"),
		DarkSouls2("Europe", "ESRB:E", "Xbox One", "2014-01-04"),
		KingdomHearts3("Asia", "ESRB", "Playstation 4", "2014-01-20");
		// String Variables
		private final String region;
		private final String rating;
		private final String console;
		private final String releaseDate;
		// Constructor
		private Games(String region, String rating, String console, String releaseDate)
		{
			this.region = region;
			this.rating = rating;
			this.console = console;
			this.releaseDate = releaseDate;
		}
		public String setRegion(){
			return region;
		}
		public String setRating(){
			return rating;
		}
		public String setConsole(){
			return console;
		}
		public String setReleaseDate(){
			return releaseDate;
		}
	}
}
