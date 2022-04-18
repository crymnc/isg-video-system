package com.experiment.streaming.controller;

import com.experiment.streaming.annotation.security.IsSupervisor;
import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.exam.ExamEntity;
import com.experiment.streaming.entity.exam.ExamResultEntity;
import com.experiment.streaming.exception.BusinessExceptions;
import com.experiment.streaming.mapper.exam.ExamResultMapper;
import com.experiment.streaming.mapper.statistic.ExamQuestionStatisticDetailMapper;
import com.experiment.streaming.mapper.statistic.ExamStatisticMapper;
import com.experiment.streaming.model.exam.Result;
import com.experiment.streaming.model.statistic.ExamStatistic;
import com.experiment.streaming.model.statistic.ExamStatisticDetail;
import com.experiment.streaming.service.ExamResultService;
import com.experiment.streaming.service.UserService;
import com.experiment.streaming.service.base.BaseCompanyService;
import com.experiment.streaming.service.base.EntityService;
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
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/assessments")
@Log4j2
public class AssessmentController {

    private final EntityService entityService;
    private final BaseCompanyService baseCompanyService;
    private final ExamResultService examResultService;

    public AssessmentController(EntityService entityService, ExamResultService examResultService, BaseCompanyService baseCompanyService) {
        this.entityService = entityService;
        this.examResultService = examResultService;
        this.baseCompanyService = baseCompanyService;
    }

    @IsSupervisor
    @RequestMapping(value = "/exam-user")
    public String assessmentExamUsersPage(@RequestParam(name = "exam-id") Long examId, @RequestParam(name = "user-id") Long userId, Model model) {
        ExamEntity examEntity = baseCompanyService.find(examId, ExamEntity.class).orElseThrow(() -> BusinessExceptions.EXAM_NOT_FOUND_BY_ID);
        UserEntity userEntity = baseCompanyService.find(userId,UserEntity.class).orElseThrow(() -> BusinessExceptions.USER_NOT_FOUND);
        ExamStatisticDetail examStatisticDetail = new ExamStatisticDetail();
        List<ExamResultEntity> examResultEntities = examResultService.getExamResultsByUserIdAndExamId(userId,examId);
        examStatisticDetail.setQuestions(ExamQuestionStatisticDetailMapper.toModelList(examEntity.getQuestions(),examResultEntities));
        examStatisticDetail.setExamStatistic(ExamStatisticMapper.toModel(userEntity,examEntity,examResultEntities));
        model.addAttribute("examStatisticDetail",examStatisticDetail);
        return "assessment/exam-user";
    }

    @IsSupervisor
    @RequestMapping(value = "/exams")
    public String assessmentExamsPage(@RequestParam(name = "exam-id") Long examId, Model model) {
        ExamEntity examEntity = baseCompanyService.find(examId, ExamEntity.class).orElseThrow(() -> BusinessExceptions.EXAM_NOT_FOUND_BY_ID);
        List<ExamResultEntity> examResultEntities = examResultService.getExamResultsByExamId(examId);
        List<UserEntity> userEntities = entityService.findAll(UserEntity.class).stream()
                .filter(user -> !(user.getRole().getName().equals("ROLE_ADMIN") || user.getRole().getName().equals("ROLE_COMPANY_ADMIN"))).collect(Collectors.toList());
        List<ExamStatistic> examStatistics = ExamStatisticMapper.toModelList(userEntities,examResultEntities,examEntity);
        model.addAttribute("examStatistics", examStatistics);
        return "assessment/exams";
    }

    @IsSupervisor
    @RequestMapping(value = "/complete-assessment", method = RequestMethod.POST)
    public ResponseEntity completeAssessment(@RequestParam(name = "exam-id") Long examId, @RequestParam(name = "user-id") Long userId, @RequestBody @NotNull Map<Long, Boolean> assessments){
        if(!CollectionUtils.isEmpty(assessments)){
            List<ExamResultEntity> singleAnswerResults = examResultService.getExamResultsByUserIdAndExamId(userId, examId)
                    .stream().filter(examResultEntity -> !examResultEntity.getQuestion().getIsMultipleChoice()).collect(Collectors.toList());
            singleAnswerResults.forEach(examResultEntity -> examResultEntity.setIsCorrect(assessments.get(examResultEntity.getAnswer().getId())));
            entityService.saveAll(singleAnswerResults);
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed. No assessment is entered");
    }
}
