package com.experiment.streaming.service;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.survey.SurveyAnswerEntity;
import com.experiment.streaming.entity.survey.SurveyEntity;
import com.experiment.streaming.entity.survey.SurveyResultEntity;
import com.experiment.streaming.repository.SurveyResultRepository;
import com.experiment.streaming.service.base.EntityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyResultService extends EntityService {

    private final SurveyResultRepository surveyResultRepository;

    public SurveyResultService(SurveyResultRepository surveyResultRepository){
        this.surveyResultRepository = surveyResultRepository;
    }

    public List<SurveyResultEntity> getSurveyResultsByUserIdAndSurveyId(Long userId, Long surveyId){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setId(surveyId);
       return surveyResultRepository.findByUserAndSurvey(userEntity,surveyEntity);
    }

    public List<SurveyResultEntity> getSurveyResultsBySurveyId(Long surveyId){
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setId(surveyId);
        return surveyResultRepository.findBySurvey(surveyEntity);
    }

    public List<SurveyResultEntity> getSurveyResultsByUserId(Long userId){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return surveyResultRepository.findByUser(userEntity);
    }

    public List<SurveyResultEntity> getSurveyResultsByUserIdAndAnswerId(Long userId, Long answerId){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        SurveyAnswerEntity surveyAnswerEntity = new SurveyAnswerEntity();
        surveyAnswerEntity.setId(answerId);
        return surveyResultRepository.findByUserAndAnswer(userEntity,surveyAnswerEntity);
    }
}
