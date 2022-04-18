package com.experiment.streaming.repository;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.survey.SurveyAnswerEntity;
import com.experiment.streaming.entity.survey.SurveyEntity;
import com.experiment.streaming.entity.survey.SurveyResultEntity;
import com.experiment.streaming.repository.base.EntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResultRepository extends EntityRepository<SurveyResultEntity> {

    List<SurveyResultEntity> findByUserAndSurvey(UserEntity userEntity, SurveyEntity surveyEntity);

    List<SurveyResultEntity> findByUserAndAnswer(UserEntity userEntity, SurveyAnswerEntity answerEntity);

    List<SurveyResultEntity> findBySurvey(SurveyEntity surveyEntity);

    List<SurveyResultEntity> findByUser(UserEntity userEntity);
}
