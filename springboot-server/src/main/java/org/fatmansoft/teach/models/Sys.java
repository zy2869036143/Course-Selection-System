package org.fatmansoft.teach.models;

import javax.persistence.*;

@Entity
//@Table(name = "S202000300021_Sys",
//        uniqueConstraints =
//        @UniqueConstraint(columnNames = "term")
//)
public class Sys {
    @Id
    @Column(name = "term", nullable = false)
    private String term;

    @Column(name = "state", nullable = false)
    private int state;
    public Sys(){}

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
