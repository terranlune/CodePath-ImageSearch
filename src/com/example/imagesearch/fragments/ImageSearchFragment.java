package com.example.imagesearch.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

	private GridView gvResults;
	private ArrayList<GimageSearch> searchResults = new ArrayList<GimageSearch>();
	private GimageSearchArrayAdapter resultsAdapter;
	private String searchQuery;

	public ImageSearchFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.e("blah", "ImageSearchFragment onCreate " + this.toString());
		resultsAdapter = new GimageSearchArrayAdapter(getActivity(),
				searchResults);

		Bundle args = getArguments();
		if (args != null) {
			searchQuery = args.getString("query");
			search(searchQuery);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_image_search,
				container, false);

		gvResults = (GridView) rootView.findViewById(R.id.gvResults);

		gvResults.setAdapter(resultsAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),
						ImageDisplayActivity.class);
				GimageSearch item = searchResults.get(position);
				intent.putExtra("image", item);
				startActivity(intent);
			}
		});
		gvResults.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				addPage(page);
			}
		});

		return rootView;
	}

	public void search(String query) {
		Log.e("blah", "Searching for " + query + " from " + this.toString());
		Toast.makeText(getActivity(), "searching for " + query,
				Toast.LENGTH_SHORT).show();
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

		SharedPreferences prefs = getActivity().getSharedPreferences("com.example.imagesearch", Context.MODE_PRIVATE);
		String imgsz = prefs.getString("imgsz", "all");
		String imgcolor = prefs.getString("imgcolor", "all");
		String imgtype = prefs.getString("imgtype", "all");
		String sitefilter = prefs.getString("sitefilter", "");
		
		if (!imgsz.equals("all")) {
			builder.appendQueryParameter("imgsz", imgsz);
		}
		if (!imgcolor.equals("all")) {
			builder.appendQueryParameter("imgcolor", imgcolor);
		}
		if (!imgtype.equals("all")) {
			builder.appendQueryParameter("imgtype", imgtype);
		}
		if (!sitefilter.equals("")) {
			builder.appendQueryParameter("as_sitesearch", sitefilter);
		}
		

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
					Log.e("query", "added " + jsonResults.length() + " results");

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static ImageSearchFragment newInstance(String query) {
		ImageSearchFragment fragment = new ImageSearchFragment();
		Bundle args = new Bundle();
		args.putString("query", query);
		fragment.setArguments(args);
		return fragment;
	}
}