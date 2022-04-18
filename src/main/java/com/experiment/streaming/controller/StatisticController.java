package com.experiment.streaming.controller;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.exam.ExamEntity;
import com.experiment.streaming.entity.exam.ExamResultEntity;
import com.experiment.streaming.entity.statistic.VideoStatisticEntity;
import com.experiment.streaming.exception.BusinessExceptions;
import com.experiment.streaming.mapper.UserSummaryMapper;
import com.experiment.streaming.mapper.statistic.ExamQuestionStatisticDetailMapper;
import com.experiment.streaming.mapper.statistic.ExamStatisticMapper;
import com.experiment.streaming.mapper.statistic.VideoStatisticMapper;
import com.experiment.streaming.model.statistic.ExamStatistic;
import com.experiment.streaming.model.statistic.ExamStatisticDetail;
import com.experiment.streaming.model.statistic.VideoStatistic;
import com.experiment.streaming.service.ExamResultService;
import com.experiment.streaming.service.UserService;
import com.experiment.streaming.service.VideoStatisticService;
import com.experiment.streaming.service.base.BaseCompanyService;
import com.experiment.streaming.service.base.EntityService;
import com.experiment.streaming.utils.LoggedUserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/statistics")
public class StatisticController {

    private final EntityService entityService;

    private final BaseCompanyService baseCompanyService;

    private final ExamResultService examResultService;

    private final VideoStatisticService videoStatisticService;

    public StatisticController(EntityService entityService, ExamResultService examResultService, VideoStatisticService videoStatisticService, BaseCompanyService baseCompanyService) {
        this.entityService = entityService;
        this.examResultService = examResultService;
        this.videoStatisticService = videoStatisticService;
        this.baseCompanyService = baseCompanyService;
    }

    @RequestMapping(value = "/exam-statistic")
    public String examStatistic(@RequestParam(name = "exam-id") Long examId, Model model) {
        ExamEntity examEntity = baseCompanyService.find(examId, ExamEntity.class).orElseThrow(() -> BusinessExceptions.EXAM_NOT_FOUND_BY_ID);
        List<ExamResultEntity> examResultEntities = examResultService.getExamResultsByExamId(examId);
        List<UserEntity> userEntities = entityService.findAll(UserEntity.class).stream()
                .filter(user -> !(user.getRole().getName().equals("ROLE_ADMIN") || user.getRole().getName().equals("ROLE_COMPANY_ADMIN"))).collect(Collectors.toList());
        List<ExamStatistic> examStatistics = ExamStatisticMapper.toModelList(userEntities,examResultEntities,examEntity);
        model.addAttribute("examStatistics", examStatistics);
        return "statistic/exam-statistic";
    }

    @RequestMapping(value = "/exam-details")
    public String examStatisticDetail(@RequestParam(name = "exam-id") Long examId, @RequestParam(name = "user-id") Long userId, Model model) {
        ExamEntity examEntity = baseCompanyService.find(examId, ExamEntity.class).orElseThrow(() -> BusinessExceptions.EXAM_NOT_FOUND_BY_ID);
        UserEntity userEntity = baseCompanyService.find(userId,UserEntity.class).orElseThrow(() -> BusinessExceptions.USER_NOT_FOUND);
        ExamStatisticDetail examStatisticDetail = new ExamStatisticDetail();
        if(examEntity.getContent() != null) {
            List<VideoStatisticEntity> videoStatisticEntities = videoStatisticService.getVideoStatisticByVideoIdAndUserId(examEntity.getContent().getId(), userId);
            examStatisticDetail.setVideoStatistics(VideoStatisticMapper.toModelList(videoStatisticEntities));
        }
        List<ExamResultEntity> examResultEntities = examResultService.getExamResultsByUserIdAndExamId(userId,examId);
        examStatisticDetail.setQuestions(ExamQuestionStatisticDetailMapper.toModelList(examEntity.getQuestions(),examResultEntities));
        examStatisticDetail.setExamStatistic(ExamStatisticMapper.toModel(userEntity,examEntity,examResultEntities));
        model.addAttribute("examStatisticDetail",examStatisticDetail);
        return "statistic/exam-statistic-detail";
    }

    @RequestMapping(value = "/add-video-statistic", method = RequestMethod.POST)
    public ResponseEntity addVideoStatistic(@RequestBody @Valid @NotNull VideoStatistic videoStatistic) {
        videoStatistic.setUserId(UserService.findLoggedUser().getId());
        entityService.save(VideoStatisticMapper.toEntity(videoStatistic));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/video-statistic")
    public String videoStatisticPage(@RequestParam(name = "video-id") Long videoId,Model model) {
        UserEntity loggedUser = UserService.findLoggedUser();
        List<UserEntity> userEntityList;
        if (LoggedUserUtils.isAdmin())
            userEntityList = baseCompanyService.findAll(UserEntity.class);
        else
            userEntityList = baseCompanyService.findAllByCompanyId(loggedUser.getCompany().getId(),UserEntity.class);
        model.addAttribute("users", UserSummaryMapper.toModelList(userEntityList.stream()
                .filter(user -> !(user.getRole().getName().equals("ROLE_ADMIN") || user.getRole().getName().equals("ROLE_COMPANY_ADMIN")))
                .collect(Collectors.toList())));
        model.addAttribute("videoId",videoId);
        return "statistic/video-statistic";
    }

    @RequestMapping(value = "/video-details")
    public String videoStatisticDetail(@RequestParam(name = "video-id") Long videoId, @RequestParam(name = "user-id") Long userId, Model model) {
        List<VideoStatisticEntity> videoStatisticEntities = videoStatisticService.getVideoStatisticByVideoIdAndUserId(videoId, userId);
        model.addAttribute("videoStatistics",VideoStatisticMapper.toModelList(videoStatisticEntities));
        return "statistic/video-statistic-detail";
    }


}
