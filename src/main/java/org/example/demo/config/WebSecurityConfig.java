package org.example.demo.config;

import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/", "/adatbazis", "/kapcsolat", "/diagram", "/rest",
                        "/login", "/register",
                        "/css/**", "/js/**", "/img/**", "/lib/**", "/scss/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                .requestMatchers("/api/**").hasRole("ADMIN")

                .requestMatchers("/uzenetek").hasAnyRole("USER", "ADMIN")

                .requestMatchers("/crud/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )

            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))

            .httpBasic(Customizer.withDefaults())
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedHandler((request, response, e) -> {
                    if (request.getRequestURI().startsWith(request.getContextPath() + "/api/")) {

                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    } else {

                        response.sendRedirect(request.getContextPath() + "/");
                    }
                })
            );
        return http.build();
    }
}
