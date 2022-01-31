package org.fatmansoft.teach.payload.request;

public class ScoreSaveRequest {
    private String courseId;
    private String data;

    public ScoreSaveRequest(){}

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
