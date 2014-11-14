package com.bharatl.instagrampopularphotos;

public class PopularPhotosModel {
	
	private String Caption;
	private String Image;
	private String Username;
	private int likeCounts;
	private int height;
	private String profilePic;
	private String lat;
	private String lng;

	public String getCaption() {
		return Caption;
	}
	public void setCaption(String caption) {
		Caption = caption;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public int getLikeCounts() {
		return likeCounts;
	}
	public void setLikeCounts(int likeCounts) {
		this.likeCounts = likeCounts;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	
}
