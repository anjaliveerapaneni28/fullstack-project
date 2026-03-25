package com.trackwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trackwise.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

    User findByEmail(String email);

}
