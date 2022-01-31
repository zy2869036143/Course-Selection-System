package org.fatmansoft.teach.models;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
//@Table(name = "S202000300021_Stoc",
//        uniqueConstraints = {
//            @UniqueConstraint(columnNames = "id")
//        }
//)
public class Stoc {
    @Id
    private  int id;

    @Column(name = "userId", nullable = false)
    private String userId;
    @NotBlank
    private String term;

    private String courses;

    public String getUserId() {
        return userId;
    }

    public String getTerm() {
        return term;
    }

    public int getId() {
        return id;
    }

    public String getCourses() {
        return courses;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
