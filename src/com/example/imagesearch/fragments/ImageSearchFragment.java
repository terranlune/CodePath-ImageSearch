package com.example.imagesearch.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.imagesearch.R;
import com.example.imagesearch.activities.ImageDisplayActivity;
import com.example.imagesearch.adapters.GimageSearchArrayAdapter;
import com.example.imagesearch.helpers.EndlessScrollListener;
import com.example.imagesearch.models.GimageSearch;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * A placeholder fragment containing a simple view.
 */
public class ImageSearchFragment extends Fragment {

	private EditText etQuery;
	private Button btSearch;
	private GridView gvResults;
	private ArrayList<GimageSearch> searchResults = new ArrayList<GimageSearch>();
	private GimageSearchArrayAdapter resultsAdapter;
	private String searchQuery;

	public ImageSearchFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_image_search,
				container, false);

		etQuery = (EditText) rootView.findViewById(R.id.etQuery);
		btSearch = (Button) rootView.findViewById(R.id.btSearch);
		gvResults = (GridView) rootView.findViewById(R.id.gvResults);
		
		resultsAdapter = new GimageSearchArrayAdapter(getActivity(), searchResults);
		gvResults.setAdapter(resultsAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), ImageDisplayActivity.class);
				GimageSearch item = searchResults.get(position);
				intent.putExtra("image", item);
				startActivity(intent);
			}});
		gvResults.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				addPage(page);
			}});

		return rootView;
	}

	public String getSearchQuery() {
		return etQuery.getText().toString();
	}

	
	public void search(String query) {
		Toast.makeText(getActivity(), "searching for " + query, Toast.LENGTH_SHORT)
				.show();
		searchQuery = query;
		resultsAdapter.clear();
		addPage(0);
	}
	
	public void addPage(int page) {
		
		if (searchQuery == null) {
			return;
		}
		
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("https").authority("ajax.googleapis.com");
		builder.appendEncodedPath("ajax/services/search/images");
		builder.appendQueryParameter("v", "1.0");
		builder.appendQueryParameter("q", searchQuery);
		builder.appendQueryParameter("rsz", "8"); // Results per page
		builder.appendQueryParameter("start", Integer.toString(page * 8));

		Log.e("query", builder.build().toString());

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(builder.build().toString(), new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {

				JSONArray jsonResults;
				try {
					jsonResults = response.getJSONObject("responseData")
							.getJSONArray("results");
					
					resultsAdapter.addAll(GimageSearch
							.fromJSONArray(jsonResults));
					Log.e("query","added " + jsonResults.length() + " results");

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

}