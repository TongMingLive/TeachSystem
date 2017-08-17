package com.example.tong.teachsystem.Bean;

public class User {

	private int userId;
	private String userNumber;
	private String userPassword;
	private String userName;
	private int userType;
	private int signInType;
	private int signOutType;
	private int resultNum;

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getSignInType() {
		return signInType;
	}

	public void setSignInType(int signInType) {
		this.signInType = signInType;
	}

	public int getSignOutType() {
		return signOutType;
	}

	public void setSignOutType(int signOutType) {
		this.signOutType = signOutType;
	}

	public int getResultNum() {
		return resultNum;
	}

	public void setResultNum(int resultNum) {
		this.resultNum = resultNum;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
