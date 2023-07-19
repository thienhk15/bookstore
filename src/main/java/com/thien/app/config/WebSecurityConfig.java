package com.thien.app.config;


import com.thien.app.security.AuthenticationFilter;
import com.thien.app.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private AuthenticationFilter authenticationFilter;
    @Autowired
    private UserDetailsService userDetailsService;
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers( "/",
                                "/static/**",
                                "/templates/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/fonts/**",
                                "/admin/**",
                                "/admin/css/**",
                                "/customer/home",
                                "/customer/register",
                                "/api/auth/login/**",
                                "/api/auth/register/**",
                                "/api/users/**",
                                "/customer/login/**",
                                "/api/**",
                                "/favicon.ico",
                                "/admin/login/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui",
                                "/swagger/**",
                                "/401",
                                "/test/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/customer/products/**",
                                                        "/api/books").permitAll()
                .requestMatchers(HttpMethod.POST, "/customer/login",
                                                    "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.DELETE).hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionFixation().none()
                .and()
//                .formLogin((form) -> form
//                        .loginPage("/customer/login")
//                        .usernameParameter("email")
//                        .passwordParameter("password")
//                        .successHandler(savedRequestAwareAuthenticationSuccessHandler())
//                        // Cấu hình các thiết lập khác
//                        .failureUrl("/customer/login")
//                        .permitAll()
//                )
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/customer/login")
                .deleteCookies("JSESSIONID") // Xóa cookie JSESSIONID (nếu có)
                .invalidateHttpSession(true) // Xóa session
                .clearAuthentication(true)
                .permitAll()
                .and()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/customer/home"); // Trang mặc định sau khi xác thực thành công
        return successHandler;
    }
}
