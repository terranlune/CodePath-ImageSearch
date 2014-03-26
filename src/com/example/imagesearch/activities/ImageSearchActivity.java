package com.example.imagesearch.activities;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.imagesearch.R;
import com.example.imagesearch.fragments.IntroFragment;
import com.example.imagesearch.fragments.SearchResultsFragment;

public class ImageSearchActivity extends FragmentActivity {
	private static final int UPDATE_SETTINGS = 0;
	private boolean redoSearch = false;
	private Intent lastIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);

		if (savedInstanceState == null) {
			handleIntent(getIntent());
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putParcelable("lastIntent", lastIntent);
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		lastIntent = savedInstanceState.getParcelable("lastIntent");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (redoSearch) {
			handleIntent(lastIntent);
			redoSearch = false;
		}
	}
	
	@Override
	public void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		MenuItem menuItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) menuItem.getActionView();
		SearchableInfo info = searchManager
				.getSearchableInfo(getComponentName());
		searchView.setSearchableInfo(info);

		// Start up with the search bar expanded
		MenuItemCompat.expandActionView(menuItem);
		searchView.setIconified(false);

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

	private void handleIntent(Intent intent) {

		lastIntent = intent;

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			SearchResultsFragment f = SearchResultsFragment.newInstance(query);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, f).commit();
		} else {
			IntroFragment f = new IntroFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, f).commit();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == UPDATE_SETTINGS) {
			if (resultCode == RESULT_OK) {
				redoSearch  = true;
			}
		}
	}

}
