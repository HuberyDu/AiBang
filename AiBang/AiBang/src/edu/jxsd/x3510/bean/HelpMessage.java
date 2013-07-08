package edu.jxsd.x3510.bean;

import java.io.Serializable;

public class HelpMessage implements Serializable {
	
	private int helpMessageID;
	private int userID;
	private float x;
	private float y;
	private String loactionName;
	private String helpContent;
	private String helpTime;
	private String addressName;
	private int PhotoPotrait;
	private int photoContext;
	private String userName;
	private String comment;
	private String gift;
	public String getGift() {
		return gift;
	}
	public void setGift(String gift) {
		this.gift = gift;
	}
	public int getHelpMessageID() {
		return helpMessageID;
	}
	public void setHelpMessageID(int helpMessageID) {
		this.helpMessageID = helpMessageID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public String getLoactionName() {
		return loactionName;
	}
	public void setLoactionName(String loactionName) {
		this.loactionName = loactionName;
	}
	public String getHelpContent() {
		return helpContent;
	}
	public void setHelpContent(String helpContent) {
		this.helpContent = helpContent;
	}
	public String getHelpTime() {
		return helpTime;
	}
	public void setHelpTime(String helpTime) {
		this.helpTime = helpTime;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public int getPhotoPotrait() {
		return PhotoPotrait;
	}
	public void setPhotoPotrait(int photoPotrait) {
		PhotoPotrait = photoPotrait;
	}
	public int getPhotoContext() {
		return photoContext;
	}
	public void setPhotoContext(int photoContext) {
		this.photoContext = photoContext;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
