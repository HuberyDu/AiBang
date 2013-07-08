package edu.jxsd.x3510.bean;

public class PersonalMessage {
	private int userID;
	private int friendsID;
	private String personalContent;
	private String personalTime;
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getFriendsID() {
		return friendsID;
	}
	public void setFriendsID(int friendsID) {
		this.friendsID = friendsID;
	}
	public String getPersonalContent() {
		return personalContent;
	}
	public void setPersonalContent(String personalContent) {
		this.personalContent = personalContent;
	}
	public String getPersonalTime() {
		return personalTime;
	}
	public void setPersonalTime(String personalTime) {
		this.personalTime = personalTime;
	}
	
}
