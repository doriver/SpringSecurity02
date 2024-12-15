package com.example.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {  
	/* UserDetailsService
	 * 사용자 정보를 불러오는 방법을 정의함
	 * 얻은 User를 UserDetails에 담아서 return해줌
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		 
		
		User user = new User(); // username으로 DB에서 조회해 와야함
		user.setUsername(username);
		user.setAge(31);
		user.setPassword(passwordEncoder.encode("2222"));

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		 return new CustomUserDetails(user);
	}

}
