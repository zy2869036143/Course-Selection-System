package org.fatmansoft.teach.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
//@Table(	name = "S202000300021_User",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = "userName"),
//                @UniqueConstraint(columnNames = "personId"),
//        })
public class User {

    @NotBlank
    private String realName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotBlank
    @Size(max = 20)
    private String userName;

    @NotBlank
    private String gender;

    @ManyToOne()
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @ManyToOne
    @JoinColumn(name="personId")
    private Person person;

    @NotBlank
    @Size(max = 60)
    private String password;

    private Integer loginCount;
    private Date lastLoginTime;


    @NotBlank
    private  String university;
    @NotBlank
    private  String phone;
    @NotBlank
    private  String major;


    private int score;

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getMajor() {
        return major;
    }

    public String getUniversity() {
        return university;
    }


    public User() {
    }

    public User(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}

//@Entity
//@Table(	name = "user",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = "userName")
//        })
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer userId;
//    @NotBlank
//    private String realName;
//    @NotBlank
//    private String userName;
//    @NotBlank
//    private String gender;
//
//    @ManyToOne()
//    @JoinColumn(name = "user_type_id")
//    private UserType userType;
//
//    @ManyToOne
//    @JoinColumn(name="personId")
//    private Person person;
//    @NotBlank
//    private String university;
//    @NotBlank
//    private String phone;
//    @NotBlank
//    private String major;
//    @NotBlank
//    private String password;
//
//    public String getRealName() {
//        return realName;
//    }
//
//    public void setRealName(String realName) {
//        this.realName = realName;
//    }
//
//    public User() {
//    }
//
//    public UserType getUserType() {
//        return userType;
//    }
//
//    public void setPerson(Person person) {
//        this.person = person;
//    }
//
//    public Person getPerson() {
//        return person;
//    }
//
//    public void setUserType(UserType userType) {
//        this.userType = userType;
//    }
//
//    public User(String username, String password) {
//        this.userName = username;
//        this.password = password;
//    }
//    public String getPassword() {
//        return password;
//    }
//
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//
//
//    public String getUniversity() {
//        return university;
//    }
//
//
//    public String getPhone() {
//        return phone;
//    }
//
//
//    public String getMajor() {
//        return major;
//    }
//
//
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//
//    public void setUniversity(String university) {
//        this.university = university;
//    }
//
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public void setMajor(String major) {
//        this.major = major;
//    }
//}
