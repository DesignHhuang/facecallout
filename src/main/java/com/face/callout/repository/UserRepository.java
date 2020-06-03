package com.face.callout.repository;

import com.face.callout.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

	User findByEmail(String email);

	User findByMobile(String mobile);

	User findByEmailOrMobile(String email,String mobile);


}