package com.thien.app.controller.customer;

import com.thien.app.dto.JWTAuthResponse;
import com.thien.app.dto.LoginDto;
import com.thien.app.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/customer")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/login")
    public String auth_login(HttpSession session){
        session.removeAttribute("auth");
        session.removeAttribute("currentUser");
        return "login";
    }

    @PostMapping(path="/login" )
    public RedirectView login(@RequestBody LoginDto loginDto, RedirectAttributes  redirectAttributes, HttpSession session){
        try{
            //authenticate
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails,loginDto.getPassword()));

            if(!authentication.isAuthenticated()){
                RedirectView redirectView = new RedirectView("/customer/login");
                redirectView.setExposeModelAttributes(true);
                return redirectView;
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            redirectAttributes.addFlashAttribute("auth", SecurityContextHolder.getContext().getAuthentication());
            session.setAttribute("auth", SecurityContextHolder.getContext().getAuthentication());
            session.setAttribute("currentUser",userService.getUserByEmail(authentication.getName()));
            System.out.println("authentication auth controller: "+ SecurityContextHolder.getContext().getAuthentication());
            RedirectView redirectView = new RedirectView("/customer/home");
            redirectView.setExposeModelAttributes(true);
            return redirectView;

        } catch (Exception e){
            RedirectView redirectView = new RedirectView("/customer/login");
            redirectView.setExposeModelAttributes(true);
            return redirectView;
        }
    }
    @GetMapping("/register")
    public String auth_register(){
        return "login";
    }
    @PostMapping(path="/register" )
    public RedirectView register(@Valid @RequestBody LoginDto loginDto, RedirectAttributes  redirectAttributes){
        String apiUrl = "http://localhost:8080/api/auth/register"; // URL của API login

        ResponseEntity<JWTAuthResponse> response = restTemplate.postForEntity(apiUrl, loginDto, JWTAuthResponse.class);
        JWTAuthResponse jwtAuthResponse = response.getBody();

        if(jwtAuthResponse!=null) {
            redirectAttributes.addFlashAttribute("accessToken", jwtAuthResponse.getAccessToken());
            redirectAttributes.addFlashAttribute("refreshToken", jwtAuthResponse.getRefreshToken());
            System.out.println("auth api jwtAuthResponse"+ jwtAuthResponse.toString());
            RedirectView redirectView = new RedirectView("/customer/home");
            redirectView.setExposeModelAttributes(true);
            return redirectView;
        }
        RedirectView redirectView = new RedirectView("/customer/login");
        redirectView.setExposeModelAttributes(true);
        return redirectView;
    }
//    @GetMapping("/logout")
//    public RedirectView logout() {
//        return new RedirectView("/customer/home");
//    }

}
