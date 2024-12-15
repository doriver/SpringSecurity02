package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.security.CustomAuthenticationSuccessHandler;
import com.example.demo.security.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // Spring Security를 활성화함
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;

//  @Bean
//  public WebSecurityCustomizer webSecurityCustomizer() { // 이걸로 했을때 권장하지 않는다고 했었음 콘솔창에서
//      return (web) -> web
//              .ignoring().antMatchers();
//  }
	
	
	/*
	 * HttpSecurity에서 빌드해서 SecurityFilterChain스프링빈으로 등록해줌
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// HTTP 요청에 대한 보안 설정을 구성합니다.
        http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/").permitAll()
				.anyRequest().authenticated()
//				.anyRequest().permitAll()
			)  
        	.formLogin((form) -> form
				.loginPage("/plogin")
				.permitAll()
				.loginProcessingUrl("/login")
                .defaultSuccessUrl("/suc") // successHandler() 넣어주면 defaultSuccessUrl없애고 AuthenticationSuccessHandler여기에서 response.sendRedirect("/suc"); 추가해줘야함
//				.successHandler(new CustomAuthenticationSuccessHandler()) // AuthenticationSuccessHandler 사용할수 있게 등록
			)           
        	.logout((logout) -> logout
        			.logoutUrl("/custom-logout")  // 로그아웃 URL을 변경합니다.
                	.logoutSuccessUrl("/")  // 로그아웃 성공 후 리디렉션할 URL을 설정
                	.invalidateHttpSession(true) // 로그아웃시 세션 삭제
                	.deleteCookies("JSESSIONID")
        			.permitAll()
        	);
     
        return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
