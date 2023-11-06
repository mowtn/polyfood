package com.Lotus.polyFood.Controller;

import com.Lotus.polyFood.Model.User;
import com.Lotus.polyFood.Security.Service.AuthenticationService;
import com.Lotus.polyFood.payload.Request.SignInRequest;
import com.Lotus.polyFood.payload.Request.SignUpRequest;
import com.Lotus.polyFood.payload.Request.TokenRefreshRequest;
import com.Lotus.polyFood.payload.Response.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("login")
    public ResponseEntity<?> signin(@Valid @RequestBody SignInRequest signInRequest){
        return authenticationService.authenticate(signInRequest);
    }
    @PostMapping("register")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest){
        return authenticationService.register(signUpRequest);
    }
    @PostMapping("refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request){
        return authenticationService.refreshtoken(request);
    }
    @PostMapping("confirm")
    public ResponseEntity<?> confirm(@RequestParam(name = "code")String code){
        return authenticationService.confirmCode(code);
    }
    @PostMapping("forgotpassword")
    public ResponseEntity<?> forgotpassword(@RequestParam(name = "email")String email){
        return authenticationService.forgotpassword(email);
    }
    @PostMapping("resetpassword")
    public ResponseEntity<?> resetpassword(@RequestParam(name = "token")String token,@RequestParam(name = "newPassword")String newPassword){
        return authenticationService.resetpassword(token,newPassword);
    }
    @GetMapping("get/{id}")
    public Response<User> get(@PathVariable int id){
        return authenticationService.getUser(id);
    }
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
       return authenticationService.logout();
    }
}
