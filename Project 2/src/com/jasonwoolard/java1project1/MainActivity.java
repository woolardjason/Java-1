/*
 * Jason Woolard
 * Java 1 - Project 2
 * Full Sail University
 * Term 1312
 */

package com.jasonwoolard.java1project1;

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
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jasonwoolard.java1project1.web.WebClass;

public class MainActivity extends Activity {
	// Local Variables
	static String mTAG = "NETWORK ACTIVITY - MainActivity Class";
	public static String mUrlString = "http://www.giantbomb.com/api/games/?api_key=84bb1f7ad08b299e6c29992eff7ed6278f406a15&format=json&limit=5&sort=original_release_date:asc&filter=expected_release_year:";
	LinearLayout ll;
	LinearLayout.LayoutParams llp;
	TextView header;
	TextView subheader;
	TextView resultsLabel;
	static TextView resultsView;
	TextView filterLabel;
	Button searchBtn;
	EditText searchField;
	Context context;
	ListView list;
	TextView name;
	TextView deck;
	TextView release;
	Boolean mConnected = false;
	String[] mReleaseYears;

	// Node names from JSON Data
	private static final String TAG_PARENT = "results";
	private static final String TAG_NAME = "name";
	private static final String TAG_DECK = "deck";
	private static final String TAG_RELEASE = "expected_release_year";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Setting Local Var Context to this activity
		context = this;
		
		// Setting mReleaseYears Local Var to String Array from resources
		mReleaseYears = getResources().getStringArray(R.array.yearArray);
		// Creating the Linear Layout (defining local variable ll)
		ll = new LinearLayout(this);
		// Setting the orientation of the Linear Layout to VERTICAL, as opposed to the opposite - HORIZONTAL
		ll.setOrientation(LinearLayout.VERTICAL);
		// Setting the value of LLP to Match the Parent View
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		// Setting the Linear Layouts parameters to the defined local variable
		// 'llp'
		ll.setLayoutParams(llp);

		// Setting Local Variable 'header'
		header = new TextView(this);
		// Setting the Text for the created TextView
		header.setText(R.string.headerText);
		// Setting the TextView Object to the center of it's container
		header.setGravity(Gravity.CENTER);
		// Setting the TextView's Text color to White
		header.setTextColor(Color.WHITE);
		// Setting the TextView's Text Size to 16
		header.setTextSize(16);
		// Setting the TextView's Background Color to dark gray
		header.setBackgroundColor(Color.DKGRAY);
		// Setting the TextView's Text Color to White
		header.setTextColor(Color.WHITE);
		// Adding the TextView to the Linear Layout
		ll.addView(header);

		// Setting Local Variable 'filterLabel'
		filterLabel = new TextView(this);
		// Setting the Text for the created TextView
		filterLabel.setText(R.string.filterText);
		// Setting the TextView Object to the center of it's container
		filterLabel.setGravity(Gravity.CENTER);
		// Setting the Textview's Text Color to dark gray
		filterLabel.setTextColor(Color.DKGRAY);
		// Setting the Textview's Text Size to 12
		filterLabel.setTextSize(12);
		// Setting the Textview's background color to light gray
		filterLabel.setBackgroundColor(Color.LTGRAY);
		// Adding the TextView to the Linear Layout
		ll.addView(filterLabel);

		// Creating Spinner Adapter
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, mReleaseYears);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Creating the spinner
		Spinner viewSpinner = new Spinner(context);
		// Setting the spinners adapter to created adapter 'spinnerAdapter'
		viewSpinner.setAdapter(spinnerAdapter);
		// Setting
		viewSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// Setting Local Var Boolean 'mConnected' to the current context's status
				mConnected = WebClass.grabConnectionStatus(context);
				// If connected...
				if (mConnected) {
					// Performing a search using performSearch method based off current selected spinner position. Passes is data from array (mReleaseYears) based on selection to allow user manipulation of data.
					performSearch(mUrlString + mReleaseYears[position]);
				// If not connected...
				} else {
					// Setting the result's view text
					resultsView.setText(R.string.networkError);
				}
			}
			// Method onNothingSelected...self explanatory method not utilized at the moment.
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		// Adding viewSpinenr to the Linear Layout
		ll.addView(viewSpinner);

		// Setting Local Variable 'sub header'
		subheader = new TextView(this);
		// Setting the Text for the created TextView
		subheader.setText(R.string.chooseGame);
		// Setting the TextView Object to the center of it's container
		subheader.setGravity(Gravity.CENTER);
		// Setting the TextView's Text Size to 12
		subheader.setTextSize(12);
		// Setting the TextView's Background Color to Light Gray
		subheader.setBackgroundColor(Color.LTGRAY);
		// Setting the TextView's Text Color to Dark Gray
		subheader.setTextColor(Color.DKGRAY);
		// Adding the TextView to the Linear Layout
		ll.addView(subheader);

		// Create List View
		list = new ListView(this);
		// Setting Id of list to 15
		list.setId(15);
		// Adding the list to the Linear Layout
		ll.addView(list);

		// Setting the Results Text View Local Variable
		resultsLabel = new TextView(this);
		// Setting the Results Text View Properties (text, text color, text size, background color, and gravity)
		resultsLabel.setText(R.string.gameDetailText);
		resultsLabel.setTextColor(Color.DKGRAY);
		resultsLabel.setTextSize(12);
		resultsLabel.setBackgroundColor(Color.LTGRAY);
		resultsLabel.setGravity(Gravity.CENTER);
		// Adding the Results Text View to the Linear Layout
		ll.addView(resultsLabel);

		// Setting the Result View Local Variable
		resultsView = new TextView(this);

		// Setting the Results Text View Properties (text, text color, text size, background color, and gravity)
		resultsView.setBackgroundColor(Color.DKGRAY);
		resultsView.setTextColor(Color.WHITE);
		resultsView.setTextSize(10);
		resultsView.setGravity(Gravity.CENTER_HORIZONTAL);
		// Adding the Results Text View to the Linear Layout
		ll.addView(resultsView);

		// Setting the content view as the created LinearLayout above
		setContentView(ll);
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
			// Calling execute with finalURL passed in (which is initial item url passed in)
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
		// onPreExecute Method - Setting TextView Cars to TextView IDS located in xml
		protected void onPreExecute() {
			super.onPreExecute();
			// Clearing out the GameList (for reloads)
			gameList.clear();
			// Setting name,deck,and release textviews to ones found by Id in resources
			name = (TextView) findViewById(R.id.name);
			deck = (TextView) findViewById(R.id.deck);
			release = (TextView) findViewById(R.id.release);
		}

		@Override
		protected String doInBackground(URL... urls) {
			// Resetting URL
			String fURL = "";
			for (URL url : urls) {
				// Setting fURL stringVar to response of getURLReponse method from WebClass class.
				fURL = WebClass.getURLResponse(url);
			}
			// Returning fURL string var
			return fURL;
		}

		// OnPostExecute method
		protected void onPostExecute(String result) {
			try {
				// Setting json var for type JSONObject to new JSOBObject with passed in string result
				JSONObject json = new JSONObject(result);
				// Setting results JSONArray var to results json node
				JSONArray results = json.getJSONArray(TAG_PARENT);
				// Setting j int var to results array length
				int j = results.length();
				// For conditional cycling through results array grabbing defined nodes and storing them
				for (int i = 0; i < j; i++) {
					// Setting JSONObject var 'jo' to each object within results array
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
					list = (ListView) findViewById(15);
					// Setting ListAdapter entitled adapter to a new SimpleAdapter with data gameList, view Mainactivity and utilizing defined custom list_view.xml, while displaying  game name and release.
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
							// Setting the resultsView background color to dark gray (in case an error occurs, and it's currently red)
							resultsView.setBackgroundColor(Color.DKGRAY);
							// Setting the resultView to display the current position's (in list) item details based off pulled in json data thats stored in gameList
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
