package com.thien.app.controller;

import com.thien.app.dto.*;
import com.thien.app.entity.RefreshToken;
import com.thien.app.entity.User;
import com.thien.app.enums.Role;
import com.thien.app.repository.UserRepository;
import com.thien.app.security.JwtTokenProvider;
import com.thien.app.security.exception.TokenRefreshException;
import com.thien.app.service.RefreshTokenService;
import com.thien.app.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = JWTAuthResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(HttpServletRequest httpServletRequest, @RequestBody LoginDto loginDto){
        try{
            //authenticate
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails,loginDto.getPassword()));
            //if not authenticated
            if(!authentication.isAuthenticated()){
                JWTAuthResponse jwtAuthResponse = new JWTAuthResponse( "Unauthorized");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jwtAuthResponse);            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

            //create accessToken and refreshToken
            Long userId = userService.getUserByEmail(loginDto.getEmail()).getId();
            String accessToken = jwtTokenProvider.generateToken(userId);
            RefreshToken refreshToken = jwtTokenProvider.generateRefreshToken(userId);
            httpServletRequest.getSession().setAttribute("authentication", authentication);
            //create response
            JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(accessToken, refreshToken.getToken(), userId, loginDto.getEmail(), "success");
            return ResponseEntity.ok(jwtAuthResponse);
        } catch (Exception e){
            JWTAuthResponse jwtAuthResponse = new JWTAuthResponse( "User Not Found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jwtAuthResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser( HttpServletRequest request, @RequestBody RegisterDto signUpDto){
        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        // create and save user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);
        //
        LoginDto loginDto = new LoginDto(user.getEmail(), user.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(loginDto.getEmail());
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) throws Exception {
        String requestRefreshToken = request.getRefreshToken();

        Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findByToken(requestRefreshToken);
        if(!optionalRefreshToken.isPresent()) {
            throw new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!");
        }
        RefreshToken refreshToken = optionalRefreshToken.get();

        if (refreshTokenService.verifyExpiration(refreshToken)) {
            Long userId = refreshToken.getUserId();
            String newAccessToken = jwtTokenProvider.generateToken(userId);
            RefreshToken newRefreshToken = jwtTokenProvider.generateRefreshToken(userId);
            RefreshToken newToken = refreshTokenService.createRefreshToken(userId, newRefreshToken.getToken(), newRefreshToken.getExpiredDate());
            return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, refreshToken.getToken()));
        } else {
            throw new TokenRefreshException(requestRefreshToken, "RefreshToken has expired!");
        }

    }
}

