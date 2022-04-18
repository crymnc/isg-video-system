package com.experiment.streaming.mapper.survey;

import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.survey.SurveyAnswerEntity;
import com.experiment.streaming.entity.survey.SurveyEntity;
import com.experiment.streaming.entity.survey.SurveyQuestionEntity;
import com.experiment.streaming.model.survey.Answer;
import com.experiment.streaming.model.survey.Survey;
import com.experiment.streaming.model.survey.SurveyInsertion;
import com.experiment.streaming.model.survey.QuestionInsertion;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SurveyMapper {

    public static SurveyEntity toEntity(Survey survey){
        if(survey == null)
            return null;
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setId(survey.getId());
        surveyEntity.setName(survey.getName());
        surveyEntity.setTitle(survey.getTitle());
        surveyEntity.setDescription(survey.getDescription());
        surveyEntity.setStartDate(survey.getStartDate());
        surveyEntity.setFinishDate(survey.getFinishDate());
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(survey.getCompanyId());
        surveyEntity.setCompany(companyEntity);
        surveyEntity.setDiscontinueDate(survey.getDiscontinueDate());
        return surveyEntity;
    }

    public static Survey toModel(SurveyEntity surveyEntity){
        if (surveyEntity == null)
            return null;
        Survey survey = new Survey();
        survey.setId(surveyEntity.getId());
        survey.setName(surveyEntity.getName());
        survey.setTitle(surveyEntity.getTitle());
        survey.setDescription(surveyEntity.getDescription());
        survey.setStartDate(surveyEntity.getStartDate());
        survey.setFinishDate(surveyEntity.getFinishDate());
        survey.setCompanyId(surveyEntity.getCompany().getId());
        survey.setDiscontinueDate(surveyEntity.getDiscontinueDate());
        return survey;
    }

    public static SurveyEntity toEntity(SurveyInsertion surveyInsertion){
        if(surveyInsertion == null)
            return null;
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setId(surveyInsertion.getId());
        surveyEntity.setName(surveyInsertion.getName());
        surveyEntity.setTitle(surveyInsertion.getTitle());
        surveyEntity.setDescription(surveyInsertion.getDescription());
        surveyEntity.setStartDate(surveyInsertion.getStartDate());
        surveyEntity.setFinishDate(surveyInsertion.getFinishDate());
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(surveyInsertion.getCompanyId());
        surveyEntity.setCompany(companyEntity);
        if(surveyInsertion.getContentId() != null && surveyInsertion.getContentId() != -1L) {
            ContentEntity contentEntity = new ContentEntity();
            contentEntity.setId(surveyInsertion.getContentId());
            surveyEntity.setContent(contentEntity);
        }
        surveyEntity.setDiscontinueDate(surveyInsertion.getDiscontinueDate());

        for(QuestionInsertion questionInsertion:surveyInsertion.getQuestions().stream().sorted(Comparator.comparing(QuestionInsertion::getOrder)).collect(Collectors.toList())){
            SurveyQuestionEntity surveyQuestionEntity = new SurveyQuestionEntity();
            surveyQuestionEntity.setId(questionInsertion.getId());
            surveyQuestionEntity.setQuestion(questionInsertion.getQuestion());
            surveyQuestionEntity.setTitle(questionInsertion.getTitle());
            surveyQuestionEntity.setIsMultipleChoice(questionInsertion.getIsMultipleChoice());
            surveyQuestionEntity.setDiscontinueDate(questionInsertion.getDiscontinueDate());
            surveyQuestionEntity.setOrder(questionInsertion.getOrder());
            if(!questionInsertion.getIsMultipleChoice()){
                SurveyAnswerEntity surveyAnswerEntity = new SurveyAnswerEntity();
                if(questionInsertion.getAnswers().size()>0)
                    surveyAnswerEntity.setId(questionInsertion.getAnswers().get(0).getId());
                surveyAnswerEntity.setOrder(1);
                surveyAnswerEntity.setQuestion(surveyQuestionEntity);
                surveyQuestionEntity.getAnswers().add(surveyAnswerEntity);
            }else {
                for (Answer answer : questionInsertion.getAnswers().stream().sorted(Comparator.comparing(Answer::getOrder)).collect(Collectors.toList())) {
                    SurveyAnswerEntity surveyAnswerEntity = new SurveyAnswerEntity();
                    surveyAnswerEntity.setId(answer.getId());
                    surveyAnswerEntity.setAnswer(answer.getAnswer());
                    surveyAnswerEntity.setDiscontinueDate(answer.getDiscontinueDate());
                    surveyAnswerEntity.setOrder(answer.getOrder());
                    surveyAnswerEntity.setQuestion(surveyQuestionEntity);
                    surveyQuestionEntity.getAnswers().add(surveyAnswerEntity);
                }
            }
            surveyQuestionEntity.setSurvey(surveyEntity);
            surveyEntity.getQuestions().add(surveyQuestionEntity);
        }
        return surveyEntity;
    }

    public static SurveyInsertion toInsertionModel(SurveyEntity surveyEntity){
        if(surveyEntity == null)
            return null;
        SurveyInsertion surveyInsertion = new SurveyInsertion();
        surveyInsertion.setId(surveyEntity.getId());
        surveyInsertion.setName(surveyEntity.getName());
        surveyInsertion.setTitle(surveyEntity.getTitle());
        surveyInsertion.setDescription(surveyEntity.getDescription());
        surveyInsertion.setStartDate(surveyEntity.getStartDate());
        surveyInsertion.setFinishDate(surveyEntity.getFinishDate());
        surveyInsertion.setCompanyId(surveyEntity.getCompany().getId());
        if(surveyEntity.getContent() != null)
            surveyInsertion.setContentId(surveyEntity.getContent().getId());
        surveyInsertion.setDiscontinueDate(surveyEntity.getDiscontinueDate());

        for(SurveyQuestionEntity questionEntity:surveyEntity.getQuestions().stream().sorted(Comparator.comparing(SurveyQuestionEntity::getOrder)).collect(Collectors.toList())){
            QuestionInsertion questionInsertion = new QuestionInsertion();
            questionInsertion.setId(questionEntity.getId());
            questionInsertion.setQuestion(questionEntity.getQuestion());
            questionInsertion.setTitle(questionEntity.getTitle());
            questionInsertion.setIsMultipleChoice(questionEntity.getIsMultipleChoice());
            questionInsertion.setDiscontinueDate(questionEntity.getDiscontinueDate());
            questionInsertion.setOrder(questionEntity.getOrder());
            for(SurveyAnswerEntity answerEntity:questionEntity.getAnswers().stream().sorted(Comparator.comparing(SurveyAnswerEntity::getOrder)).collect(Collectors.toList())){
                Answer answer = new Answer();
                answer.setId(answerEntity.getId());
                answer.setAnswer(answerEntity.getAnswer());
                answer.setDiscontinueDate(answerEntity.getDiscontinueDate());
                answer.setOrder(answerEntity.getOrder());
                questionInsertion.getAnswers().add(answer);
            }
            surveyInsertion.getQuestions().add(questionInsertion);
        }
        return surveyInsertion;
    }

    public static List<Survey> toModelList(List<SurveyEntity> surveyEntities){
        return surveyEntities.stream().map(surveyEntity -> toModel(surveyEntity)).collect(Collectors.toList());
    }

    public static List<SurveyEntity> toEntityList(List<Survey> surveys){
        return surveys.stream().map(survey -> toEntity(survey)).collect(Collectors.toList());
    }
}
