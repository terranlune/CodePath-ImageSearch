package com.example.imagesearch.fragments;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
	private ImageView ivImage;

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

		ivImage = (ImageView) rootView.findViewById(R.id.ivResult);
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
	
	public Uri getImageBitmapUri() {
		return getLocalBitmapUri(ivImage);
	}
	
	public Uri getLocalBitmapUri(ImageView imageView) {
	    Bitmap bitmap = getImageBitmap(imageView);
	    // Write image to default external storage directory   
	    Uri bmpUri = null;
	    try {
	        File file =  new File(Environment.getExternalStoragePublicDirectory(  
	            Environment.DIRECTORY_DOWNLOADS), "share_image.png");  
	        FileOutputStream out = new FileOutputStream(file);
	        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
	        out.close();
	        bmpUri = Uri.fromFile(file);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return bmpUri;
	}

	public Bitmap getImageBitmap(ImageView imageView) {
	    Drawable drawable = imageView.getDrawable();
	    Bitmap bmp;
	    if (drawable instanceof BitmapDrawable){
	        bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
	    } else { // workaround to convert color to bitmap
	        bmp = Bitmap.createBitmap(drawable.getBounds().width(), 
	            drawable.getBounds().height(), Config.ARGB_8888);
	        Canvas canvas = new Canvas(bmp); 
	        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	        drawable.draw(canvas);
	    }
	    return bmp;
	}
}