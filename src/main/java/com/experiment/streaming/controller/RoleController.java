package com.experiment.streaming.controller;

import com.experiment.streaming.annotation.security.IsAdmin;
import com.experiment.streaming.entity.RoleEntity;
import com.experiment.streaming.exception.BusinessException;
import com.experiment.streaming.exception.BusinessExceptions;
import com.experiment.streaming.mapper.ConstantMapper;
import com.experiment.streaming.model.Role;
import com.experiment.streaming.service.base.EntityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@IsAdmin
@Controller
@RequestMapping(value="/roles")
public class RoleController {

    private final EntityService entityService;

    public RoleController(EntityService entityService){
        this.entityService = entityService;
    }

    @RequestMapping
    public String role(Model model) {
        List<Role> roles = ConstantMapper.toModelList(entityService.findAll(RoleEntity.class));
        model.addAttribute("roles", roles);
        return "role/roles";
    }

    @RequestMapping(value="/add")
    public String addRolePage() {
        return "role/add";
    }

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public String addRole(@ModelAttribute("role") Role role){
        entityService.save(ConstantMapper.toEntity(role,RoleEntity.class));
        return "redirect:/roles";
    }

    @RequestMapping(value="/update")
    public String updateRolePage(@RequestParam(name="id") Long id, Model model) {
        model.addAttribute("role",ConstantMapper.toModel(entityService.find(id,RoleEntity.class).orElseThrow(()->BusinessExceptions.ROLE_NOT_FOUND_BY_ID)));
        return "role/update";
    }

    @RequestMapping(value="/update",method = RequestMethod.POST)
    public String updateRole(@ModelAttribute("role") Role role){
        entityService.save(ConstantMapper.toEntity(role,RoleEntity.class));
        return "redirect:/roles";
    }

    @RequestMapping(value="/delete")
    public String deleteRole(@RequestParam(name="id") Long id) {
        entityService.delete(id,RoleEntity.class);
        return "redirect:/roles";
    }

}
