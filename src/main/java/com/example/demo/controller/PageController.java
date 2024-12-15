package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.security.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/*
 * 인증에 성공하면 UserDetails( 사용자 정보, 권한 )가 들어있는 Authentication이 생성됨 
 * Authentication는 SecurityContextHolder의 ThreadLocal과, 동시에 HttpSession에도 저장된다. 
 * 
 * SecurityContextHolder의 ThreadLocal가 Authentication을 한 쓰레드 내에서 공유를 가능하게 한다. 
 * 한 요청이 끝나면 스레드 로컬에서 인증 정보는 제거된다.
 * 
 * 다음번 요청에 SecurityContextPersistenceFilter에서 세션에 저장된 인증정보를 확인하고 
 * SecurityContextHolder의 스레드 로컬에 세션에 저장된 인증정보를 다시 저장한다.
 */

@Controller
public class PageController {

	@GetMapping("/")
	public String hhh(HttpServletRequest request,Model model) {
		
		String name = null;
		int age = 0;
		
        HttpSession session = request.getSession(false);
        // 요청에 담긴 세션ID에 해당하는 세션이 있으면 그 세션 반환
     	// 없으면 생성X, null반환
        
        if (session != null) {
            SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
            // 로그인 돼있으면 이게 null아님                           
            
            if (securityContext !=null) {
            	Authentication authentication = securityContext.getAuthentication();
            	CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();
            	
            	name = userDetail.getUsername();
            	age = userDetail.getAge(); // 31로 고정
            }            
        }
        
        model.addAttribute("name", name); 
        model.addAttribute("age", age); 
        
		return "home";
	}
	
	@GetMapping("/plogin")
	public String lll() {
		
		return "login";
	}

	@GetMapping("/suc")
	public String sss(Model model) {
		
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();
        
        model.addAttribute("name", userDetail.getUsername()); 
		model.addAttribute("age", userDetail.getAge()); // 31로 고정
		return "success";
	}
}
