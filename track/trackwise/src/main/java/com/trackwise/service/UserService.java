package com.trackwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trackwise.repository.UserRepository;
import com.trackwise.model.User;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    public void registerUser(User user){
        repo.save(user);
    }

    public User login(String email,String password){
        User u = repo.findByEmail(email);

        if(u!=null && u.getPassword().equals(password)){
            return u;
        }
        return null;
    }
}
