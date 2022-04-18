package com.experiment.streaming.mapper;

import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.entity.RoleEntity;
import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toModel(UserEntity userEntity){
        if(userEntity == null)
            return null;
        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        if(userEntity.getCompany()!=null)
            user.setCompanyId(userEntity.getCompany().getId());
        user.setRoleId(userEntity.getRole().getId());
        user.setEntryDate(userEntity.getEntryDate());
        user.setActive(userEntity.isActive());
        user.setLastLoginDate(userEntity.getLastLoginDate());
        user.setCreatedAt(userEntity.getCreatedAt());
        user.setCreatedBy(userEntity.getCreatedBy());
        user.setUpdatedAt(userEntity.getUpdatedAt());
        user.setUpdatedBy(userEntity.getUpdatedBy());
        user.setDiscontinueDate(userEntity.getDiscontinueDate());
        user.setPassword(userEntity.getPassword());
        return user;
    }


    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setDiscontinueDate(user.getDiscontinueDate());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setCreatedBy(user.getCreatedBy());
        userEntity.setUpdatedAt(user.getUpdatedAt());
        userEntity.setUpdatedBy(user.getUpdatedBy());
        userEntity.setName(user.getName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setActive(user.isActive());
        userEntity.setLastLoginDate(user.getLastLoginDate());
        userEntity.setEntryDate(user.getEntryDate());
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(user.getRoleId());
        userEntity.setRole(roleEntity);
        if(user.getCompanyId() != -1L) {
            CompanyEntity companyEntity = new CompanyEntity();
            companyEntity.setId(user.getCompanyId());
            userEntity.setCompany(companyEntity);
        }
        return userEntity;
    }

    public static List<User> toModelList(List<UserEntity> userEntityList){
        return userEntityList.stream().map(userEntity -> toModel(userEntity)).collect(Collectors.toList());
    }

    public static List<UserEntity> toEntityList(List<User> userList){
        return userList.stream().map(user -> toEntity(user)).collect(Collectors.toList());
    }

}
