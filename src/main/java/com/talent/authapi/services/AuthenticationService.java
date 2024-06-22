package com.talent.authapi.services;


import com.talent.authapi.dtos.LoginUserDto;
import com.talent.authapi.dtos.RegisterUserDto;
import com.talent.authapi.entities.User;
import com.talent.authapi.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
//        User user = new User()
//                .setUserName(input.getUserName())
//                .setEmail(input.getEmail())
//                .setPassword(passwordEncoder.encode(input.getPassword()));
    	// 檢查密碼是否相符
        if (!input.getPassword().equals(input.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // 創建新的使用者實例並設置屬性
        User user = new User();
        user.setUserName(input.getUserName());
        user.setEmail(input.getEmail());
        user.setAccount(input.getAccount());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole("ROLE_USER"); // 默認user

        // 儲存使用者到資料庫並返回儲存後的使用者物件
        return userRepository.save(user);
        
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
