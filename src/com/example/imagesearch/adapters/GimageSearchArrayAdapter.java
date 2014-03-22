package com.example.imagesearch.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imagesearch.R;
import com.example.imagesearch.models.GimageSearch;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GimageSearchArrayAdapter extends ArrayAdapter<GimageSearch> {

    private static class ViewLookupCache {
        ImageView ivThumb;
        TextView tvContent;
    }
    
	public GimageSearchArrayAdapter(Context context, List<GimageSearch> images) {
		super(context, R.layout.item_gimagesearch, images);
	}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
       // Get the data item for this position
       GimageSearch result = getItem(position);   
       
       // Check if an existing view is being reused, otherwise inflate the view
       ViewLookupCache viewLookupCache; // view lookup cache stored in tag
       if (convertView == null) {
          viewLookupCache = new ViewLookupCache();
          LayoutInflater inflater = LayoutInflater.from(getContext());
          convertView = inflater.inflate(R.layout.item_gimagesearch, null);
          viewLookupCache.ivThumb = (ImageView) convertView.findViewById(R.id.ivThumb);
          viewLookupCache.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
          convertView.setTag(viewLookupCache);
       } else {
           viewLookupCache = (ViewLookupCache) convertView.getTag();
       }
       
       ImageLoader.getInstance().displayImage(result.getTbUrl(), viewLookupCache.ivThumb);
       viewLookupCache.tvContent.setText(result.getContent());
       
       // Return the completed view to render on screen
       return convertView;
   }
	
}
