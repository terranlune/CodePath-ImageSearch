package com.example.imagesearch.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.imagesearch.R;
import com.example.imagesearch.fragments.ImageDisplayFragment;
import com.example.imagesearch.models.GimageSearch;

public class ImageDisplayActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);

		if (savedInstanceState == null) {
			GimageSearch gis = (GimageSearch) getIntent().getSerializableExtra("image");
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, ImageDisplayFragment.newInstance(gis)).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
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

}
