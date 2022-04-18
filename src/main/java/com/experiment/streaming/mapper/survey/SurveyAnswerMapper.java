package com.experiment.streaming.mapper.survey;

import com.experiment.streaming.entity.survey.SurveyAnswerEntity;
import com.experiment.streaming.entity.survey.SurveyQuestionEntity;
import com.experiment.streaming.model.survey.Answer;

public class SurveyAnswerMapper {

    public static SurveyAnswerEntity toEntity(Answer answer){
        if(answer == null)
            return null;
        SurveyAnswerEntity answerEntity = new SurveyAnswerEntity();
        answerEntity.setId(answer.getId());
        answerEntity.setAnswer(answer.getAnswer());
        answerEntity.setOrder(answer.getOrder());
        answerEntity.setDiscontinueDate(answer.getDiscontinueDate());
        SurveyQuestionEntity questionEntity = new SurveyQuestionEntity();
        questionEntity.setId(answer.getQuestionId());
        answerEntity.setQuestion(questionEntity);
        return answerEntity;
    }

    public static Answer toModel(SurveyAnswerEntity answerEntity){
        if(answerEntity == null)
            return null;
        Answer answer = new Answer();
        answer.setId(answerEntity.getId());
        answer.setAnswer(answerEntity.getAnswer());
        answer.setOrder(answerEntity.getOrder());
        answer.setDiscontinueDate(answerEntity.getDiscontinueDate());
        answer.setQuestionId(answerEntity.getQuestion().getId());
        return answer;
    }
}
