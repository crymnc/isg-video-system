package com.experiment.streaming.controller;

import com.experiment.streaming.annotation.security.IsSupervisor;
import com.experiment.streaming.annotation.security.IsUser;
import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.survey.SurveyEntity;
import com.experiment.streaming.entity.survey.SurveyResultEntity;
import com.experiment.streaming.exception.BusinessExceptions;
import com.experiment.streaming.mapper.CompanyMapper;
import com.experiment.streaming.mapper.ContentMapper;
import com.experiment.streaming.mapper.survey.SurveyMapper;
import com.experiment.streaming.mapper.survey.SurveyResultMapper;
import com.experiment.streaming.model.Company;
import com.experiment.streaming.model.Content;
import com.experiment.streaming.model.survey.SurveyInsertion;
import com.experiment.streaming.model.survey.Result;
import com.experiment.streaming.service.SurveyResultService;
import com.experiment.streaming.service.UserService;
import com.experiment.streaming.service.base.BaseCompanyService;
import com.experiment.streaming.service.base.EntityService;
import com.experiment.streaming.utils.LoggedUserUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/surveys")
@Log4j2
public class SurveyController {

    private final EntityService entityService;

    private final BaseCompanyService baseCompanyService;

    private final SurveyResultService surveyResultService;

    public SurveyController(EntityService entityService, SurveyResultService surveyResultService, BaseCompanyService baseCompanyService) {
        this.entityService = entityService;
        this.surveyResultService = surveyResultService;
        this.baseCompanyService = baseCompanyService;
    }

    @RequestMapping
    public String surveys(Model model) {
        List<SurveyEntity> surveyEntities;
        if (LoggedUserUtils.isAdmin())
            surveyEntities = entityService.findAll(SurveyEntity.class);
        else
            surveyEntities = baseCompanyService.findAllByCompanyId(UserService.findLoggedUser().getCompany().getId(), SurveyEntity.class);

        if(LoggedUserUtils.isUser()){
            HashMap<Long, Boolean> surveyStatus = new HashMap<>();
            surveyEntities.forEach(surveyEntity ->{
                List<SurveyResultEntity> surveyResultEntities = surveyResultService.getSurveyResultsByUserIdAndSurveyId(UserService.findLoggedUser().getId(),surveyEntity.getId());
                surveyStatus.put(surveyEntity.getId(), !CollectionUtils.isEmpty(surveyResultEntities));
            });
            model.addAttribute("surveyStatus", surveyStatus);
        }
        model.addAttribute("surveys", SurveyMapper.toModelList(surveyEntities));
        return "survey/surveys";
    }

    @IsUser
    @RequestMapping(value = "/start")
    public String startSurvey(@RequestParam(name = "survey-id") Long surveyId, Model model) {
        List<SurveyResultEntity> surveyResults = surveyResultService.getSurveyResultsByUserIdAndSurveyId(UserService.findLoggedUser().getId(), surveyId);
        SurveyEntity surveyEntity = baseCompanyService.find(surveyId, SurveyEntity.class).orElseThrow(() -> BusinessExceptions.EXAM_NOT_FOUND_BY_ID);
        if (surveyEntity.getQuestions().size() == surveyResults.size())
            throw BusinessExceptions.EXAM_HAS_BEEN_COMPLETED_BY_USER;

        if (surveyEntity.getContent() != null)
            model.addAttribute("surveyContent", ContentMapper.toModel(surveyEntity.getContent()));
        model.addAttribute("completedQuestions", SurveyResultMapper.toModelList(surveyResults));
        model.addAttribute("survey", SurveyMapper.toInsertionModel(surveyEntity));
        return "survey/start-survey";
    }

    @IsUser
    @RequestMapping(value = "/complete")
    public ResponseEntity completeSurvey(@RequestBody @Valid @NotNull List<Result> results) {
        Long loggedUserId = UserService.findLoggedUser().getId();
        results.forEach(result -> {
            result.setUserId(loggedUserId);
        });
        entityService.saveAll(SurveyResultMapper.toEntityList(results));
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @IsSupervisor
    @RequestMapping(value = "/add")
    public String addSurveyPage(Model model) {
        List<Content> contents;
        if (LoggedUserUtils.isAdmin()) {
            List<Company> companies = CompanyMapper.toModelList(entityService.findAll(CompanyEntity.class));
            contents = ContentMapper.toModelList(entityService.findAll(ContentEntity.class));
            model.addAttribute("companies", companies);
        } else
            contents = ContentMapper.toModelList(baseCompanyService.findAllByCompanyId(UserService.findLoggedUser().getCompany().getId(), ContentEntity.class));
        model.addAttribute("contents", contents);
        return "survey/add";
    }

    @IsSupervisor
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addSurvey(@RequestBody @Valid @NotNull SurveyInsertion survey) {
        baseCompanyService.save(SurveyMapper.toEntity(survey));
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @IsSupervisor
    @RequestMapping(value = "/delete")
    public String deleteContent(@RequestParam(name = "id") Long id) {
        baseCompanyService.delete(id, SurveyEntity.class);
        return "redirect:/surveys";
    }

    @IsSupervisor
    @RequestMapping(value = "/update")
    public String updateSurveyPage(@RequestParam(name = "id") Long id, Model model) {
        List<Content> contents;
        if (LoggedUserUtils.isAdmin()) {
            List<Company> companies = CompanyMapper.toModelList(entityService.findAll(CompanyEntity.class));
            contents = ContentMapper.toModelList(entityService.findAll(ContentEntity.class));
            model.addAttribute("companies", companies);
        } else
            contents = ContentMapper.toModelList(baseCompanyService.findAllByCompanyId(UserService.findLoggedUser().getCompany().getId(), ContentEntity.class));
        model.addAttribute("contents", contents);
        model.addAttribute("survey", SurveyMapper.toInsertionModel(baseCompanyService.find(id, SurveyEntity.class).orElseThrow(() -> BusinessExceptions.EXAM_NOT_FOUND_BY_ID)));
        return "survey/update";
    }

    @IsSupervisor
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateSurvey(@RequestBody @Valid @NotNull SurveyInsertion survey) {
        baseCompanyService.save(SurveyMapper.toEntity(survey));
        return "redirect:/surveys";
    }
}
