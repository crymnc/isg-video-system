package com.experiment.streaming.service;

import com.experiment.streaming.config.CustomUserDetails;
import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.model.User;
import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.exception.BusinessExceptions;
import com.experiment.streaming.repository.UserRepository;
import com.experiment.streaming.service.base.BaseCompanyService;
import com.experiment.streaming.service.base.EntityService;
import com.experiment.streaming.utils.LoggedUserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService extends BaseCompanyService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserEntity saveUser(UserEntity newUser){
        findUserByEmail(newUser.getEmail()).ifPresent(userEntity -> {throw BusinessExceptions.USER_ALREADY_EXISTS;});
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        if(newUser.getCompany().getId() == -1L)
            newUser.setCompany(null);
        return save(newUser);
    }

    @Transactional
    public UserEntity updateUser(UserEntity newUser){
        findUserById(newUser.getId()).orElseThrow(() -> BusinessExceptions.USER_NOT_FOUND);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return save(newUser);
    }

    public void deleteUserById(Long id){
        delete(id,UserEntity.class);
    }

    public void deleteUserByUsername(String email){
        this.findUserByEmail(email);
        userRepository.deleteByEmail(email);
    }

    public Optional<UserEntity> findUserByEmail(String email){
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        userEntity.ifPresent(user -> LoggedUserUtils.checkPermissionToCompany(user.getCompany().getId()));
        return userEntity;
    }

    public Optional<UserEntity> findUserById(Long id){
        return find(id,UserEntity.class);
    }

    public static UserEntity findLoggedUser(){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUserEntity();
    }

    public List<UserEntity> findAllUsers(){
        return findAll(UserEntity.class);
    }

}
