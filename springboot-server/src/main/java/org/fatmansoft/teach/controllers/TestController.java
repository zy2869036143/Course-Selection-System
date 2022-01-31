package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.User;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.UserRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class TestController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess(HttpSession session) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return "User Content.";
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")    
    public ResponseEntity<?> adminAccess(HttpSession session) {
        LocalDateTime ts = (LocalDateTime)session.getAttribute("ts");
        Map<String, String> res = new HashMap<String, String>();
        if (ts == null) {
            res.put("ts", "");
        } else {
            res.put("ts", ts.toString());
        }
        session.setAttribute("ts",  LocalDateTime.now());
        return ResponseEntity.ok(new DataResponse(
                "ok",
                res
        ));
    }

    @PostMapping("/getMenuList")
    @PreAuthorize("hasRole('USER') or hasRole('TEACHER') or hasRole('ADMIN')")
    public DataResponse getMenuList(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        String user = dataRequest.getString("user");
        List mList = new ArrayList();
        Map m = new HashMap();
        m.put("name","ChangePassword");
        m.put("title","修改密码");
        mList.add(m);
        m = new HashMap();
        if (user.equals("admin")){
            m.put("name" ,  "DashSample");
            m.put("title", "选课控制");
            mList.add(m);
        }
        return CommonMethod.getReturnData(mList);
    }


    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('USER') or hasRole('TEACHER') or hasRole('ADMIN')")
    public DataResponse changePassword(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        String identity = dataRequest.getString("identity");
        String user = dataRequest.getString("user");
        String oldPassword = dataRequest.getString("oldPassword");
        String newPassword = dataRequest.getString("newPassword");
        if (user.equals("admin")==false){
            if(identity.equals("tea")) {oldPassword = "1"+oldPassword;newPassword = "1"+newPassword;}
            else if (identity.equals("stu")) {oldPassword = "0" +oldPassword; newPassword = "1"+newPassword;}

        }
        User u = userRepository.findByUserId(userId).get();
        if(!encoder.matches(oldPassword, u.getPassword())) {
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        }
        u.setPassword(encoder.encode(newPassword));
        userRepository.save(u);
        return CommonMethod.getReturnMessageOK();
    }


}