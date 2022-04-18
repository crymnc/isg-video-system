package com.experiment.streaming.mapper.statistic;

import com.experiment.streaming.entity.survey.SurveyQuestionEntity;
import com.experiment.streaming.entity.survey.SurveyResultEntity;
import com.experiment.streaming.model.statistic.SurveyAnswerStatisticDetail;
import com.experiment.streaming.model.statistic.SurveyQuestionStatisticDetail;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SurveyQuestionStatisticDetailMapper {
    public static SurveyQuestionStatisticDetail toModel(SurveyQuestionEntity questionEntity, List<SurveyResultEntity> resultEntityList) {

        SurveyQuestionStatisticDetail surveyQuestionStatisticDetail = new SurveyQuestionStatisticDetail();
        surveyQuestionStatisticDetail.setId(questionEntity.getId());
        surveyQuestionStatisticDetail.setQuestion(questionEntity.getQuestion());
        surveyQuestionStatisticDetail.setTitle(questionEntity.getTitle());
        surveyQuestionStatisticDetail.setIsMultipleChoice(questionEntity.getIsMultipleChoice());
        surveyQuestionStatisticDetail.setDiscontinueDate(questionEntity.getDiscontinueDate());
        surveyQuestionStatisticDetail.setOrder(questionEntity.getOrder());

        questionEntity.getAnswers().forEach(surveyAnswerEntity -> {
            Optional<SurveyResultEntity> userAnsweredResult = resultEntityList.stream().filter(surveyResultEntity -> surveyResultEntity.getAnswer().getId().equals(surveyAnswerEntity.getId())).findFirst();
            SurveyAnswerStatisticDetail surveyAnswerStatisticDetail = new SurveyAnswerStatisticDetail();
            surveyAnswerStatisticDetail.setId(surveyAnswerEntity.getId());
            surveyAnswerStatisticDetail.setDiscontinueDate(surveyAnswerEntity.getDiscontinueDate());
            surveyAnswerStatisticDetail.setOrder(surveyAnswerEntity.getOrder());
            String userAnswerText = null;
            if(userAnsweredResult.isPresent()) {
                if (questionEntity.getIsMultipleChoice())
                    userAnswerText = surveyAnswerEntity.getAnswer();
                else
                    userAnswerText = userAnsweredResult.get().getAnswerText();
            }
            else{
                userAnswerText = surveyAnswerEntity.getAnswer();
            }
            surveyAnswerStatisticDetail.setAnswerText(userAnswerText);
            surveyQuestionStatisticDetail.getAnswers().add(surveyAnswerStatisticDetail);
        });

        return surveyQuestionStatisticDetail;
    }

    public static List<SurveyQuestionStatisticDetail> toModelList(List<SurveyQuestionEntity> questionEntityList, List<SurveyResultEntity> resultEntityList) {
        return questionEntityList.stream().map(questionEntity -> toModel(questionEntity, resultEntityList)).collect(Collectors.toList());
    }
}
