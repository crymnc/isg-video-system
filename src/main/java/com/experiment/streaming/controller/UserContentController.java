package com.experiment.streaming.controller;

import com.experiment.streaming.annotation.security.IsUser;
import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.statistic.VideoStatisticEntity;
import com.experiment.streaming.mapper.UserContentMapper;
import com.experiment.streaming.service.UserService;
import com.experiment.streaming.service.VideoStatisticService;
import com.experiment.streaming.service.base.BaseCompanyService;
import com.experiment.streaming.service.base.EntityService;
import com.experiment.streaming.utils.LoggedUserUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@IsUser
@Controller
@RequestMapping(value = "/user-contents")
@Log4j2
public class UserContentController {

    private final EntityService entityService;

    private final BaseCompanyService baseCompanyService;
    private final VideoStatisticService videoStatisticService;

    public UserContentController(EntityService entityService, BaseCompanyService baseCompanyService, VideoStatisticService videoStatisticService) {
        this.baseCompanyService = baseCompanyService;
        this.entityService = entityService;
        this.videoStatisticService = videoStatisticService;
    }


    @RequestMapping
    public String userContentsPage(Model model){
        List<ContentEntity> contentEntities;
        if(LoggedUserUtils.isAdmin())
            contentEntities = baseCompanyService.findAll(ContentEntity.class);
        else
            contentEntities = baseCompanyService.findAllByCompanyId(UserService.findLoggedUser().getCompany().getId(), ContentEntity.class);
        List<VideoStatisticEntity> videoStatisticEntities = videoStatisticService.getVideoStatisticByUserId(UserService.findLoggedUser().getId());
        model.addAttribute("userContents", UserContentMapper.toModelList(contentEntities,videoStatisticEntities));
        return "content/user-contents-page";
    }
}
