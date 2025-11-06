package com.semihsahinoglu.todo_app.service;

import com.semihsahinoglu.todo_app.dto.CreateUserRequest;
import com.semihsahinoglu.todo_app.entity.Role;
import com.semihsahinoglu.todo_app.entity.User;
import com.semihsahinoglu.todo_app.repository.RoleRepository;
import com.semihsahinoglu.todo_app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User createUser(CreateUserRequest createUserRequest) {
        Set<Role> authorities = createUserRequest.authorities().stream().
                map(role -> roleRepository.findRoleByName(role.getName()).orElse(null))
                .collect(Collectors.toSet());

        User user = User.builder()
                .username(createUserRequest.username())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .authorities(authorities)
                .build();

        return userRepository.save(user);
    }
}
