package com.sparta.project02.service;

import com.sparta.project02.dto.SignupRequestDto;
import com.sparta.project02.model.User;
import com.sparta.project02.model.UserRole;
import com.sparta.project02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void registerUser(SignupRequestDto requestDto) {
        String pattern = "^[a-zA-Z0-9]*$";
        String username = requestDto.getUsername();
        String confirm = requestDto.getConfirm();


        if(!Pattern.matches(pattern, username)){
            throw new IllegalArgumentException("닉네임은 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성돼야 합니다.");
        }
        else if(username.length()<3){
            throw new IllegalArgumentException("닉네임은 최소 3자 이상으로 구성돼야 합니다.");
        }

        if(requestDto.getPassword().length()<4){
            throw new IllegalArgumentException("패스워드는 최소 4자 이상으로 구성돼야 합니다.");
        }
        else if(requestDto.getPassword().contains((username))){
            throw new IllegalArgumentException("패스워드에 닉네임이 포함되어있습니다.");
        }

        if(!requestDto.getPassword().equals((requestDto.getConfirm()))){
            throw new IllegalArgumentException("패스워드 확인과 패스워드가 일치하지 않습니다.");
        }

        // 닉네임 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 존재합니다.");
        }
        // 사용자 ROLE 확인
        UserRole role = UserRole.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.ADMIN;
        }

        // 패스워드 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        User user = new User(username, password, email, role);
        userRepository.save(user);
    }
}