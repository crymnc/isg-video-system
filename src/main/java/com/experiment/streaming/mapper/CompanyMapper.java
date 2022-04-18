package com.experiment.streaming.mapper;

import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.model.Company;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {

    public static CompanyEntity toEntity(Company company){
        if(company == null)
            return null;
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(company.getId());
        companyEntity.setName(company.getName());
        companyEntity.setAddress(company.getAddress());
        companyEntity.setDescription(company.getDescription());
        companyEntity.setExternalId(company.getExternalId());
        companyEntity.setDiscontinueDate(company.getDiscontinueDate());
        return companyEntity;
    }

    public static Company toModel(CompanyEntity companyEntity){
        if(companyEntity == null)
            return null;
        Company company = new Company();
        company.setId(companyEntity.getId());
        company.setName(companyEntity.getName());
        company.setAddress(companyEntity.getAddress());
        company.setDescription(companyEntity.getDescription());
        company.setExternalId(companyEntity.getExternalId());
        company.setDiscontinueDate(companyEntity.getDiscontinueDate());
        return company;
    }

    public static List<Company> toModelList(List<CompanyEntity> companyEntities){
        return companyEntities.stream().map(companyEntity -> toModel(companyEntity)).collect(Collectors.toList());
    }

    public static List<CompanyEntity> toEntityList(List<Company> companies){
        return companies.stream().map(company -> toEntity(company)).collect(Collectors.toList());
    }
}
