/*
 * Jason Woolard
 * Java 1 - Project 1
 * Full Sail University
 * Term 1312
 */

package com.jasonwoolard.java1project1;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jasonwoolard.java1project1.json.Json;

public class MainActivity extends Activity {
	// Local Variables
	LinearLayout ll;
	LinearLayout.LayoutParams llp;
	TextView header;
	TextView subheader;
	TextView resultsLabel;
	TextView resultsView;
	String[] gameList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Setting 're' variable to return the resources for apps package
		Resources re = getResources();
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

		// Setting Local Variable 'tv'
		header = new TextView(this);
		// Setting the Text for the created TextView
		header.setText(R.string.headerText);
		// Setting the TextView Object to the center of it's container
		header.setGravity(Gravity.CENTER);
		// Setting the TextView's Text color to White
		header.setTextColor(Color.WHITE);
		// Setting the TextView's Text Size to 20
		header.setTextSize(20);
		// Setting the TextView's Background Color to dark gray
		header.setBackgroundColor(Color.DKGRAY);
		// Setting the TextView's Text Color to White
		header.setTextColor(Color.WHITE);
		// Adding the TextView to the Linear Layout
		ll.addView(header);

		// Setting Local Variable 'tv'
		subheader = new TextView(this);
		// Setting the Text for the created TextView
		subheader.setText(R.string.chooseGame);
		// Setting the TextView Object to the center of it's container
		subheader.setGravity(Gravity.CENTER);
		// Setting the TextView's Text color to White
		subheader.setTextColor(Color.WHITE);
		// Setting the TextView's Text Size to 12
		subheader.setTextSize(12);
		// Setting the TextView's Background Color to dark gray
		subheader.setBackgroundColor(Color.DKGRAY);
		// Setting the TextView's Text Color to White
		subheader.setTextColor(Color.WHITE);
		// Adding the TextView to the Linear Layout
		ll.addView(subheader);

		gameList = re.getStringArray(R.array.gameList);
		
		// Create List Adapter
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, gameList);

		// Create List View
		ListView listView = new ListView(this);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Setting the selected string to the clicked gameList object within the string array (then calling toString method to pass in below)
				String selected = gameList[position].toString();
				// Setting the resultsView (textview) to the passed in selected 'enum'
				resultsView.setText(Json.readJSON(selected));
			}
		});
		ll.addView(listView);

		// Setting the Results Label View Local Variable
		resultsLabel = new TextView(this);
		resultsLabel.setText(R.string.gameDetailText);
		resultsLabel.setTextColor(Color.WHITE);
		resultsLabel.setBackgroundColor(Color.DKGRAY);
		resultsLabel.setGravity(Gravity.CENTER);
		ll.addView(resultsLabel);

		// Setting the Result View Local Variable
		resultsView = new TextView(this);
		resultsView.setText(R.string.gameDetailHint);
		resultsView.setBackgroundColor(Color.LTGRAY);
		resultsView.setTextColor(Color.WHITE);
		resultsView.setGravity(Gravity.CENTER_HORIZONTAL);
		ll.addView(resultsView);

		// Setting the content view as the created LinearLayout above
		setContentView(ll);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
