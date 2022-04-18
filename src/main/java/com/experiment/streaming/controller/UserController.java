package com.experiment.streaming.controller;

import com.experiment.streaming.annotation.security.IsSupervisor;
import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.entity.RoleEntity;
import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.exception.BusinessExceptions;
import com.experiment.streaming.mapper.CompanyMapper;
import com.experiment.streaming.mapper.ConstantMapper;
import com.experiment.streaming.mapper.UserMapper;
import com.experiment.streaming.mapper.UserSummaryMapper;
import com.experiment.streaming.model.Company;
import com.experiment.streaming.model.Role;
import com.experiment.streaming.model.User;
import com.experiment.streaming.service.UserService;
import com.experiment.streaming.service.base.BaseCompanyService;
import com.experiment.streaming.service.base.EntityService;
import com.experiment.streaming.utils.LoggedUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    private final BaseCompanyService baseCompanyService;

    private final EntityService entityService;

    public UserController(UserService userService, BaseCompanyService baseCompanyService, EntityService entityService) {
        this.userService = userService;
        this.baseCompanyService = baseCompanyService;
        this.entityService = entityService;
    }

    @IsSupervisor
    @RequestMapping
    public String users(Model model) {
        UserEntity loggedUser = UserService.findLoggedUser();
        List<UserEntity> userEntityList;
        if (LoggedUserUtils.isAdmin())
            userEntityList = userService.findAllUsers();
        else
            userEntityList = baseCompanyService.findAllByCompanyId(loggedUser.getCompany().getId(),UserEntity.class).stream()
                    .filter(user -> !user.getRole().getName().equals("ROLE_ADMIN")).collect(Collectors.toList());
        model.addAttribute("users", UserSummaryMapper.toModelList(userEntityList));
        return "user/users";
    }

    @IsSupervisor
    @RequestMapping(value = "/add")
    public String addUserPage(Model model) {
        List<Role> roles;
        if(LoggedUserUtils.isAdmin())
            roles = ConstantMapper.toModelList(entityService.findAll(RoleEntity.class));
        else
            roles = ConstantMapper.toModelList(entityService.findAll(RoleEntity.class).stream().filter(role -> !role.getName().equals("ROLE_ADMIN")).collect(Collectors.toList()));
        model.addAttribute("roles", roles);
        if(LoggedUserUtils.isAdmin()) {
            List<Company> companies = CompanyMapper.toModelList(entityService.findAll(CompanyEntity.class));
            model.addAttribute("companies", companies);
        }
        return "user/add";
    }

    @IsSupervisor
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user) {
        RoleEntity roleEntity = entityService.find(user.getRoleId(),RoleEntity.class).orElseThrow(() -> BusinessExceptions.ROLE_NOT_FOUND_BY_ID);
        if(!LoggedUserUtils.isAdmin() && roleEntity.getName().equals("ROLE_ADMIN"))
            throw BusinessExceptions.ONLY_ADMIN_CAN_ADD_ADMIN;
        userService.saveUser(UserMapper.toEntity(user));
        return "redirect:/users";
    }

    @IsSupervisor
    @RequestMapping(value = "/update")
    public String updateUserPage(@RequestParam(name = "id") Long id, Model model) {
        if(LoggedUserUtils.isAdmin()) {
            List<Company> companies = CompanyMapper.toModelList(entityService.findAll(CompanyEntity.class));
            model.addAttribute("companies", companies);
        }
        List<Role> roles;
        if(LoggedUserUtils.isAdmin())
            roles = ConstantMapper.toModelList(entityService.findAll(RoleEntity.class));
        else
            roles = ConstantMapper.toModelList(entityService.findAll(RoleEntity.class).stream().filter(role -> !role.getName().equals("ROLE_ADMIN")).collect(Collectors.toList()));
        model.addAttribute("roles", roles);
        model.addAttribute("user", UserMapper.toModel(userService.find(id, UserEntity.class).orElseThrow(() -> BusinessExceptions.USER_NOT_FOUND)));
        return "user/update";
    }

    @IsSupervisor
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user) {
        RoleEntity roleEntity = entityService.find(user.getRoleId(),RoleEntity.class).orElseThrow(() -> BusinessExceptions.ROLE_NOT_FOUND_BY_ID);
        if(!LoggedUserUtils.isAdmin() && roleEntity.getName().equals("ROLE_ADMIN"))
            throw BusinessExceptions.ONLY_ADMIN_CAN_ADD_ADMIN;
        userService.updateUser(UserMapper.toEntity(user));
        return "redirect:/users";
    }

    @IsSupervisor
    @RequestMapping(value = "/delete")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        UserEntity userEntity = userService.findUserById(id).orElseThrow(() -> BusinessExceptions.USER_NOT_FOUND);
        if(!LoggedUserUtils.isAdmin() && userEntity.getRole().getName().equals("ROLE_ADMIN"))
            throw BusinessExceptions.ONLY_ADMIN_CAN_DELETE_ADMIN;
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "/profile")
    public String userProfilePage(@RequestParam(name = "user-id") Long userId, Model model) {
        UserEntity userEntity = userService.findUserById(userId).orElseThrow(() -> BusinessExceptions.USER_NOT_FOUND);
        model.addAttribute("user", UserMapper.toModel(userEntity));
        model.addAttribute("userRole", ConstantMapper.toModel(userEntity.getRole()));
        model.addAttribute("company", CompanyMapper.toModel(userEntity.getCompany()));
        return "user/profile";
    }
}
