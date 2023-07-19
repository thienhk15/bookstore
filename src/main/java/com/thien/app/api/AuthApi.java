package com.thien.app.api;

import com.thien.app.dto.JWTAuthResponse;
import com.thien.app.dto.LoginDto;
import com.thien.app.dto.RegisterDto;
import com.thien.app.entity.RefreshToken;
import com.thien.app.entity.User;
import com.thien.app.enums.Role;
import com.thien.app.repository.UserRepository;
import com.thien.app.security.JwtTokenProvider;
import com.thien.app.service.RefreshTokenService;
import com.thien.app.service.UserService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
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

@RestController
@RequestMapping("/api/auth")
public class AuthApi {
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
    public ResponseEntity<?> authenticateUser(HttpServletRequest request, @RequestBody LoginDto loginDto){
        JWTAuthResponse jwtAuthResponse;
        try{
            //authenticate
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails,loginDto.getPassword()));
            //if not authenticated
            if(!authentication.isAuthenticated()){
                jwtAuthResponse = new JWTAuthResponse( "unauthorized");
                return ResponseEntity.ok(jwtAuthResponse);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

            //create accessToken and refreshToken
            Long userId = userService.getUserByEmail(loginDto.getEmail()).getId();
            String accessToken = jwtTokenProvider.generateToken(authentication);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);
            request.getSession().setAttribute("authentication", authentication);
            //create response
            jwtAuthResponse = new JWTAuthResponse(accessToken, refreshToken.getToken(), userId, loginDto.getEmail(), "success");
        } catch (Exception e){
            jwtAuthResponse = new JWTAuthResponse( "User Not Found");
        }
        return ResponseEntity.ok(jwtAuthResponse);
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
        return authenticateUser(request,loginDto);

        //return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
