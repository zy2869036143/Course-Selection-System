package org.fatmansoft.teach.payload.response;

import java.util.Map;

// 405
public class CourseResponse {
    private String code;
    private Map data;
    public CourseResponse(String c, Map d){
        this.code = c;
        this.data = d;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
