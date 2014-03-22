package com.example.imagesearch.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GimageSearch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2759324183199396481L;
	private String url;
	private String tbUrl;
	private String content;
	
	public GimageSearch(JSONObject json) {
		try {
			this.url = json.getString("url");
			this.tbUrl = json.getString("tbUrl");
			this.content = json.getString("contentNoFormatting");
		} catch (JSONException e) {
			this.url = null;
			this.tbUrl = null;
			this.content = null;
		}
	}
	
	public String getUrl() {
		return url;
	}

	public String getTbUrl() {
		return tbUrl;
	}
	
	public String getContent() {
		return content;
	}

	public static ArrayList<GimageSearch> fromJSONArray(
			JSONArray jsonResults) {
		
		ArrayList<GimageSearch> results = new ArrayList<GimageSearch>();
		for (int i=0; i<jsonResults.length(); ++i) {
			try {
				results.add(new GimageSearch(jsonResults.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return results;
	}

	public String toString() {
		return tbUrl;
	}
	
}
