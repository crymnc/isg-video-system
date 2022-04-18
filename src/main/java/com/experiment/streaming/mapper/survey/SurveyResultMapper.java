package com.experiment.streaming.mapper.survey;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.survey.SurveyAnswerEntity;
import com.experiment.streaming.entity.survey.SurveyEntity;
import com.experiment.streaming.entity.survey.SurveyQuestionEntity;
import com.experiment.streaming.entity.survey.SurveyResultEntity;
import com.experiment.streaming.model.survey.Result;

import java.util.List;
import java.util.stream.Collectors;

public class SurveyResultMapper {

    public static SurveyResultEntity toEntity(Result result){
        if(result == null)
            return null;
        SurveyResultEntity resultEntity = new SurveyResultEntity();
        resultEntity.setId(result.getId());
        resultEntity.setAnswerText(result.getAnswerText());
        resultEntity.setDiscontinueDate(result.getDiscontinueDate());
        UserEntity userEntity = new UserEntity();
        userEntity.setId(result.getUserId());
        resultEntity.setUser(userEntity);

        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setId(result.getSurveyId());
        resultEntity.setSurvey(surveyEntity);

        SurveyQuestionEntity questionEntity = new SurveyQuestionEntity();
        questionEntity.setId(result.getQuestionId());
        resultEntity.setQuestion(questionEntity);

        SurveyAnswerEntity answerEntity = new SurveyAnswerEntity();
        answerEntity.setId(result.getAnswerId());
        resultEntity.setAnswer(answerEntity);
        return resultEntity;
    }

    public static Result toModel(SurveyResultEntity resultEntity){
        if(resultEntity == null)
            return null;
        Result result = new Result();
        result.setId(resultEntity.getId());
        result.setAnswerText(resultEntity.getAnswerText());
        result.setDiscontinueDate(resultEntity.getDiscontinueDate());
        result.setUserId(resultEntity.getUser().getId());
        result.setSurveyId(resultEntity.getSurvey().getId());
        result.setQuestionId(resultEntity.getQuestion().getId());
        result.setAnswerId(resultEntity.getAnswer().getId());
        return result;
    }

    public static List<Result> toModelList(List<SurveyResultEntity> examResultEntityList){
        return examResultEntityList.stream().map(examResultEntity -> toModel(examResultEntity)).collect(Collectors.toList());
    }

    public static List<SurveyResultEntity> toEntityList(List<Result> resultList){
        return resultList.stream().map(result -> toEntity(result)).collect(Collectors.toList());
    }
}
