package org.fatmansoft.teach.payload.request;

public class TermRequest {
    private String uid;
    private String term;

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}
