package com.face.callout.service;

import java.util.*;

import com.face.callout.entity.Role;
import com.face.callout.entity.User;
import com.face.callout.repository.RoleRepository;
import com.face.callout.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	public User findUserByEmail(String email) {
	    return userRepository.findByEmail(email);
	}

	public User findUserByEmailOrMobile(String email,String mobile) {
		return userRepository.findByEmailOrMobile(email,mobile);
	}

	public void saveUser(User user) {
	    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    Role userRole = roleRepository.findByRole("ADMIN");
	    user.setRoleList(new HashSet<>(Arrays.asList(userRole)));
	    userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {

	    User user = userRepository.findByMobile(mobile);
	    if(user != null) {
	        List<GrantedAuthority> authorities = getUserAuthority(user.getRoleList());
	        return buildUserForAuthentication(user, authorities);
	    } else {
	        throw new UsernameNotFoundException("username not found");
	    }
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRole) {
	    Set<GrantedAuthority> roles = new HashSet<>();
		userRole.forEach((role) -> {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		});

	    List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
	    return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
	    return new org.springframework.security.core.userdetails.User(user.getMobile(), user.getPassword(), authorities);
	}

	public UserDetails currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		return (UserDetails) authentication.getPrincipal();
	}
}