package com.experiment.streaming.mapper;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.model.UserSummary;

import java.util.List;
import java.util.stream.Collectors;

public class UserSummaryMapper {

    public static UserSummary toModel(UserEntity userEntity) {
        if (userEntity == null)
            return null;
        UserSummary userSummary = new UserSummary();
        userSummary.setId(userEntity.getId());
        userSummary.setName(userEntity.getName());
        userSummary.setLastName(userEntity.getLastName());
        userSummary.setEmail(userEntity.getEmail());
        if (userEntity.getCompany() != null) {
            userSummary.setCompanyName(userEntity.getCompany().getName());
            userSummary.setCompanyId(userEntity.getCompany().getId());
        }
        userSummary.setRoleName(userEntity.getRole().getDsc());
        userSummary.setRoleId(userEntity.getRole().getId());
        userSummary.setActive(userEntity.isActive());
        userSummary.setCreatedAt(userEntity.getCreatedAt());
        userSummary.setCreatedBy(userEntity.getCreatedBy());
        userSummary.setUpdatedAt(userEntity.getUpdatedAt());
        userSummary.setUpdatedBy(userEntity.getUpdatedBy());
        userSummary.setDiscontinueDate(userEntity.getDiscontinueDate());
        return userSummary;
    }

    public static List<UserSummary> toModelList(List<UserEntity> userEntityList) {
        return userEntityList.stream().map(userEntity -> toModel(userEntity)).collect(Collectors.toList());
    }

}
