package com.marakana.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class StatusActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			StatusFragment fragment = new StatusFragment();
			getFragmentManager()
					.beginTransaction()
					.add(android.R.id.content, fragment,
							fragment.getClass().getSimpleName()).commit();
		}
	}
	

}
