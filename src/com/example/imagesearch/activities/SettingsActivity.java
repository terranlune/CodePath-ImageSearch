package com.example.imagesearch.activities;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.imagesearch.R;

public class SettingsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new SettingsFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class SettingsFragment extends Fragment {

		private Spinner spImgSize;
		private Spinner spImgColor;
		private Spinner spImgType;
		private EditText etSiteFilter;
		
		public SettingsFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_settings,
					container, false);
			
			spImgSize = (Spinner) rootView.findViewById(R.id.spImgSize);
			spImgColor = (Spinner) rootView.findViewById(R.id.spImgColor);
			spImgType = (Spinner) rootView.findViewById(R.id.spImgType);
			etSiteFilter = (EditText) rootView.findViewById(R.id.etSiteFilter);
			
			SharedPreferences prefs = getActivity().getSharedPreferences("com.example.imagesearch", MODE_PRIVATE);

			setSpinnerToValue(spImgSize, prefs.getString("imgsz", "all"));
			setSpinnerToValue(spImgColor, prefs.getString("imgcolor", "all"));
			setSpinnerToValue(spImgType, prefs.getString("imgtype", "all"));
			etSiteFilter.setText(prefs.getString("sitefilter", ""));
			
			return rootView;
		}
		
		public boolean save() {
			Editor editor = getActivity().getSharedPreferences("com.example.imagesearch", MODE_PRIVATE).edit();
			editor.putString("imgsz", spImgSize.getSelectedItem().toString());
			editor.putString("imgcolor", spImgColor.getSelectedItem().toString());
			editor.putString("imgtype", spImgType.getSelectedItem().toString());
			editor.putString("sitefilter", etSiteFilter.getText().toString());
			return editor.commit();
		}
		
		public void setSpinnerToValue(Spinner spinner, String value) {
		    int index = 0;
		    SpinnerAdapter adapter = spinner.getAdapter();
		    for (int i = 0; i < adapter.getCount(); i++) {
		        if (adapter.getItem(i).equals(value)) {
		            index = i;
		        }
		    }
		    spinner.setSelection(index);
		}
		
	}
	
	public void onClickSave(View view) {
		SettingsFragment f = (SettingsFragment) getSupportFragmentManager().findFragmentById(R.id.container);
		boolean success = f.save();
		if (success) {
			setResult(RESULT_OK);
		}else{
			setResult(RESULT_CANCELED);
		}
		finish();
	}

}
