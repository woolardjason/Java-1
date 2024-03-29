package com.example.java1week4.lib;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import com.example.java1week4.network.NetworkConn;

public class Data extends AsyncTask<String, Void, String> {
	static String mTAG = "DATA CLASS - Data Class";
	// Local Vars
	String TAG_PARENT = "results";
	String TAG_NAME = "name";
	String TAG_DECK = "deck";
	String TAG_RELEASE = "expected_release_year";
	static Context context;
	public static ArrayList<HashMap<String, String>> gameList = new ArrayList<HashMap<String, String>>();
	
	// Setting interface 'returnListListener' up to be implemented by MainActivity
	public interface returnListListener {
		public void returnList(ArrayList<HashMap<String, String>> gList);
	}
	// Setting mCallBack var to returnListListener
	returnListListener mCallBack;
	public Data(Context context) {
		mCallBack = (returnListListener) context;
	}
	// onPreExecute Method - Setting TextView Cars to TextView IDS located
	// in xml
	protected void onPreExecute() {
		super.onPreExecute();
		// Clearing out the GameList (for reloads)
		gameList.clear();
	}
	@Override
	protected String doInBackground(String... urls) {
		// Resetting URL
		String fURL = "";
		// Setting fURL stringVar to response of getURLReponse method
		// from WebClass class.
		fURL = NetworkConn.getURLResponse(urls[0]);

		// Returning fURL string var
		return fURL;
	}
	// OnPostExecute method
	protected void onPostExecute(String result) {
		try {
			// Setting json var for type JSONObject to new JSOBObject with
			// passed in string result
			JSONObject json = new JSONObject(result);
			// Setting results JSONArray var to results json node
			JSONArray results = json.getJSONArray(TAG_PARENT);
			// Setting j int var to results array length
			int j = results.length();
			// For conditional cycling through results array grabbing
			// defined nodes and storing them
			for (int i = 0; i < j; i++) {
				// Setting JSONObject var 'jo' to each object within results
				// array
				JSONObject jo = results.getJSONObject(i);

				// Storing pulled in JSON Nodes into separate strings
				String nam = jo.getString(TAG_NAME);
				String dec = jo.getString(TAG_DECK);
				String rel = jo.getString(TAG_RELEASE);

				// Creating HashMap entitled map and initializing it
				HashMap<String, String> map = new HashMap<String, String>();

				// Putting JSON nodes into map
				map.put(TAG_RELEASE, rel);
				map.put(TAG_NAME, nam);
				map.put(TAG_DECK, dec);

				// Adding data to gameList Local var
				gameList.add(map);
				// Checking if local var 'mCallBack' is not equal to null, if not call returnList and pass it created 'gameList' to be used
				if (mCallBack != null) {
					mCallBack.returnList(gameList);
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
