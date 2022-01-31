package org.fatmansoft.teach.controllers;
import org.fatmansoft.teach.models.*;
import org.fatmansoft.teach.payload.request.*;
import org.fatmansoft.teach.payload.response.CourseResponse;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.payload.response.MessageResponse;
import org.fatmansoft.teach.repository.CourseRespository;
import org.fatmansoft.teach.repository.StocRepository;
import org.fatmansoft.teach.repository.SysInfo;
import org.fatmansoft.teach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseRespository courseRespository;
    @Autowired
    StocRepository stocRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SysInfo sysRepository;

    // 系统管理员开放选课学期
    @PostMapping("/osys")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> openSYS(@Valid @RequestBody OsysRequest osys){
        System.out.println("osys" + osys.getTerm());
        sysRepository.findById(osys.getTerm());
        Sys sys = new Sys();
        sys.setTerm(osys.getTerm()+"-"+(Integer.parseInt(osys.getTerm())+1)+"-"+osys.getSequence());
        sys.setState(0);
        sysRepository.save(sys);
        return ResponseEntity.ok(new MessageResponse("success"));
    }

    // 系统管理员关闭选课学期
    @PostMapping("/esys")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> endSYS(){
        System.out.println("关闭选课系统");
        List<Sys> syses =  sysRepository.findAllByState(0);
        for (int i=0; i< syses.size();++i){
            syses.get(i).setState(1);
            sysRepository.save(syses.get(i));
        }
        return ResponseEntity.ok(new MessageResponse("success"));
    }

    // 用户获取当前正在开放的选课学期
    @PostMapping("/term")
    public ResponseEntity<?>  getTerm(){
        List<Sys> sysList =  sysRepository.findAllByState(0);
        Map<Integer, Sys> map = new HashMap<>();
        try{
            map.put(0,sysList.get(0));
        }catch (IndexOutOfBoundsException o){}
        System.out.println("获取学期");
        return ResponseEntity.ok( new DataResponse("ok", map));
    }

    // 学生获取当前选课学期对应的所有课程
    @PostMapping("/all")
    @PreAuthorize("hasRole('USER')  or hasRole('TEACHER')  or hasRole('ADMIN')")
    public ResponseEntity<?> getAll(){
        System.out.println("获取当前学期对应的所有课程");
        Sys sys = sysRepository.findAllByState(0).get(0);
        List<Course> lsc =  courseRespository.findByTerm(sys.getTerm());
        int length = lsc.size();
        Map<String, Course> map = new HashMap<String, Course>();
        for (int i=0; i<length;++i){
            Course course = lsc.get(i);
            course.setSequence(i+1);
            map.put(i+"", course);
            course.setRemain(course.getCapacity()-course.getCurrentNumber());
        }
        return ResponseEntity.ok(new CourseResponse("ok",map));
    }

    // 学生开启选课学期
    @PostMapping("/setTerm")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> setTerm(@Valid @RequestBody TermRequest termRequest){
        String uid = termRequest.getUid();
        String term = termRequest.getTerm();
        try {
            stocRepository.findByUserIdAndAndTerm(uid, term).get();
            System.out.println("已经存在");
        }catch (NoSuchElementException e){
            Stoc stoc = new Stoc();
            stoc.setCourses("");
            stoc.setTerm(term);
            stoc.setUserId(uid);
            List<Stoc> s = stocRepository.findAll();
            stoc.setId(s.size()+1);
            stocRepository.save(stoc);
        }
        return ResponseEntity.ok(new MessageResponse("success"));
    }

    // 用户查看自己的课程请求
    @PostMapping("/mycourse")
    @PreAuthorize("hasRole('USER')  or hasRole('TEACHER')  or  hasRole('ADMIN')")
    public ResponseEntity<?> getMyCourse(@Valid @RequestBody UserRequest ur){
        System.out.println("获取我的课程");
        String username = ur.getId();
        Optional<Stoc> stocOptional = stocRepository.findByUserId(username);
        Map<String, Course> map  = new HashMap<String, Course>();
        if (!stocOptional.isPresent())
            return ResponseEntity.ok(new CourseResponse("ok",map));
        Stoc stoc = stocOptional.get();
        if(stoc.getCourses().equals("") || stoc.getCourses()==null){}
        else{
            String mycourses []= stoc.getCourses().split(";");
            for (int i=0;i<mycourses.length;++i){
                try {
                    Course c = courseRespository.findById(mycourses[i]).get();
                    map.put(i+"", c);
                }
                catch (NoSuchElementException e){
                    System.out.println("未找到课程"+mycourses[i]);
                }
            }
        }
        return ResponseEntity.ok(new CourseResponse("ok",map));
    }

    // 老师录入学生成绩请求
    @PostMapping("/setScore")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<?> setScore(@Valid @RequestBody ScoreSaveRequest scoreSaveRequest){
        System.out.println("保存学生成绩");
        List<Sys>  ses = sysRepository.findAllByState(0);
        if (ses.size()!=0){
            return ResponseEntity.ok(new MessageResponse("fail"));
        }
        Course course = courseRespository.findById(scoreSaveRequest.getCourseId()).get();
        course.setStudents(scoreSaveRequest.getData());
        courseRespository.save(course);
        return ResponseEntity.ok(new MessageResponse("success"));
    }

    // 学生查看课程得分请求
    @PostMapping("/stuScore")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> stuScore(@Valid @RequestBody UserRequest userRequest){
        String uid = userRequest.getId();
        Stoc stoc = stocRepository.findByUserId(uid).get();
        Map<String, Course> map  = new HashMap<String, Course>();
        int counttt = 0;
        if(stoc.getCourses().equals("") || stoc.getCourses()==null){}

        else{
            String mycourses []= stoc.getCourses().split(";");
            for (int i=0;i<mycourses.length;++i){
                try {
                    Course c = courseRespository.findById(mycourses[i]).get();
                    String students = c.getStudents();
                    String stulist []=  students.split(";");
                    int [] scores = new int[stulist.length];
                    for(int ii=0; ii< stulist.length; ++ii){
                        if(stulist[ii].contains(":"))
                            scores[ii] = Integer.parseInt(stulist[ii].split(":")[1]);
                    }
                    Arrays.sort(scores);
                    c.setMaxScore(scores[stulist.length-1]);
                    c.setMinScore(scores[0]);
                    if (students.contains(uid+":")){
                        String s_score =  students.split((uid+":"))[1];
                        Integer score = 0;
                        if (s_score.split(";").length!=1 || s_score.contains(";")==false){
                            s_score = s_score.split(";")[0];
                            score = Integer.parseInt(s_score);
                        }
                        int index=0;
                        for(int p=0; p<stulist.length; ++p){
                            if (scores[p]==score){ index = p ; break;}
                        }
                        c.setRankk(stulist.length - index);
                        c.setScore(score);
                        map.put(counttt+"", c);
                        counttt++;
                    }
                }
                catch (NoSuchElementException e){
                    System.out.println("未找到课程"+mycourses[i]);
                }
            }
        }
        return ResponseEntity.ok(new CourseResponse("ok",map));
    }


    // 学生保存所选课程
    @PostMapping("/save")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> save(@Valid @RequestBody CourseSaveRequest c){
        Optional<Stoc> stoc = stocRepository.findByUserId(c.getUserId());
        Stoc stu = stoc.get();
        System.out.println("保存"+c.getCourseId()+c.getUserId()+"\n"+stoc.get().getCourses());
        Optional<Course> csa = courseRespository.findById(c.getCourseId());
        Course course = csa.get();
        boolean success = false;
        if(course.getCurrentNumber()<course.getCapacity()){
            boolean b = stu.getCourses().equals("")||stu.getCourses()==null;
            stu.setCourses(stu.getCourses()+(b?"":";")+c.getCourseId());
            stocRepository.save(stu);
            // 保存选课学生的学号
            course.setStudents(course.getStudents()+(course.getStudents().equals("")||course.getStudents()==null?"":";")+c.getUserId());
            // 选课人数加一
            course.setCurrentNumber(course.getCurrentNumber()+1);
            // 学生课表添加课
            courseRespository.save(course);
            success = true;
        }
        return ResponseEntity.ok(new MessageResponse(success?"success":"fail"));
    }

    // 学生退课请求
    @PostMapping("/erase")
    @PreAuthorize("hasRole('USER') or hasRole('TEACHER')  or hasRole('ADMIN')")
    public ResponseEntity<?>  handle(@Valid @RequestBody  CourseSaveRequest csr){
        System.out.println(csr.getCourseId()+"____"+csr.getUserId());
        String eraseCourse = csr.getCourseId();
        String userId = csr.getUserId();
        Course course = courseRespository.findById(eraseCourse).get();
        String currentStus =  course.getStudents();
        if(currentStus.startsWith(userId) & currentStus.endsWith(userId)){
            currentStus = currentStus.replace(userId,"");
        }else if(currentStus.endsWith(userId)){
            currentStus =  currentStus.replace(";"+userId,"");
        }else{
            currentStus = currentStus.replace(userId+";","");
        }
        course.setStudents(currentStus);
        // 选课人数减1
        course.setCurrentNumber(course.getCurrentNumber()-1);
        courseRespository.save(course);
        System.out.println("成功删除学生");
        Stoc stoc = stocRepository.findByUserId(userId).get();
        String mycourse = stoc.getCourses();
        // 保持格式
        if (mycourse.endsWith(eraseCourse) & mycourse.startsWith(eraseCourse)){
            mycourse = mycourse.replace(eraseCourse, "");
        }else if (mycourse.endsWith(eraseCourse)){
            mycourse = mycourse.replace(";"+eraseCourse,"");
        }else{
            mycourse = mycourse.replace(eraseCourse+";","");
        }
        stoc.setCourses(mycourse);
        stocRepository.save(stoc);
        System.out.println("成功删除课程");
        return ResponseEntity.ok(new MessageResponse("success"));
    }

    // 老师开设课程请求
    @PostMapping("/setup")
    @PreAuthorize("hasRole('TEACHER')  or  hasRole('ADMIN')")
    public ResponseEntity<?>  handle(@Valid @RequestBody  Course csr){
        System.out.println("csr"+ csr.getId());
        csr.setStudents("");
        csr.setSequence(0);
        csr.setScore(0);
        csr.setCurrentnumber(0);
        csr.setMajor("");
        csr.setClassHour(csr.getCredit()*32);

        csr.setMaxScore(0);
        csr.setMinScore(0);
        csr.setRankk(0);
        csr.setRemain(0);

        courseRespository.save(csr);
        String uid = csr.getUserid();
        Optional<Stoc> stocOptional = stocRepository.findByUserIdAndAndTerm(uid, csr.getTerm());
        if (!stocOptional.isPresent()){
            Stoc stoc = new Stoc();
            stoc.setUserId(uid);
            stoc.setTerm(csr.getTerm());
            stoc.setCourses(csr.getId());
            stocRepository.save(stoc);
            return ResponseEntity.ok(new MessageResponse("success"));
        }
        Stoc stoc = stocOptional.get();
        String current = stoc.getCourses();
        // 不为空
        if(!(current.equals("null") || current.equals(""))){
            current+=";";
        }
        stoc.setTerm(csr.getTerm());
        stoc.setCourses(current+csr.getId());
        stocRepository.save(stoc);
        return ResponseEntity.ok(new MessageResponse("success"));
    }

    // 老师停止开设课程请求
    @PostMapping("/stopopen")
    @PreAuthorize("hasRole('USER')  or hasRole('TEACHER')  or hasRole('ADMIN')")
    public ResponseEntity<?>  stopOpenCourse(@Valid @RequestBody  CourseSaveRequest csr){
        String courseId = csr.getCourseId();
        System.out.println("教师停开课程");
        String userId = csr.getUserId();
        Course course = courseRespository.findById(courseId).get();
        Stoc stoc =  stocRepository.findByUserId(userId).get();
        String mycourse = stoc.getCourses();
        // 保持格式
        if (mycourse.startsWith(courseId) & mycourse.endsWith(courseId)){
            mycourse = mycourse.replace(courseId, "");
        }
        else if (mycourse.endsWith(courseId)){
            mycourse = mycourse.replace(";"+courseId, "");
        }else{
            mycourse = mycourse.replace(courseId+";","");
        }
        stoc.setCourses(mycourse);
        stocRepository.save(stoc);
        courseRespository.delete(course);
        return ResponseEntity.ok(new MessageResponse("success"));
    }

    @PostMapping("/score")
    @PreAuthorize("hasRole('USER')  or hasRole('TEACHER')  or hasRole('ADMIN')")
    public ResponseEntity<?> getStudents(@Valid @RequestBody CourseSaveRequest c){
        String userId = c.getUserId();
        String courseId = c.getCourseId();
        Course course = courseRespository.findById(courseId).get();
        String stus[] = course.getStudents().split(";");
        System.out.println("录入分数");
        Map<String, User> map = new HashMap<>();
        for (int i=0; i < stus.length;++i){
            String uid = null;
            int score=0;
            if(stus[i].contains(":")){
                uid =  stus[i].split(":")[0];
                score =  Integer.parseInt(stus[i].split(":")[1]);
            }else {
                uid =  stus[i];
            }
            User  user = null;
            try {
                user = userRepository.findByUserName(uid).get();
                user.setScore(score);
                map.put(i+"", user);
            }catch (NoSuchElementException e) {
            }
        }
        return ResponseEntity.ok(new DataResponse("ok",map));
    }
}
