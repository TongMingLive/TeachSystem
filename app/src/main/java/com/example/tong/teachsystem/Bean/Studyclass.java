package com.example.tong.teachsystem.Bean;

import java.io.Serializable;

/**
 * Created by tong- on 2017/4/30.
 */
public class Studyclass implements Serializable{
    private int classId;
    private String className;
    private String classTime;
    private int userId;
    private String userName;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
