package com.Lotus.polyFood.Security.Service;

import com.Lotus.polyFood.Email.EmailSender;
import com.Lotus.polyFood.Model.*;
import com.Lotus.polyFood.Repository.DbContext.DbContext;
import com.Lotus.polyFood.Security.Jwt.JwtUltis;
import com.Lotus.polyFood.payload.Request.SignInRequest;
import com.Lotus.polyFood.payload.Request.SignUpRequest;
import com.Lotus.polyFood.payload.Request.TokenRefreshRequest;
import com.Lotus.polyFood.payload.Response.JwtResponse;
import com.Lotus.polyFood.payload.Response.MessageReponse;
import com.Lotus.polyFood.payload.Response.Response;
import com.Lotus.polyFood.payload.Response.TokenRefreshResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUltis jwtUltis;
    @Autowired
    DbContext dbContext;
    @Autowired
    EmailSender emailSender;
    @Autowired
    RefreshTokenService refreshTokenService;
    public ResponseEntity<?> authenticate(SignInRequest signInRequest){
        Authentication authentication =authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),signInRequest.getPassword()));

        //
        Optional<User> user = dbContext.userRepository.findByUsername(signInRequest.getUsername());
        if (user.get().getStatus()!=1)
            return ResponseEntity
                    .badRequest().body(new MessageReponse("User has not been activated!"));
        //

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUltis.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream().map((role)-> role.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        ));
    }

    public ResponseEntity<?> register(SignUpRequest signUpRequest){
        if (dbContext.userRepository.existsByEmail(signUpRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageReponse("Error: email is already taken"));
        }
        if (dbContext.userRepository.existsByUsername(signUpRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageReponse("Error: Username is already taken"));
        }

        //send code confirm
        String code = String.valueOf(generateNumber());
        String link ="http://localhost:8080/api/auth/confirm?code=" + code;
        String mail = emailSender.buildEmail(signUpRequest.getUsername(),code,link);
        emailSender.send(signUpRequest.getEmail(), mail, "Confirm Email");

        //create new user
        User user = new User(
                signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail(),new Date());

        Set<String> role = signUpRequest.getRole();
        Set<Decentralization> decentralizations = new HashSet<>();

        if (role == null){
            Decentralization decentralization = dbContext.decentralizationRepository.findByName(EDecentralization.ROLE_USER)
                 .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            decentralizations.add(decentralization);
            System.out.println(decentralization.getName());
        }else {
            role.forEach(rol -> {
                switch (rol) {
                    case "admin":
                        Decentralization adminRole = dbContext.decentralizationRepository.findByName(EDecentralization.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        decentralizations.add(adminRole);

                        break;
                    default:
                        Decentralization userRole = dbContext.decentralizationRepository.findByName(EDecentralization.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        decentralizations.add(userRole);
                        }
                    });
                }
        user.setDecentralizations(decentralizations);
        dbContext.userRepository.save(user);
        dbContext.codeRegistrationRepository.save(new CodeRegistration(
           code,
           LocalDateTime.now(),
           LocalDateTime.now().plusMinutes(15),
           user
        ));
        return ResponseEntity.ok(new MessageReponse("User register successfully! Please check your code in your email!"));
    }
    public ResponseEntity<?> refreshtoken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(requestRefreshToken);
        refreshTokenService.verifyExpiration(refreshToken.get());
        User user = refreshToken.get().getUser();
        if (user==null)
            return ResponseEntity.badRequest().body(new MessageReponse("User not found"));
        String token = jwtUltis.generateTokenFromUsername(user.getUsername());
        return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
    }
    public static int generateNumber() {
        Random random = new Random();
        return random.nextInt((999999 - 0) + 1) + 0;
    }

    public ResponseEntity<?> confirmCode(String code) {
        Optional<CodeRegistration> codeRegistration = dbContext.codeRegistrationRepository.findByCode(code);
        if (codeRegistration.get().getConfirmAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expiredAt = codeRegistration.get().getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired"); //token hết hạn
        }
        codeRegistration.get().setConfirmAt(LocalDateTime.now());

        dbContext.codeRegistrationRepository.save(codeRegistration.get());
        User user = codeRegistration.get().getUser();
        user.setStatus(1);

        dbContext.userRepository.save(user);
        return ResponseEntity.ok(new MessageReponse("confirm"));
    }

    public ResponseEntity<?> forgotpassword(String email) {
        Optional<User> user = dbContext.userRepository.findByEmail(email);
        if (user.isEmpty()) return ResponseEntity
                .badRequest()
                .body(new MessageReponse("User not found!"));
        String token = UUID.randomUUID().toString();
        int hours = new Date().getHours()+1;
        Date date = new Date();
        date.setHours(hours);

        user.get().setResetPasswordToken(token);
        user.get().setResetPasswordTokenExpiry(date);
        dbContext.userRepository.save(user.get());
        String link ="http://localhost:8080/api/auth/confirmToken?token=" + token;
        String mail = emailSender.buildEmail(user.get().getUsername(),token,link);
        emailSender.send(user.get().getEmail(), mail, "Forgot password");
        return ResponseEntity.ok(new MessageReponse("Please check your email"));
    }

    public ResponseEntity<?> resetpassword(String token, String newPassword) {
        boolean flag = false;
        System.out.println(token);
        for (User user:dbContext.userRepository.findAll()){
            if (token.equals(user.getResetPasswordToken())){
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setUpdateAt(new Date());
                dbContext.userRepository.save(user);
                flag =true;
                break;
            }
        }
        if (!flag) return ResponseEntity.badRequest().body(new MessageReponse("token not found"));
        else return ResponseEntity.ok(new MessageReponse("password reset done"));
    }

    public Response<User> getUser(int id) {
        Optional<User> user = dbContext.userRepository.findById(id);
        return new Response<>("user",1,user.get());
    }

    public ResponseEntity<?> logout() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageReponse("Log out successful!"));
    }
}
