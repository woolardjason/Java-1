/*
 * Jason Woolard
 * Java 1 - Project 1
 * Full Sail University
 * Term 1312
 */
package com.jasonwoolard.java1project1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
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
	Button resultsBtn;
	RadioGroup rg;
	RadioButton rb1;
	RadioButton rb2;
	RadioButton rb3;
	
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
		
		// Setting Local Variable 'rg'
		rg = new RadioGroup(this);
		// Adding the RadioGroup 'rg' to the Linear Layout View
		ll.addView(rg);
		
		// Defining the Linear Layout Parameters for each Radio Button
		LinearLayout.LayoutParams layoutParams = new LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT);
		
		// Creating Radio Buttons to add to Radio Group
		rb1 = new RadioButton(this);
		// Setting the Text Size of rb1
		rb1.setTextSize(12);
		// Setting the Text of rb1
		rb1.setText("2013");
		// Adding rb1 button to the Radio Group with index of 0 && utilizing defined layoutParameters
		rg.addView(rb1, 0, layoutParams);
		
		// Creating Radio Buttons to add to Radio Group
		rb2 = new RadioButton(this);
		// Setting the Text Size of rb1
		rb2.setTextSize(12);
		// Setting the Text of rb2
		rb2.setText("2014");		
		// Adding rb2 button to the Radio Group with index of 0 && utilizing defined layoutParameters
		rg.addView(rb2, 1, layoutParams);
		
		// Creating Radio Buttons to add to Radio Group
		rb3= new RadioButton(this);
		// Setting the Text Size of rb1
		rb3.setTextSize(12);
		// Setting the Text of rb3
		rb3.setText("2015");
		// Adding rb3 button to the Radio Group with index of 0 && utilizing defined layoutParameters
		rg.addView(rb3, 2, layoutParams);
		
	    // Setting Local Variable 'resultsBtn'
	    resultsBtn = new Button(this);
	    // Setting the Button text to the predefined string value 'button_getResults' in 'values/strings.xml'
	    resultsBtn.setText(R.string.button_getResults);
	    // Setting the Buttons Text Size to 14
	    resultsBtn.setTextSize(14);
	    // Setting the Buttons Background Color to Light Gray
	    resultsBtn.setBackgroundColor(Color.LTGRAY);
	    // Adding the Results Button to the Linear Layout
	    ll.addView(resultsBtn);
	    
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
