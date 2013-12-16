package com.example.java1week4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	// Local Variables
	Context context;
	String mTAG = "NETWORK ACTIVITY - MainActivity Class";
	String mUrlString = "http://www.giantbomb.com/api/games/?api_key=84bb1f7ad08b299e6c29992eff7ed6278f406a15&format=json&limit=5&sort=original_release_date:asc&filter=expected_release_year:";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_form);
		// Setting context local var to this activity.
		context = this;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
