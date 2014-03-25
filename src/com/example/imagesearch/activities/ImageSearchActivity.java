package com.example.imagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.imagesearch.R;
import com.example.imagesearch.fragments.ImageSearchFragment;

public class ImageSearchActivity extends ActionBarActivity {

	private static final int UPDATE_SETTINGS = 0;
	private ImageSearchFragment imageSearchFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		
		if (savedInstanceState == null) {
			imageSearchFragment = new ImageSearchFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.imageSearchFragmentContainer, imageSearchFragment).commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivityForResult(intent, UPDATE_SETTINGS);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == UPDATE_SETTINGS) {
			if (resultCode == RESULT_OK) {
				String query = imageSearchFragment.getSearchQuery();
				imageSearchFragment.search(query);
			}
		}
	}
	
	public void onClickSearch(View view) {
		String query = imageSearchFragment.getSearchQuery();
		imageSearchFragment.search(query);
	}

}
