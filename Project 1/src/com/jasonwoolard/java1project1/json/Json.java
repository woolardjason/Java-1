/*
 * Jason Woolard
 * Java 1 - Project 1
 * Full Sail University
 * Term 1312
 */

package com.jasonwoolard.java1project1.json;

import org.json.JSONException;
import org.json.JSONObject;
// Importing Custom Video Games Class from lib package
import com.jasonwoolard.java1project1.lib.VideoGames.Games;

public class Json {
	public static JSONObject createJSON() {
		// Create Video Games JSON Object
		JSONObject videoGamesObject = new JSONObject();
		try {
			// Create Results JSON Object
			JSONObject resultsObject = new JSONObject();

			// Create Video Games Object in Results
			for (Games game : Games.values()) {
				// Create Video Game Object
				JSONObject videoGameObject = new JSONObject();

				// Adding Info to Video Game Object
				videoGameObject.put("title", game.setTitle());
				videoGameObject.put("region", game.setRegion());
				videoGameObject.put("rating", game.setRating());
				videoGameObject.put("console", game.setConsole());
				videoGameObject.put("release date", game.setReleaseDate());
				resultsObject.put(game.name().toString(), videoGameObject);
			}
			// Adding Results to Video Games
			videoGamesObject.put("results", resultsObject);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		// Returning the videoGamesObject
		return videoGamesObject;
	}
	// Read Json Method
	public static String readJSON(String selected) {
		String result, title, region, rating, console, releaseDate;
		JSONObject object = createJSON();
		
		try {
			// Setting each string variable to get the json details from object based on key input in getString();
			title = object.getJSONObject("results").getJSONObject(selected).getString("title");
			region = object.getJSONObject("results").getJSONObject(selected).getString("region");
			rating = object.getJSONObject("results").getJSONObject(selected).getString("rating");
			console = object.getJSONObject("results").getJSONObject(selected).getString("console");
			releaseDate = object.getJSONObject("results").getJSONObject(selected).getString("release date");
			// Neatly organizing the data into the result string to be displayed in a TextView
			result = "Game Title: " + title + "\r\n" +
					 "Region: " + region + "\r\n" +
					 "Rating: "	+ rating + "\r\n" +
					 "Console: " + console + "\r\n" +
					 "Release Date: " + releaseDate + "\r\n";
			
		} catch (JSONException e) {
			e.printStackTrace();
			result = e.toString();
		}
		// Returning result string containing above data specified
		return result;
	}
}