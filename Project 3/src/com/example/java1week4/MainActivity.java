package com.example.java1week4;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.java1week4.network.NetworkConn;

public class MainActivity extends Activity {
	// Local Variables
	Context context;
	String mTAG = "NETWORK ACTIVITY - MainActivity Class";
	String mUrlString = "http://www.giantbomb.com/api/games/?api_key=84bb1f7ad08b299e6c29992eff7ed6278f406a15&format=json&limit=5&sort=original_release_date:asc&filter=expected_release_year:";
	String[] mReleaseYears;
	TextView resultsView;
	ListView list;
	TextView name;
	TextView deck;
	TextView release;
	Boolean mConnected = false;

	// ArrayList HashMap entitled gameList to store json data to be used throughout app
	ArrayList<HashMap<String, String>> gameList;
	
	// Node names from JSON Data
	String TAG_PARENT = "results";
	String TAG_NAME = "name";
	String TAG_DECK = "deck";
	String TAG_RELEASE = "expected_release_year";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_form);
		// Setting context local var to this activity.
		context = this;
		resultsView = (TextView) findViewById(R.id.results_view);
		// Setting gameList Local Var to a new ArrayList HashMap to store data
		gameList = new ArrayList<HashMap<String, String>>();
		// Setting mReleaseYears Local Var to String Array from resources
		mReleaseYears = getResources().getStringArray(R.array.yearArray);
		
		// Creating Spinner Adapter
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, mReleaseYears);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Creating the spinner
		Spinner viewSpinner = (Spinner) findViewById(R.id.release_years);
		// Setting the spinners adapter to created adapter 'spinnerAdapter'
		viewSpinner.setAdapter(spinnerAdapter);
		// Setting
		viewSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// Setting Local Var Boolean 'mConnected' to the current
				// context's status
				mConnected = NetworkConn.grabConnectionStatus(context);
				// If connected...
				if (mConnected) {
					// Performing a search using performSearch method based off
					// current selected spinner position. Passes is data from
					// array (mReleaseYears) based on selection to allow user
					// manipulation of data.
					performSearch(mUrlString + mReleaseYears[position]);
					// If not connected...
				} else {
					// Setting the result's view text
					resultsView.setText("Network Error");
				}
			}

			// Method onNothingSelected...self explanatory method not utilized
			// at the moment.
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	// Private method to perform a search based on url string passed in
	private void performSearch(String item) {
		// Setting initialURL to item url passed in as argument
		String initialURL = item;
		// Initializing finalURL, type URL
		URL finalURL;
		try {
			// Setting finalURL to new URL with initialURL
			finalURL = new URL(initialURL);
			// Setting gd as new instance of grabData method
			grabData gd = new grabData();
			// Calling execute with finalURL passed in (which is initial item
			// url passed in)
			gd.execute(finalURL);
		} catch (MalformedURLException e) {
			// Logging Malformed URLException
			Log.e("INCORRECT URL", "CHECK URL PASSED");
			// Setting finalURL to null
			finalURL = null;
		}
	}

	// Private 'grabData' class extending from AsyncTask (asynchronous)
	private class grabData extends AsyncTask<URL, Void, String> {
		// onPreExecute Method - Setting TextView Cars to TextView IDS located
		// in xml
		protected void onPreExecute() {
			super.onPreExecute();
			// Clearing out the GameList (for reloads)
			gameList.clear();
			// Setting name,deck,and release textviews to ones found by Id in
			// resources
			name = (TextView) findViewById(R.id.name);
			deck = (TextView) findViewById(R.id.deck);
			release = (TextView) findViewById(R.id.release);
		}

		@Override
		protected String doInBackground(URL... urls) {
			// Resetting URL
			String fURL = "";
			for (URL url : urls) {
				// Setting fURL stringVar to response of getURLReponse method
				// from WebClass class.
				fURL = NetworkConn.getURLResponse(url);
			}
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

					// Setting list local var to created listView (onCreate)
					list = (ListView) findViewById(R.id.game_list);
					// Setting ListAdapter entitled adapter to a new
					// SimpleAdapter with data gameList, view Mainactivity and
					// utilizing defined custom list_view.xml, while displaying
					// game name and release.
					ListAdapter adapter = new SimpleAdapter(MainActivity.this,
							gameList, R.layout.list_view, new String[] {
									TAG_NAME, TAG_RELEASE }, new int[] {
									R.id.name, R.id.release });
					// Setting list's adapter to adapter created above
					list.setAdapter(adapter);
					// Setting list's item click listener
					list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						// Method to be called upon item click within list
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// Setting the resultsView background color to dark
							// gray (in case an error occurs, and it's currently
							// red)
							resultsView.setBackgroundColor(Color.DKGRAY);
							// Setting the resultView to display the current
							// position's (in list) item details based off
							// pulled in json data thats stored in gameList
							resultsView.setText("Game: "
									+ gameList.get(+position).get("name")
									+ "\n"
									+ "About Game: "
									+ gameList.get(+position).get("deck")
									+ "\n"
									+ "Release Year: "
									+ gameList.get(+position).get(
											"expected_release_year"));
						}
					});
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
