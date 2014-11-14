package com.bharatl.instagrampopularphotos;

import java.util.List;
import java.util.zip.Inflater;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InstagramAdapter extends ArrayAdapter<PopularPhotosModel> {
	
	private static String address;
	private String map = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";
	

	public InstagramAdapter(Context context, List<PopularPhotosModel> photos) {
		
		super(context, R.layout.photo_layout, photos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PopularPhotosModel photo = getItem(position);
		
		if (convertView == null){
			
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.photo_layout, parent, false);
		}	
		String mapUri = null;
		if (photo.getLat() != null && photo.getLng() != null ){
		mapUri = map+photo.getLat()+","+photo.getLng()+"&sensor=true";
		getLatLong(mapUri, convertView);
		}
		

		TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
		tvAddress.setText(InstagramAdapter.address);
		TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
		tvUsername.setText(photo.getUsername());
		ImageView imgProfile = (ImageView) convertView.findViewById(R.id.imgProfile);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
		TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
		tvCaption.setText(photo.getCaption());
		tvLikes.setText(String.valueOf(photo.getLikeCounts())+ " Likes");
		imgPhoto.getLayoutParams().height = photo.getHeight();
		
		
		imgPhoto.setImageResource(0);
		
		Picasso.with(getContext()).load(photo.getImage()).into(imgPhoto);
		Picasso.with(getContext()).load(photo.getProfilePic()).transform(new InstagramCircularImage()).into(imgProfile);
		
		
		return convertView;

	
	}

	private void getLatLong(String mapUri, View convertView) {
		

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(mapUri, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				
				try {
					JSONArray resp = response.getJSONArray("results");
					
					InstagramAdapter.address = resp.getJSONObject(0).getString("formatted_address");
				} catch (JSONException e) {
	
					e.printStackTrace();
				}
				
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
			
			
		});
	
		
	}
	

}
