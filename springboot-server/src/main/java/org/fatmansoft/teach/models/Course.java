package org.fatmansoft.teach.models;

import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
//@Table(name = "S202000300021_Course" ,
//        uniqueConstraints = {
//        @UniqueConstraint(columnNames = "id"),
//})
public class  Course {
    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String term;
    @NotBlank
    private String teacher;
    @NotBlank
    private String time;
    @NotBlank
    private String place;
    @NotNull
    private int credit;
    @NotNull
    private int classhour;
    @NotBlank
    private String property;

    private String major;

    private String students;
    @NotNull
    private int capacity;
    private int sequence;
    private int currentnumber;

    private String userid;

    private int score;
    private int maxScore;
    private int minScore;


    private int rankk;
    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public void setRankk(int rankk) {
        this.rankk = rankk;
    }

    public int getRankk() {
        return rankk;
    }

    public int getScore() {
        return score;
    }

    private int remain;
    public  Course(String id){
        this.id = id;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public int getRemain() {
        return remain;
    }


    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public Course(Course c){
        this.id = c.id;
        this.name = c.name;
        this.teacher = c.teacher;
        this.time = c.time;
        this.credit = c.credit;
        this.classhour = c.classhour;
        this.property = c.property;
        this.major = c.major;
        this.place = c.place;


    }

    public Course() {

    }

    public void setClasshour(int classhour) {
        this.classhour = classhour;
    }

    public void setCurrentnumber(int currentnumber) {
        this.currentnumber = currentnumber;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getClasshour() {
        return classhour;
    }

    public int getCurrentnumber() {
        return currentnumber;
    }

    public int getSequence() {
        return sequence;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setRemainCapacity(int remainCapacity) {
        this.currentnumber = remainCapacity;
    }

    public int getRemainCapacity() {
        return currentnumber;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentnumber = currentNumber;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    public String getTerm() {
        return term;
    }

    public int getCurrentNumber() {
        return currentnumber;
    }

    public String getStudents() {
        return students;
    }


    public void setPlace(String place) {
        this.place = place;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setClassHour(int classHour) {
        this.classhour = classHour;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getClassHour() {
        return classhour;
    }

    public int getCredit() {
        return credit;
    }

    public String getId() {
        return id;
    }

    public String getMajor() {
        return major;
    }

    public String getName() {
        return name;
    }

    public String getProperty() {
        return property;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }
}
