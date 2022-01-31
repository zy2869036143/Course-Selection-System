package org.fatmansoft.teach.controllers;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.fatmansoft.teach.models.*;
import org.fatmansoft.teach.payload.request.UserRequest;
import org.fatmansoft.teach.repository.StocRepository;
import org.fatmansoft.teach.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.fatmansoft.teach.payload.request.LoginRequest;
import org.fatmansoft.teach.payload.request.SignupRequest;
import org.fatmansoft.teach.payload.response.JwtResponse;
import org.fatmansoft.teach.payload.response.MessageResponse;
import org.fatmansoft.teach.repository.UserRepository;
import org.fatmansoft.teach.security.jwt.JwtUtils;
import org.fatmansoft.teach.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    StocRepository stocRepository;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        User user = null;
        String plus = "";
        try{
            user = userRepository.findByUserName(loginRequest.getUsername()).get();
            if (loginRequest.getUsername().equals("admin")){}
            else if (loginRequest.getIdentity().equals("stu")){
                plus="0";
            }else if (loginRequest.getIdentity().equals("tea")){
                plus="1";
            }
        }catch (NoSuchElementException E){
            loginRequest.setUsername("!T!"+loginRequest.getUsername());
            System.out.println("No Such Element.");
        }finally {
            String pass = loginRequest.getPassword();
            if (!plus.equals("")){pass = plus+pass;}
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), pass));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    roles.get(0)));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUsername())) {

            return  ResponseEntity.ok(new MessageResponse("Error: Username is already taken!"));
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        System.out.println("注册用户");
        // Create new user's account

        User user = new User();
        user.setUserName(signUpRequest.getUsername());
        user.setGender(signUpRequest.getGender());
        user.setMajor(signUpRequest.getMajor());
        user.setPhone(signUpRequest.getPhone());
        user.setRealName(signUpRequest.getRealName());
        user.setUniversity(signUpRequest.getUniversity());
        System.out.println(signUpRequest.getMajor());
        String strRoles = signUpRequest.getRole();
//        Set<String> strRoles = signUpRequest.getRole();
        if (strRoles == null) {
            UserType userRole = userTypeRepository.findByName(EUserType.ROLE_USER);
            user.setUserType(userRole);
        } else {
            //strRoles.forEach(role -> {
                switch (strRoles) {
                    case "admin":
                        UserType adminRole = userTypeRepository.findByName(EUserType.ROLE_ADMIN);
                        user.setUserType(adminRole);
                        user.setPassword(encoder.encode(signUpRequest.getPassword()));
                        break;
                    case "teacher":
                        UserType teachRole = userTypeRepository.findByName(EUserType.ROLE_TEACHER);
                        user.setUserType(teachRole);
                        user.setUserName(signUpRequest.getUsername());
                        user.setPassword(encoder.encode("1"+signUpRequest.getPassword()));
                        break;
                    default:
                        UserType userRole = userTypeRepository.findByName(EUserType.ROLE_USER);
                        user.setUserType(userRole);
                        user.setPassword(encoder.encode("0"+signUpRequest.getPassword()));
                }
            //});
        }
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/infor")
    @PreAuthorize("hasRole('USER') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUseInformation(@Valid @RequestBody UserRequest userRequest) {
        System.out.println("获取用户信息"+userRequest.getId());
        User user = userRepository.findByUserName(userRequest.getId()).get();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('USER') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        System.out.println("user"+ userUpdateRequest.getUsername());
        User user = userRepository.findByUserName(userUpdateRequest.getUsername()).get();
        user.setMajor(userUpdateRequest.getMajor());
        user.setUniversity(userUpdateRequest.getUniversity());
        user.setGender(userUpdateRequest.getGender());
        user.setRealName(userUpdateRequest.getName());
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("success"));
    }
}
