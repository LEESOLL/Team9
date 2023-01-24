package com.example.market9.config;

import com.example.market9.entity.UserRoleEnum;
import com.example.market9.jwt.JwtAuthFilter;
import com.example.market9.jwt.JwtUtil;
import com.example.market9.security.CustomSecurityFilter;
import com.example.market9.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig {

//    private final UserDetailsServiceImpl userDetailsService;

    private final JwtUtil jwtUtil;

    @Bean // 비밀번호 암호화 기능 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // 아래의 securityFilterChain 보다 우선적으로 걸리는 설정이다.
        // h2-console 사용 및 resources 접근 허용 설정 (아래와 같이 들어오는 경로들은 인증처리 하는것을 무시하겠다)
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf().disable();

        http.formLogin().disable();

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests() // 이외의 모든 리퀘스트는 인증처리 하겠다는 의미
                .antMatchers("/api/auth/seller/accept/**").hasAnyRole(UserRoleEnum.Authority.ADMIN)
                .antMatchers("/api/auth/seller/refuse/**").hasAnyRole(UserRoleEnum.Authority.ADMIN)
                .antMatchers("/api/auth/users").hasAnyRole(UserRoleEnum.Authority.ADMIN)
                .antMatchers("/api/auth/seller").hasAnyRole(UserRoleEnum.Authority.ADMIN)
                .antMatchers("/api/auth/signup").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class); //JWT 인증/인가를 사용하기 위한 설정


        return http.build();
    }
}
