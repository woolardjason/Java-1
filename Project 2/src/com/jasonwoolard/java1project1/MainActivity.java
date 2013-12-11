/*
 * Jason Woolard
 * Java 1 - Project 1
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

import com.jasonwoolard.java1project1.web.WebClass;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity 
{
	// Local Variables
	static String mTAG = "NETWORK ACTIVITY - MainActivity Class";
	public static String mUrlString = "http://www.giantbomb.com/api/games/?api_key=84bb1f7ad08b299e6c29992eff7ed6278f406a15&filter=expected_release_year:2014&format=json&limit=5&sort=original_release_date:asc";
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

	// Node names from JSON Data
	private static final String TAG_PARENT = "results";
	private static final String TAG_NAME = "name";
	private static final String TAG_DECK = "deck";
	private static final String TAG_RELEASE = "expected_release_year";
	
	ArrayList<HashMap<String, String>> gameList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
        gameList = new ArrayList<HashMap<String, String>>();

		// Setting 're' variable to return the resources for apps package
		// Creating the Linear Layout (defining local variable ll)
		ll = new LinearLayout(this);
		// Setting the orientation of the Linear Layout to VERTICAL, as opposed to the opposite - HORIZONTAL
		ll.setOrientation(LinearLayout.VERTICAL);
		// Setting the value of LLP to Match the Parent View
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		// Setting the Linear Layouts parameters to the defined local variable 'llp'
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

		// Setting Local Variable 'searchField'
		searchField = new EditText(this);
		// Setting PlaceHolder Text for searchField
		searchField.setHint(R.string.searchFieldHint);
		// Setting Max Lines to 1 
		searchField.setSingleLine(true);
		// Setting Text Size of searchField
		searchField.setTextSize(12);
		// Adding the EditText field to the Linear Layout
		ll.addView(searchField);
		
		// Setting Local Variable 'searchBtn'
		searchBtn = new Button(this);
		// Setting the Text for the button
		searchBtn.setText(R.string.searchBtn);
		// Setting the Text Size for the button
		searchBtn.setTextSize(12);
		// Setting the onClickListener (methods called when clicked) for the searchBtn
		searchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// If conditional checking length of the searchField to ensure user has typed in text before conducting what would be a search in project 2.
				int z = 0;
				if (searchField.length() == z)
				{
					// Setting resultsView text to predefined string & background color to red
					resultsView.setBackgroundColor(Color.RED);
					resultsView.setText(R.string.searchError);
				}
				else
				{
					mConnected = WebClass.grabConnectionStatus(context);
					if (mConnected)
					{
						performSearch("http://www.giantbomb.com/api/games/?api_key=84bb1f7ad08b299e6c29992eff7ed6278f406a15&format=json&limit=5&sort=original_release_date:asc&filter=expected_release_year:2014|2100,name:"+searchField.getText().toString());
					}
					else
					{
						resultsView.setText("You are not connected to the internet, please check your connection and try again.");
					}
				}
				// Utilizing InputMethodManager to Dismiss the android keyboard
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
			}
		});
		// Adding the Button to the Linear Layout
		ll.addView(searchBtn);
		
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
		list.setId(15);
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
		// Checking connection status by calling the method with context as argument.
		mConnected = WebClass.grabConnectionStatus(context);
		if (mConnected)
		{
			performSearch(mUrlString);
		}
		else
		{
			resultsView.setText("You are not connected to the internet, please check your connection and try again.");
		}
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
	private void performSearch(String item){
		
		String initialURL = item;
		
		URL finalURL;
		try{
			finalURL = new URL(initialURL);
			grabData gd = new grabData();
			gd.execute(finalURL);
		} catch (MalformedURLException e){
			Log.e("INCORRECT URL", "CHECK URL PASSED");
			finalURL = null;
		}
	}
	// Private 'grabData' class extending from AsyncTask (asynchronous)
	private class grabData extends AsyncTask<URL, Void, String>
	{
		//onPreExecute Method - Setting TextView Cars to TextView IDS located in xml
		protected void onPreExecute() 
		{
			super.onPreExecute();
			// Clearing out the GameList
			gameList.clear();
	        name = (TextView)findViewById(R.id.name);
	        deck = (TextView)findViewById(R.id.deck);
	        release = (TextView)findViewById(R.id.release);  
	    }
		@Override
		protected String doInBackground(URL... urls) {
			// Resetting URL
			String fURL = "";
			for (URL url: urls)
			{
				fURL = WebClass.getURLResponse(url);
			}
			return fURL;
		}
		// OnPostExecute method
		protected void onPostExecute(String result)
		{
			try {
				JSONObject json = new JSONObject(result);
				JSONArray results = json.getJSONArray(TAG_PARENT);
				
				int j = results.length();
				for(int i=0; i<j; i++){
    				JSONObject jo = results.getJSONObject(i);
    				
    				// Storing  JSON Nodes into separate strings
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
    				// Setting list local var to created listView (oncreate)
    				list=(ListView)findViewById(15);
    				
    				ListAdapter adapter = new SimpleAdapter(MainActivity.this, gameList,
    						R.layout.list_view,
    						new String[] { TAG_NAME, TAG_RELEASE }, new int[] {
    								R.id.name, R.id.release});

    				list.setAdapter(adapter);
    				list.setOnItemClickListener(new AdapterView.OnItemClickListener() 
    				{
    					@Override
    		            public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
    		            {
    						resultsView.setBackgroundColor(Color.DKGRAY);
    		                resultsView.setText("Game: " + gameList.get(+position).get("name") + "\n" +
    		                					"About Game: " + gameList.get(+position).get("deck") + "\n" +
    		                					"Release Year: " + gameList.get(+position).get("expected_release_year"));
    		            }
    		       });
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Temporarily setting the resultsView to JSON data returned from 'result'.
			resultsView.setText(result);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
