package com.experiment.streaming.mapper;

import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.ContentTypeEntity;
import com.experiment.streaming.model.Content;

import java.util.List;
import java.util.stream.Collectors;

public class ContentMapper {

    public static Content toModel(ContentEntity contentEntity){
        if(contentEntity == null)
            return null;
        Content content = new Content();
        content.setId(contentEntity.getId());
        content.setName(contentEntity.getName());
        content.setDescription(contentEntity.getDescription());
        content.setContentTypeId(contentEntity.getContentType().getId());
        content.setDuration(contentEntity.getDuration());
        content.setPath(contentEntity.getPath());
        content.setShortName(contentEntity.getShortName());
        content.setTitle(contentEntity.getTitle());
        content.setDiscontinueDate(contentEntity.getDiscontinueDate());
        content.setCompanyId(contentEntity.getCompany().getId());
        content.setCompanyName(contentEntity.getCompany().getName());
        return content;
    }

    public static ContentEntity toEntity(Content content){
        if(content == null)
            return null;
        ContentEntity contentEntity = new ContentEntity();
        contentEntity.setId(content.getId());
        contentEntity.setName(content.getName());
        contentEntity.setDescription(content.getDescription());
        ContentTypeEntity contentTypeEntity=new ContentTypeEntity();
        contentTypeEntity.setId(content.getContentTypeId());
        contentEntity.setContentType(contentTypeEntity);
        contentEntity.setDuration(content.getDuration());
        contentEntity.setPath(content.getPath());
        contentEntity.setShortName(content.getShortName());
        contentEntity.setTitle(content.getTitle());
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(content.getCompanyId());
        contentEntity.setCompany(companyEntity);
        contentEntity.setDiscontinueDate(content.getDiscontinueDate());
        return contentEntity;
    }

    public static List<Content> toModelList(List<ContentEntity> contentEntities){
        return contentEntities.stream().map(contentEntity -> toModel(contentEntity)).collect(Collectors.toList());
    }

    public static List<ContentEntity> toEntityList(List<Content> contents){
        return contents.stream().map(content -> toEntity(content)).collect(Collectors.toList());
    }
}
