package com.experiment.streaming.utils;

import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.exception.BusinessException;
import com.experiment.streaming.exception.BusinessExceptions;
import com.experiment.streaming.service.UserService;

public class LoggedUserUtils {

    public final static String ROLE_ADMIN = "ROLE_ADMIN";
    public final static String ROLE_COMPANY_ADMIN = "ROLE_COMPANY_ADMIN";
    public final static String ROLE_USER = "ROLE_USER";

    public static boolean isAdmin(){
        return UserService.findLoggedUser().getRole().getName().equals(ROLE_ADMIN);
    }

    public static boolean isCompanyAdmin(){
        return UserService.findLoggedUser().getRole().getName().equals(ROLE_COMPANY_ADMIN);
    }

    public static boolean isUser(){
        return UserService.findLoggedUser().getRole().getName().equals(ROLE_USER);
    }

    public static void checkPermissionToCompany(Long companyId) throws BusinessException {
        if(!(isAdmin() || UserService.findLoggedUser().getCompany().getId().equals(companyId)))
            throw BusinessExceptions.NO_PERMISSION_TO_WORK_WITH_ANOTHER_COMPANY;
    }
}
