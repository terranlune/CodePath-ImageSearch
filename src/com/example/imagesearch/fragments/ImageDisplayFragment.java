package com.example.imagesearch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imagesearch.R;
import com.example.imagesearch.models.GimageSearch;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A placeholder fragment containing a simple view.
 */
public class ImageDisplayFragment extends Fragment {

	private GimageSearch gis;

	public ImageDisplayFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		gis = (GimageSearch) getArguments().getSerializable("image");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_image_display,
				container, false);

		ImageView ivImage = (ImageView) rootView.findViewById(R.id.ivResult);
		ImageLoader.getInstance().displayImage(gis.getUrl(), ivImage);
		TextView tvContent = (TextView) rootView.findViewById(R.id.tvContent);
		tvContent.setText(gis.getContent());

		return rootView;
	}
	
	public static ImageDisplayFragment newInstance(GimageSearch gis) {
		ImageDisplayFragment fragment = new ImageDisplayFragment();
		Bundle args = new Bundle();
		args.putSerializable("image", gis);
		fragment.setArguments(args);
		return fragment;
	}
}