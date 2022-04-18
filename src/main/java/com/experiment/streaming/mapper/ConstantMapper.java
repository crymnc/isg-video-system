package com.experiment.streaming.mapper;

import com.experiment.streaming.entity.base.BaseConstantEntity;
import com.experiment.streaming.model.base.BaseConstantModel;

import java.util.List;
import java.util.stream.Collectors;

public class ConstantMapper {


    public static <X extends BaseConstantModel, Y extends BaseConstantEntity> X toModel(Y constantEntity) {
        BaseConstantModel model = new BaseConstantModel();
        model.setId(constantEntity.getId());
        model.setName(constantEntity.getName());
        model.setDsc(constantEntity.getDsc());
        model.setDiscontinueDate(constantEntity.getDiscontinueDate());
        return (X) model;
    }

    public static <X extends BaseConstantEntity, Y extends BaseConstantModel> X toEntity(Y constantModel, Class<X> clazz) {
        X entity = null;
        try {
            entity = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        entity.setId(constantModel.getId());
        entity.setName(constantModel.getName());
        entity.setDsc(constantModel.getDsc());
        entity.setDiscontinueDate(constantModel.getDiscontinueDate());
        return entity;
    }

    public static <X extends BaseConstantModel, Y extends BaseConstantEntity> List<X> toModelList(List<Y> baseConstantEntityList){
        return baseConstantEntityList.stream().map(constantEntity -> (X)toModel(constantEntity)).collect(Collectors.toList());
    }

    public static <X extends BaseConstantEntity, Y extends BaseConstantModel> List<X> toEntityList(List<Y> baseConstantModels, Class<X> clazz){
        return baseConstantModels.stream().map(baseConstantModel -> toEntity(baseConstantModel,clazz)).collect(Collectors.toList());
    }
}
