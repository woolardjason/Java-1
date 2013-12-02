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
		GAME1("BattleField 4", "United States", "ESRB:E", "Playstation 3", "2013-12-10"),
		GAME2("Final Fantasy XV", "United States", "ESRB:E", "Nintendo Wii", "2013-12-22"),
		GAME3("Destiny", "Canada", "ESRB", "Xbox 360", "2014-01-20"),
		GAME4("Dark Souls 2", "Europe", "ESRB:E", "Xbox One", "2014-10-12"),
		GAME5("Kingdom Hearts 3", "Asia", "ESRB", "Playstation 4", "2015-01-20");
		// String Variables
		private final String gameTitle;
		private final String gameRegion;
		private final String gameRating;
		private final String gameConsole;
		private final String releaseDate;
		// Constructor
		private Games(String gameTitle, String gameRegion, String gameRating, String gameConsole, String releaseDate)
		{
			this.gameTitle = gameTitle;
			this.gameRegion = gameRegion;
			this.gameRating = gameRating;
			this.gameConsole = gameConsole;
			this.releaseDate = releaseDate;
		}
		public String setTitle() {
			return gameTitle;
		}
		public String setRegion(){
			return gameRegion;
		}
		public String setRating(){
			return gameRating;
		}
		public String setConsole(){
			return gameConsole;
		}
		public String setReleaseDate(){
			return releaseDate;
		}
	}
}
