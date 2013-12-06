/*
 * Jason Woolard
 * Java 1 - Project 1
 * Full Sail University
 * Term 1312
 */

package com.jasonwoolard.java1project1;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonwoolard.java1project1.json.Json;

public class MainActivity extends Activity {
	// Local Variables
	LinearLayout ll;
	LinearLayout.LayoutParams llp;
	TextView header;
	TextView subheader;
	TextView resultsLabel;
	TextView resultsView;
	TextView filterLabel;
	String[] gameList;
	Button searchBtn;
	EditText searchField;
	
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
		// Setting the Textview's Text Size to 14
		filterLabel.setTextSize(14);
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
					// Displaying a Toast to inform users of the video filtering being available in project 2 when data's being pulled from the api
					Toast.makeText(MainActivity.this, R.string.toastMsg, Toast.LENGTH_LONG).show();
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
		
		// Setting the String Array 'gameList' to the gameList string array located in resources
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
				// Resetting resultsView background color, in case it was changed during an error from onClickEvent for Search btn.
				resultsView.setBackgroundColor(Color.DKGRAY);
				// Setting the selected string to the clicked gameList object within the string array (then calling toString method to pass in below)
				String selected = gameList[position].toString();
				// Setting the resultsView (textview) to the passed in selected 'enum'
				resultsView.setText(Json.readJSON(selected));
			}
		});
		ll.addView(listView);

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
		resultsView.setText(R.string.gameDetailHint);
		resultsView.setBackgroundColor(Color.DKGRAY);
		resultsView.setTextColor(Color.WHITE);
		resultsView.setTextSize(10);
		resultsView.setGravity(Gravity.CENTER_HORIZONTAL);
		// Adding the Results Text View to the Linear Layout
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
