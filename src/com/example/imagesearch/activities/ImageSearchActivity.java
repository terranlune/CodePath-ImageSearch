package com.example.imagesearch.activities;

import java.util.List;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.imagesearch.R;
import com.example.imagesearch.fragments.ImageSearchFragment;

public class ImageSearchActivity extends FragmentActivity {

	private ImageSearchFragment imageSearchFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);

		if (savedInstanceState == null) {
			imageSearchFragment = new ImageSearchFragment();
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.container, imageSearchFragment)
					.commit();
		}

		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		SearchableInfo info = searchManager
				.getSearchableInfo(getComponentName());
		searchView.setSearchableInfo(info);
		searchView.setIconifiedByDefault(false);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void handleIntent(Intent intent) {

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			
			String query = intent.getStringExtra(SearchManager.QUERY);
			
			ImageSearchFragment isf = (ImageSearchFragment) getSupportFragmentManager().findFragmentById(R.id.container);
			FragmentManager manager = getSupportFragmentManager();
			Log.e("blah", "fragment manager: " + manager.toString());
			List<Fragment> fl = manager.getFragments();
			Log.e("blah", "fragment list: " + fl.toString());
			for (Fragment f : fl) {
				Log.e("blah", "Found fragment: " + f.toString());
			}
			isf.search(query);
			
//			String query = intent.getStringExtra(SearchManager.QUERY);
//			imageSearchFragment.search(query);
		}
	}

	public void onClickSearch(View view) {
		// TODO: Fix occasional null pointer error here
		String query = imageSearchFragment.getSearchQuery();
		imageSearchFragment.search(query);
	}

}
