package org.fatmansoft.teach.models;

public class UserUpdateRequest {
    private String name;
    private String username;
    private String university;
    private String gender;
    private String major;


    public void setName(String name) {
        this.name = name;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getMajor() {
        return major;
    }

    public String getUniversity() {
        return university;
    }

    public String getUsername() {
        return username;
    }

}

