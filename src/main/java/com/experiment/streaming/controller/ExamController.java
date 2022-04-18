package com.experiment.streaming.controller;

import com.experiment.streaming.annotation.security.IsSupervisor;
import com.experiment.streaming.annotation.security.IsUser;
import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.exam.ExamEntity;
import com.experiment.streaming.entity.exam.ExamResultEntity;
import com.experiment.streaming.entity.statistic.VideoStatisticEntity;
import com.experiment.streaming.exception.BusinessExceptions;
import com.experiment.streaming.mapper.CompanyMapper;
import com.experiment.streaming.mapper.ContentMapper;
import com.experiment.streaming.mapper.exam.ExamMapper;
import com.experiment.streaming.mapper.exam.ExamResultMapper;
import com.experiment.streaming.mapper.statistic.ExamQuestionStatisticDetailMapper;
import com.experiment.streaming.mapper.statistic.ExamStatisticMapper;
import com.experiment.streaming.mapper.statistic.VideoStatisticMapper;
import com.experiment.streaming.model.Company;
import com.experiment.streaming.model.Content;
import com.experiment.streaming.model.exam.ExamInsertion;
import com.experiment.streaming.model.exam.Result;
import com.experiment.streaming.model.statistic.ExamStatistic;
import com.experiment.streaming.model.statistic.ExamStatisticDetail;
import com.experiment.streaming.service.ExamResultService;
import com.experiment.streaming.service.UserService;
import com.experiment.streaming.service.base.BaseCompanyService;
import com.experiment.streaming.service.base.EntityService;
import com.experiment.streaming.utils.LoggedUserUtils;
import com.experiment.streaming.utils.PathUtils;
import com.experiment.streaming.utils.uploader.Uploader;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/exams")
@Log4j2
public class ExamController {

    private final EntityService entityService;

    private final BaseCompanyService baseCompanyService;

    private final ExamResultService examResultService;

    public ExamController(EntityService entityService, ExamResultService examResultService, BaseCompanyService baseCompanyService) {
        this.entityService = entityService;
        this.examResultService = examResultService;
        this.baseCompanyService = baseCompanyService;
    }

    @RequestMapping
    public String exams(Model model) {
        List<ExamEntity> examEntities;
        if (LoggedUserUtils.isAdmin())
            examEntities = entityService.findAll(ExamEntity.class);
        else
            examEntities = baseCompanyService.findAllByCompanyId(UserService.findLoggedUser().getCompany().getId(), ExamEntity.class);

        if(LoggedUserUtils.isUser()){
            HashMap<Long, Boolean> examStatus = new HashMap<>();
            examEntities.forEach(examEntity ->{
                List<ExamResultEntity> examResultEntities = examResultService.getExamResultsByUserIdAndExamId(UserService.findLoggedUser().getId(),examEntity.getId());
                examStatus.put(examEntity.getId(), !CollectionUtils.isEmpty(examResultEntities));
            });
            model.addAttribute("examStatus", examStatus);
        }
        model.addAttribute("exams", ExamMapper.toModelList(examEntities));
        return "exam/exams";
    }

    @IsUser
    @RequestMapping(value = "/start")
    public String startExam(@RequestParam(name = "exam-id") Long examId, Model model) {
        List<ExamResultEntity> examResults = examResultService.getExamResultsByUserIdAndExamId(UserService.findLoggedUser().getId(), examId);
        ExamEntity examEntity = baseCompanyService.find(examId, ExamEntity.class).orElseThrow(() -> BusinessExceptions.EXAM_NOT_FOUND_BY_ID);
        if (examEntity.getQuestions().size() == examResults.size())
            throw BusinessExceptions.EXAM_HAS_BEEN_COMPLETED_BY_USER;

        if (examEntity.getContent() != null)
            model.addAttribute("examContent", ContentMapper.toModel(examEntity.getContent()));
        model.addAttribute("completedQuestions", ExamResultMapper.toModelList(examResults));
        model.addAttribute("exam", ExamMapper.toInsertionModel(examEntity));
        return "exam/start-exam";
    }

    @IsUser
    @RequestMapping(value = "/complete")
    public ResponseEntity completeExam(@RequestBody @Valid @NotNull List<Result> results) {
        Long loggedUserId = UserService.findLoggedUser().getId();
        results.forEach(result -> {
            result.setUserId(loggedUserId);
        });
        entityService.saveAll(ExamResultMapper.toEntityList(results));
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @IsSupervisor
    @RequestMapping(value = "/add")
    public String addExamPage(Model model) {
        List<Content> contents;
        if (LoggedUserUtils.isAdmin()) {
            List<Company> companies = CompanyMapper.toModelList(entityService.findAll(CompanyEntity.class));
            contents = ContentMapper.toModelList(entityService.findAll(ContentEntity.class));
            model.addAttribute("companies", companies);
        } else
            contents = ContentMapper.toModelList(baseCompanyService.findAllByCompanyId(UserService.findLoggedUser().getCompany().getId(), ContentEntity.class));
        model.addAttribute("contents", contents);
        return "exam/add";
    }

    @IsSupervisor
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = { "multipart/form-data"})
    public ResponseEntity addExam(@Nullable @RequestPart("files") List<MultipartFile> files, @RequestPart("exam") ExamInsertion exam) {
        baseCompanyService.save(ExamMapper.toEntity(exam,files));
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @IsSupervisor
    @RequestMapping(value = "/delete")
    public String deleteExam(@RequestParam(name = "id") Long id) {
        Optional<ExamEntity> deletedEntity = baseCompanyService.delete(id, ExamEntity.class);
        deletedEntity.ifPresent(examEntity -> examEntity.getQuestions().forEach(questionEntity -> {
            if(questionEntity.getImageName() != null) {
                try {
                    Files.delete(Paths.get(PathUtils.UPLOAD_IMAGE_DIR, questionEntity.getImageName()));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }));
        return "redirect:/exams";
    }

    @IsSupervisor
    @RequestMapping(value = "/update")
    public String updateExamPage(@RequestParam(name = "id") Long id, Model model) {
        List<Content> contents;
        if (LoggedUserUtils.isAdmin()) {
            List<Company> companies = CompanyMapper.toModelList(entityService.findAll(CompanyEntity.class));
            contents = ContentMapper.toModelList(entityService.findAll(ContentEntity.class));
            model.addAttribute("companies", companies);
        } else
            contents = ContentMapper.toModelList(baseCompanyService.findAllByCompanyId(UserService.findLoggedUser().getCompany().getId(), ContentEntity.class));
        model.addAttribute("contents", contents);
        model.addAttribute("exam", ExamMapper.toInsertionModel(baseCompanyService.find(id, ExamEntity.class).orElseThrow(() -> BusinessExceptions.EXAM_NOT_FOUND_BY_ID)));
        return "exam/update";
    }

    @IsSupervisor
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = { "multipart/form-data"})
    public ResponseEntity updateExam(@Nullable @RequestPart("files") List<MultipartFile> files,  @RequestPart("exam") ExamInsertion exam) {
        baseCompanyService.save(ExamMapper.toEntity(exam,files));
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
