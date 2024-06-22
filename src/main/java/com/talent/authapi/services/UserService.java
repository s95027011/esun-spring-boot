package com.talent.authapi.services;

import com.talent.authapi.entities.User;
import com.talent.authapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
    
    public User findByAccount(String account) {
        return userRepository.findByAccount(account).orElse(null);
    }
    
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
