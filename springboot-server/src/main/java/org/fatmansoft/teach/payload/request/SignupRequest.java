package org.fatmansoft.teach.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

public class SignupRequest {
    @NotBlank
    @Size(min = 1, max = 20)
    private String username;
    private  String role;
//    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    private String realName;
    @NotBlank
    private String gender;
    @NotBlank
    private String university;
    @NotBlank
    private  String phone;
    @NotBlank
    private String major;

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getUniversity() {
        return university;
    }

    public String getMajor() {
        return major;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getRealName() {
        return realName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Set<String> getRole() {
//        return this.role;
//    }

//    public void setRole(Set<String> role) {
//        this.role = role;
//    }
}
