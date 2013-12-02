/*
 * Jason Woolard
 * Java 1 - Project 1
 * Full Sail University
 * Term 1312
 */
package com.jasonwoolard.java1project1;

import com.jasonwoolard.java1project1.json.Json;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {
	// Local Variables
	LinearLayout ll;
	LinearLayout.LayoutParams llp;
	TextView tv;
	TextView tv2;
	TextView resultsView;
	Button resultsBtn;
	RadioGroup rg;
	RadioButton[] rb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Creating the Linear Layout (defining local variable ll)
		ll = new LinearLayout(this);
		// Setting the orientation of the Linear Layout to VERTICAL, as opposed to the opposite - HORIZONTAL
		ll.setOrientation(LinearLayout.VERTICAL);
		// Setting the value of LLP to Match the Parent View
		llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		// Setting the Linear Layouts parameters to the defined local variable 'llp'
		ll.setLayoutParams(llp);
		
		// Setting Local Variable 'tv'
		tv = new TextView(this);
		// Setting the Text for the created TextView
	    tv.setText("Upcoming Video Game Releases");
	    // Setting the TextView Object to the center of it's container
	    tv.setGravity(Gravity.CENTER);
	    // Setting the TextView's Text color to White
	    tv.setTextColor(Color.WHITE);
	    // Setting the TextView's Text Size to 20
	    tv.setTextSize(20);
	    // Setting the TextView's Background Color to dark gray
	    tv.setBackgroundColor(Color.DKGRAY);
	    // Setting the TextView's Text Color to White
	    tv.setTextColor(Color.WHITE);
	    // Adding the TextView to the Linear Layout
	    ll.addView(tv);
	    
	    // Setting Local Variable 'tv'
		tv2 = new TextView(this);
		// Setting the Text for the created TextView
		tv2.setText(R.string.chooseDate);
		// Setting the TextView Object to the center of it's container
		tv2.setGravity(Gravity.CENTER);
		// Setting the TextView's Text color to White
		tv2.setTextColor(Color.WHITE);
		// Setting the TextView's Text Size to 12
		tv2.setTextSize(12);
		// Setting the TextView's Background Color to dark gray
		tv2.setBackgroundColor(Color.DKGRAY);
		// Setting the TextView's Text Color to White
		tv2.setTextColor(Color.WHITE);
		// Adding the TextView to the Linear Layout
		ll.addView(tv2);
		
		// Calling createRadioGroup method to create and display the Radio Group (w/ radio buttons)
		// TODO: Replace RadioGroup idea with possibly a gridview or listview displaying recent video game release posts, as well as allowing users to filter through them as part of the 'request' portion of the assignment. 
		// TODO: Sticking with Radiogroup to check if enum data is properly being displayed...Time to attempt Grid or List View?
		createRadioGroup();
		
	    // Setting Local Variable 'resultsBtn'
	    resultsBtn = new Button(this);
	    // Setting the Button text to the predefined string value 'button_getResults' in 'values/strings.xml'
	    resultsBtn.setText(R.string.button_getResults);
	    // Setting the Buttons Text Size to 14
	    resultsBtn.setTextSize(14);
	    // Setting the Text Color for the Results Button
	    resultsBtn.setTextColor(Color.WHITE);
	    // Setting the Buttons Background Color to Dark Gray
	    resultsBtn.setBackgroundColor(Color.DKGRAY);
	    resultsBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Get Info from JSON Object
				int id = rg.getCheckedRadioButtonId();
				RadioButton rb = (RadioButton) findViewById(id);
				String selected = rb.getText().toString();
				resultsView.setText(Json.readJSON(selected));
				
			}
		});
	    // Adding the Results Button to the Linear Layout
	    ll.addView(resultsBtn);
	    
	    // Setting the Result View Local Variable
	    resultsView = new TextView(this);
	    resultsView.setLayoutParams(llp);
	    resultsView.setText("Make a selected and hit search to display results!");
	    resultsView.setGravity(Gravity.CENTER_HORIZONTAL);
	    ll.addView(resultsView);
	    
	    // Setting the content view as the created LinearLayout above
		setContentView(ll);
	}
	// Private Method to Create the Radio Group w/ Buttons for Video Game Release Years
	private void createRadioGroup() {
		// Setting the Local Variable 'rg' by creating the Radio Group
		rg = new RadioGroup(this);
		// Storing an array of Strings to be used for the Radio Buttons Text
		String[] releaseYears = new String[] { "GAME1", "GAME2", "GAME3", "GAME4", "GAME5" };
		// Creating an array of RadioButtons based on the amount of objects in the releaseYears array of strings
		rb = new RadioButton[releaseYears.length];
		// Setting the Radio Groups Orientation to Vertical as opposed to Horizontal
		rg.setOrientation(RadioGroup.VERTICAL);
		// Creating a For Loop to Cycle through & create each button / set properties based off releaseYears length and index.
		for (int i = 0; i < releaseYears.length; i++) {
			rb[i] = new RadioButton(this);
			// Setting the Layout Parameters for the Radio Buttons to Fill the Parent's Width
			LinearLayout.LayoutParams layoutParams = new LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT);
			// Adding the RadioButtons to the RadioGroup
			rg.addView(rb[i], layoutParams); 
			// Setting each Radio Button's text color to Black
			rb[i].setTextColor(Color.BLACK);
			// Setting each Radio Button's Text based off the index in the releaseYears array
			rb[i].setText(releaseYears[i]);
			// Setting each Radio Button's Text Size to 12
			rb[i].setTextSize(12);
		}
		// Checking the first Radio Button as default
		rb[0].setChecked(true);
		// Setting Light Gray Design Background to Alternate Radio Buttons
		rb[0].setBackgroundColor(Color.LTGRAY);
		rb[2].setBackgroundColor(Color.LTGRAY);
		// Adding the Radio Group (w/ buttons) to the Linear Layout
		ll.addView(rg);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
