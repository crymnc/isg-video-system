package com.experiment.streaming.mapper.survey;

import com.experiment.streaming.entity.survey.SurveyEntity;
import com.experiment.streaming.entity.survey.SurveyQuestionEntity;
import com.experiment.streaming.model.survey.Question;

import java.util.List;
import java.util.stream.Collectors;

public class SurveyQuestionMapper {

    public static SurveyQuestionEntity toEntity(Question question){
        if(question == null)
            return null;
        SurveyQuestionEntity questionEntity = new SurveyQuestionEntity();
        questionEntity.setId(question.getId());
        questionEntity.setQuestion(question.getQuestion());
        questionEntity.setOrder(question.getOrder());
        questionEntity.setTitle(question.getQuestion());
        questionEntity.setIsMultipleChoice(question.getIsMultipleChoice());
        questionEntity.setDiscontinueDate(question.getDiscontinueDate());
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setId(question.getSurveyId());
        questionEntity.setSurvey(surveyEntity);
        return questionEntity;
    }

    public static Question toModel(SurveyQuestionEntity questionEntity){
        if(questionEntity == null)
            return null;
        Question question = new Question();
        question.setId(questionEntity.getId());
        question.setQuestion(questionEntity.getQuestion());
        question.setOrder(questionEntity.getOrder());
        question.setTitle(questionEntity.getTitle());
        question.setIsMultipleChoice(questionEntity.getIsMultipleChoice());
        question.setDiscontinueDate(questionEntity.getDiscontinueDate());
        question.setSurveyId(questionEntity.getSurvey().getId());
        return question;
    }

    public static List<Question> toModelList(List<SurveyQuestionEntity> entityList){
        return entityList.stream().map(entity -> toModel(entity)).collect(Collectors.toList());
    }

    public static List<SurveyQuestionEntity> toEntityList(List<Question> modelList){
        return modelList.stream().map(model -> toEntity(model)).collect(Collectors.toList());
    }
}
