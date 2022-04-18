package com.experiment.streaming.controller;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.survey.SurveyEntity;
import com.experiment.streaming.entity.survey.SurveyResultEntity;
import com.experiment.streaming.entity.statistic.VideoStatisticEntity;
import com.experiment.streaming.exception.BusinessExceptions;
import com.experiment.streaming.mapper.statistic.SurveyQuestionStatisticDetailMapper;
import com.experiment.streaming.mapper.statistic.SurveyStatisticMapper;
import com.experiment.streaming.mapper.statistic.VideoStatisticMapper;
import com.experiment.streaming.model.statistic.SurveyStatistic;
import com.experiment.streaming.model.statistic.SurveyStatisticDetail;
import com.experiment.streaming.service.SurveyResultService;
import com.experiment.streaming.service.VideoStatisticService;
import com.experiment.streaming.service.base.BaseCompanyService;
import com.experiment.streaming.service.base.EntityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/statistics")
public class SurveyStatisticController {

    private final EntityService entityService;

    private final BaseCompanyService baseCompanyService;

    private final SurveyResultService surveyResultService;

    private final VideoStatisticService videoStatisticService;

    public SurveyStatisticController(EntityService entityService, SurveyResultService surveyResultService, VideoStatisticService videoStatisticService, BaseCompanyService baseCompanyService) {
        this.entityService = entityService;
        this.surveyResultService = surveyResultService;
        this.videoStatisticService = videoStatisticService;
        this.baseCompanyService = baseCompanyService;
    }

    @RequestMapping(value = "/survey-statistic")
    public String surveyStatistic(@RequestParam(name = "survey-id") Long surveyId, Model model) {
        SurveyEntity surveyEntity = baseCompanyService.find(surveyId, SurveyEntity.class).orElseThrow(() -> BusinessExceptions.EXAM_NOT_FOUND_BY_ID);
        List<SurveyResultEntity> surveyResultEntities = surveyResultService.getSurveyResultsBySurveyId(surveyId);
        List<UserEntity> userEntities = entityService.findAll(UserEntity.class).stream()
                .filter(user -> !(user.getRole().getName().equals("ROLE_ADMIN") || user.getRole().getName().equals("ROLE_COMPANY_ADMIN"))).collect(Collectors.toList());
        List<SurveyStatistic> surveyStatistics = SurveyStatisticMapper.toModelList(userEntities,surveyResultEntities,surveyEntity);
        model.addAttribute("surveyStatistics", surveyStatistics);
        return "statistic/survey-statistic";
    }

    @RequestMapping(value = "/survey-details")
    public String surveyStatisticDetail(@RequestParam(name = "survey-id") Long surveyId, @RequestParam(name = "user-id") Long userId, Model model) {
        SurveyEntity surveyEntity = baseCompanyService.find(surveyId, SurveyEntity.class).orElseThrow(() -> BusinessExceptions.EXAM_NOT_FOUND_BY_ID);
        UserEntity userEntity = baseCompanyService.find(userId,UserEntity.class).orElseThrow(() -> BusinessExceptions.USER_NOT_FOUND);
        SurveyStatisticDetail surveyStatisticDetail = new SurveyStatisticDetail();
        List<SurveyResultEntity> surveyResultEntities = surveyResultService.getSurveyResultsByUserIdAndSurveyId(userId,surveyId);
        surveyStatisticDetail.setQuestions(SurveyQuestionStatisticDetailMapper.toModelList(surveyEntity.getQuestions(),surveyResultEntities));
        surveyStatisticDetail.setSurveyStatistic(SurveyStatisticMapper.toModel(userEntity,surveyEntity,surveyResultEntities));
        model.addAttribute("surveyStatisticDetail",surveyStatisticDetail);
        return "statistic/survey-statistic-detail";
    }
}
