package com.experiment.streaming.controller;

import com.experiment.streaming.annotation.security.IsSupervisor;
import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.ContentTypeEntity;
import com.experiment.streaming.entity.statistic.VideoStatisticEntity;
import com.experiment.streaming.exception.BusinessException;
import com.experiment.streaming.exception.BusinessExceptions;
import com.experiment.streaming.mapper.CompanyMapper;
import com.experiment.streaming.mapper.ConstantMapper;
import com.experiment.streaming.mapper.ContentMapper;
import com.experiment.streaming.mapper.UserContentMapper;
import com.experiment.streaming.mapper.statistic.VideoStatisticMapper;
import com.experiment.streaming.model.Company;
import com.experiment.streaming.model.Content;
import com.experiment.streaming.model.ContentType;
import com.experiment.streaming.service.UserService;
import com.experiment.streaming.service.VideoStatisticService;
import com.experiment.streaming.service.base.BaseCompanyService;
import com.experiment.streaming.service.base.EntityService;
import com.experiment.streaming.utils.LoggedUserUtils;
import com.experiment.streaming.utils.PathUtils;
import com.experiment.streaming.utils.uploader.Uploader;
import com.experiment.streaming.utils.uploader.VideoUploader;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@IsSupervisor
@Controller
@RequestMapping(value = "/contents")
@Log4j2
public class ContentController {

    private final EntityService entityService;

    private final BaseCompanyService baseCompanyService;
    private final VideoStatisticService videoStatisticService;

    public ContentController(EntityService entityService, BaseCompanyService baseCompanyService,VideoStatisticService videoStatisticService) {
        this.baseCompanyService = baseCompanyService;
        this.entityService = entityService;
        this.videoStatisticService = videoStatisticService;
    }

    @RequestMapping
    public String contentPage(Model model) {
        List<ContentEntity> contentEntities;
        if(LoggedUserUtils.isAdmin())
            contentEntities = baseCompanyService.findAll(ContentEntity.class);
        else
            contentEntities = baseCompanyService.findAllByCompanyId(UserService.findLoggedUser().getCompany().getId(), ContentEntity.class);
        model.addAttribute("contents", ContentMapper.toModelList(contentEntities));
        return "content/contents";
    }

    @RequestMapping(value = "/add")
    public String addContentPage(Model model) {
        if(LoggedUserUtils.isAdmin()) {
            List<Company> companies = CompanyMapper.toModelList(entityService.findAll(CompanyEntity.class));
            model.addAttribute("companies", companies);
        }
        List<ContentType> contentTypes = ConstantMapper.toModelList(entityService.findAll(ContentTypeEntity.class));
        model.addAttribute("contentTypes", contentTypes);
        return "content/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addContent(@RequestParam("file") MultipartFile file, @ModelAttribute("content") @Valid Content content) {
        if (file != null && !file.isEmpty()) {
            try {
                VideoUploader videoConverter = Uploader.getVideoUploader(file);
                videoConverter.upload();
                content.setPath(videoConverter.getConvertedFileShortPath().toString());
                content.setDuration(videoConverter.getProbeResult().getFormat().duration);
                baseCompanyService.save(ContentMapper.toEntity(content));
            }catch (IOException e){
                log.error(e);
                throw new BusinessException("Video conversion is failed. Please control video content or contact with administration");
            }catch (Exception e){
                log.error(e);
                throw new BusinessException("Please contact with administration");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @RequestMapping(value = "/update")
    public String updateContentPage(@RequestParam(name = "id") Long id, Model model) {
        if(LoggedUserUtils.isAdmin()) {
            List<Company> companies = CompanyMapper.toModelList(entityService.findAll(CompanyEntity.class));
            model.addAttribute("companies", companies);
        }
        List<ContentType> contentTypes = ConstantMapper.toModelList(entityService.findAll(ContentTypeEntity.class));
        model.addAttribute("contentTypes", contentTypes);
        model.addAttribute("appContent", ContentMapper.toModel(entityService.find(id, ContentEntity.class).orElseThrow(() -> BusinessExceptions.CONTENT_NOT_FOUND_BY_ID)));
        return "content/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateContent(@RequestParam("file") MultipartFile file,@ModelAttribute("content") Content content) {
        ContentEntity contentEntity = baseCompanyService.find(content.getId(), ContentEntity.class).orElseThrow(() -> BusinessExceptions.CONTENT_NOT_FOUND_BY_ID);
        if (file != null && !file.isEmpty()) {
            try {
                Files.delete(Paths.get(PathUtils.RESOURCES_STATIC + contentEntity.getPath()));
            }
            catch (IOException e){
                log.error(e);
            }
            try {
                VideoUploader videoConverter = Uploader.getVideoUploader(file);
                videoConverter.upload();
                content.setPath(videoConverter.getConvertedFileShortPath().toString());
                content.setDuration(videoConverter.getProbeResult().getFormat().duration);
            }catch (IOException e){
                log.error(e);
                throw new BusinessException("Video conversion is failed. Please control video content or contact with administration");
            }catch (Exception e){
                log.error(e);
                throw new BusinessException("Please contact with administration");
            }
        }else{
            content.setPath(contentEntity.getPath());
            content.setDuration(contentEntity.getDuration());
        }
        baseCompanyService.save(ContentMapper.toEntity(content));
        return "redirect:/contents";
    }

    @RequestMapping(value = "/delete")
    public String deleteContent(@RequestParam(name = "id") Long id) {
        ContentEntity content = baseCompanyService.find(id,ContentEntity.class).orElseThrow(() -> BusinessExceptions.CONTENT_NOT_FOUND_BY_ID);
        try {
            Files.delete(Paths.get(content.getPath()));
        }catch (IOException e){

        }
        entityService.delete(id, ContentEntity.class);
        return "redirect:/contents";
    }

    @RequestMapping(value = "/user-contents-page")
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
