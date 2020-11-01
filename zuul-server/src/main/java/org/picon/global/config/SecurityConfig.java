package org.picon.global.config;

import lombok.RequiredArgsConstructor;
import org.picon.global.filter.CustomAccessDeniedHandler;
import org.picon.global.filter.CustomAuthenticationEntryPoint;
import org.picon.global.filter.JwtAuthenticationFilter;
import org.picon.global.jwt.JwtService;
import org.picon.global.security.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtService jwtService;
    private final UserDetailServiceImpl userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                httpBasic().disable().
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                authorizeRequests().
                antMatchers("/auth/logIn/**","/auth/signIn/**","/auth/accessToken","/auth/test/**").permitAll().
                and().
                authorizeRequests().
                antMatchers("/admin/**").hasRole("ADMIN").
                and().
                authorizeRequests().
                antMatchers("/user/**").hasRole("USER").
                and().
                authorizeRequests().
                and().
                authorizeRequests().
                anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtService),
                        UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
    }
}
