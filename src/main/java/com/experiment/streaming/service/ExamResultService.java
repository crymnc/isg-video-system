package com.experiment.streaming.service;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.exam.ExamAnswerEntity;
import com.experiment.streaming.entity.exam.ExamEntity;
import com.experiment.streaming.entity.exam.ExamResultEntity;
import com.experiment.streaming.repository.ExamResultRepository;
import com.experiment.streaming.service.base.EntityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamResultService extends EntityService {

    private final ExamResultRepository examResultRepository;

    public ExamResultService(ExamResultRepository examResultRepository){
        this.examResultRepository = examResultRepository;
    }

    public List<ExamResultEntity> getExamResultsByUserIdAndExamId(Long userId, Long examId){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        ExamEntity examEntity = new ExamEntity();
        examEntity.setId(examId);
       return examResultRepository.findByUserAndExam(userEntity,examEntity);
    }

    public List<ExamResultEntity> getExamResultsByExamId(Long examId){
        ExamEntity examEntity = new ExamEntity();
        examEntity.setId(examId);
        return examResultRepository.findByExam(examEntity);
    }

    public List<ExamResultEntity> getExamResultsByUserId(Long userId){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return examResultRepository.findByUser(userEntity);
    }

    public List<ExamResultEntity> getExamResultsByUserIdAndAnswerId(Long userId, Long answerId){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        ExamAnswerEntity examAnswerEntity = new ExamAnswerEntity();
        examAnswerEntity.setId(answerId);
        return examResultRepository.findByUserAndAnswer(userEntity,examAnswerEntity);
    }
}
