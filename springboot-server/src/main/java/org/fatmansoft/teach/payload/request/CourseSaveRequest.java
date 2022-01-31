package org.fatmansoft.teach.payload.request;

public class CourseSaveRequest {
    private String userId;
    private String courseId;
    public CourseSaveRequest(){
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getUserId() {
        return userId;
    }
}

//    Optional<Stoc> stoc = stocRepository.findByUserId(c.getUserId());
//    Stoc s = stoc.get();
//        System.out.println("保存"+c.getCourseId()+c.getUserId()+"\n"+stoc.get().getCourses());
//                Optional<Course> csa = courseRespository.findById(c.getCourseId());
//        Course course = csa.get();
//        boolean success = false;
//        if(course.getCurrentNumber()<course.getCapacity()){
//        // 保存选课学生的学号
//        course.setStudents(course.getStudents()+(course.getStudents()==""||course.getStudents()==null?"":";")+c.getUserId());
//        // 选课人数加一
//        course.setCurrentnumber(course.getCurrentNumber()+1);
//        // 学生课表添加课程
//        s.setCourses(s.getCourses()+(s.getCourses()==""||s.getCourses()==null?"":";")+c.getCourseId());
//        stocRepository.save(s);
//        courseRespository.save(course);
//        success = true;
//        }
//        return ResponseEntity.ok(new MessageResponse(success?"success":"failure"));