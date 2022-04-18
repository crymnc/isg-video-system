package com.experiment.streaming.controller;

import com.experiment.streaming.annotation.security.IsAdmin;
import com.experiment.streaming.annotation.security.IsSupervisor;
import com.experiment.streaming.entity.ContentTypeEntity;
import com.experiment.streaming.entity.RoleEntity;
import com.experiment.streaming.exception.BusinessExceptions;
import com.experiment.streaming.mapper.ConstantMapper;
import com.experiment.streaming.model.Content;
import com.experiment.streaming.model.ContentType;
import com.experiment.streaming.model.Role;
import com.experiment.streaming.service.base.EntityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@IsAdmin
@Controller
@RequestMapping(value="/content-types")
public class ContentTypeController {

    private final EntityService entityService;

    public ContentTypeController(EntityService entityService){
        this.entityService = entityService;
    }

    @RequestMapping
    public String contentTypes(Model model) {
        List<ContentType> contentTypes = ConstantMapper.toModelList(entityService.findAll(ContentTypeEntity.class));
        model.addAttribute("contentTypes", contentTypes);
        return "content-type/content-types";
    }

    @RequestMapping(value="/add")
    public String addContentTypePage() {
        return "content-type/add";
    }

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public String addContentType(@ModelAttribute("contentType") ContentType contentType){
        entityService.save(ConstantMapper.toEntity(contentType,ContentTypeEntity.class));
        return "redirect:/content-types";
    }

    @RequestMapping(value="/update")
    public String updateContentTypePage(@RequestParam(name="id") Long id, Model model) {
        model.addAttribute("contentType",ConstantMapper.toModel(entityService.find(id,ContentTypeEntity.class).orElseThrow(()->BusinessExceptions.CONTENT_TYPE_NOT_FOUND_BY_ID)));
        return "content-type/update";
    }

    @RequestMapping(value="/update",method = RequestMethod.POST)
    public String updateContentType(@ModelAttribute("contentType") ContentType contentType){
        entityService.save(ConstantMapper.toEntity(contentType,ContentTypeEntity.class));
        return "redirect:/content-types";
    }

    @RequestMapping(value="/delete")
    public String deleteContentType(@RequestParam(name="id") Long id) {
        entityService.delete(id,ContentTypeEntity.class);
        return "redirect:/content-types";
    }

}
