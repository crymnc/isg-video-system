package com.experiment.streaming.mapper.statistic;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.survey.SurveyEntity;
import com.experiment.streaming.entity.survey.SurveyResultEntity;
import com.experiment.streaming.mapper.UserMapper;
import com.experiment.streaming.model.statistic.SurveyStatistic;

import java.util.List;
import java.util.stream.Collectors;

public class SurveyStatisticMapper {

    public static SurveyStatistic toModel(UserEntity userEntity, SurveyEntity surveyEntity, List<SurveyResultEntity> surveyResultEntities) {
        SurveyStatistic surveyStatistic = new SurveyStatistic();
        surveyStatistic.setSurveyId(surveyEntity.getId());
        surveyStatistic.setUser(UserMapper.toModel(userEntity));
        long count = surveyResultEntities.stream()
                .filter(surveyResultEntity -> surveyResultEntity.getUser().getId().equals(userEntity.getId())).count();
        surveyStatistic.setSurveyStatus(surveyEntity.getQuestions().size() == count ? "Completed" : "Not Completed");

        return surveyStatistic;
    }

    public static List<SurveyStatistic> toModelList(List<UserEntity> userEntities, List<SurveyResultEntity> surveyResultEntities, SurveyEntity surveyEntity) {
        return userEntities.stream().map(userEntity -> toModel(userEntity,surveyEntity,surveyResultEntities)).collect(Collectors.toList());
    }
}
