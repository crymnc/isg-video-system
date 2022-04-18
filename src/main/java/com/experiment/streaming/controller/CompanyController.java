package com.experiment.streaming.controller;

import com.experiment.streaming.annotation.security.IsAdmin;
import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.exception.BusinessExceptions;
import com.experiment.streaming.mapper.CompanyMapper;
import com.experiment.streaming.model.Company;
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
@RequestMapping(value = "/companies")
public class CompanyController {

    private final EntityService entityService;

    public CompanyController(EntityService entityService){
        this.entityService = entityService;
    }

    @RequestMapping
    public String companies(Model model) {
        List<CompanyEntity> companyEntities = entityService.findAll(CompanyEntity.class);
        model.addAttribute("companies", CompanyMapper.toModelList(companyEntities));
        return "company/companies";
    }

    @RequestMapping(value="/add")
    public String addCompanyPage() {
        return "company/add";
    }

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public String addCompany(@ModelAttribute("company") Company company){
        entityService.save(CompanyMapper.toEntity(company));
        return "redirect:/companies";
    }

    @RequestMapping(value="/update")
    public String updateRolePage(@RequestParam(name="id") Long id, Model model) {
        model.addAttribute("company",CompanyMapper.toModel(entityService.find(id,CompanyEntity.class).orElseThrow(()-> BusinessExceptions.COMPANY_NOT_FOUND_BY_ID)));
        return "company/update";
    }

    @RequestMapping(value="/update",method = RequestMethod.POST)
    public String updateRole(@ModelAttribute("company") Company company){
        entityService.save(CompanyMapper.toEntity(company));
        return "redirect:/companies";
    }

    @RequestMapping(value="/delete")
    public String deleteCompany(@RequestParam(name="id") Long id) {
        entityService.delete(id,CompanyEntity.class);
        return "redirect:/companies";
    }
}
