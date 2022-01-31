package org.fatmansoft.teach.payload.request;

public class OsysRequest {
    private String term;
    private String sequence;

    public OsysRequest(){

    }
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
