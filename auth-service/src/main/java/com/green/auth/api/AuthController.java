package com.green.auth.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.green.auth.domain.User;
import com.green.auth.users.UserService;
import com.green.auth.users.PasswordResetTokenRepository;
import com.green.auth.domain.PasswordResetToken;
import com.green.auth.users.ResetNotificationService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordResetTokenRepository resetTokenRepository;

    @Autowired
    private ResetNotificationService resetNotificationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtil.generateToken((UserDetails) auth.getPrincipal());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam(required = false) String username,
            @RequestParam(required = false) String phone) {
        String target = username;
        if ((target == null || target.isBlank()) && phone != null) {
            User u = userService.getUserByPhone(phone);
            if (u != null)
                target = u.getUsername();
        }
        if (target == null || target.isBlank()) {
            return ResponseEntity.badRequest().body("Username or phone is required");
        }
        resetTokenRepository.deleteByUsername(target);
        PasswordResetToken token = new PasswordResetToken();
        token.setUsername(target);
        token.setToken(java.util.UUID.randomUUID().toString());
        token.setExpiresAt(java.time.LocalDateTime.now().plusMinutes(30));
        resetTokenRepository.save(token);
        resetNotificationService.sendEmailToken(target, token.getToken());
        if (phone != null && !phone.isBlank()) {
            resetNotificationService.sendSmsToken(phone, token.getToken());
        }
        return ResponseEntity.ok("Reset token issued");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        PasswordResetToken prt = resetTokenRepository.findByToken(token).orElse(null);
        if (prt == null || prt.getExpiresAt().isBefore(java.time.LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
        User user = userService.getUserByUsername(prt.getUsername());
        if (user == null)
            return ResponseEntity.badRequest().body("User not found");
        user.setPassword(passwordEncoder.encode(newPassword));
        resetTokenRepository.deleteByToken(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfile> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).build();
        }
        UserProfile profile = new UserProfile();
        profile.setUsername(auth.getName());
        return ResponseEntity.ok(profile);
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static class UserProfile {
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
