package com.bharatl.instagrampopularphotos;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PopularActivity extends Activity {
	public final static String CLIENT_ID = "4198b161dafc4f43bb5fd4b70c3194e1";
	private ArrayList<PopularPhotosModel> photosArray;
	private InstagramAdapter aPhotos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popular);
		RenederPopularPhotos();
	}

	private void RenederPopularPhotos() {
		//End point https://api.instagram.com/v1/media/popular?client_id=
		String url = "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;
		photosArray = new ArrayList<PopularPhotosModel>();
		aPhotos = new InstagramAdapter(this, photosArray);
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(aPhotos);
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				
				
				JSONArray respArray = null;
				try {
					photosArray.clear();
					respArray = response.getJSONArray("data");
				 
				for (int i =0 ; i< respArray.length(); i++){
					JSONObject respJson = respArray.getJSONObject(i);
					PopularPhotosModel popular = new PopularPhotosModel();
					popular.setImage(respJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
					if (!respJson.isNull("caption")) {
					popular.setCaption(respJson.getJSONObject("caption").getString("text"));
					}
					popular.setUsername(respJson.getJSONObject("user").getString("username"));
					popular.setLikeCounts(respJson.getJSONObject("likes").getInt("count"));
					popular.setProfilePic(respJson.getJSONObject("user").getString("profile_picture"));
					popular.setHeight(respJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
					if(!respJson.isNull("location") && !respJson.getJSONObject("location").isNull("latitude") && !respJson.getJSONObject("location").isNull("longitude")){
					popular.setLat(respJson.getJSONObject("location").getString("latitude"));
					popular.setLng(respJson.getJSONObject("location").getString("longitude"));
					}
					photosArray.add(popular);

					}
				aPhotos.notifyDataSetChanged();
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
			
			
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.popular, menu);
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
