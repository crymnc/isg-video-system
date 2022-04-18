package com.experiment.streaming.repository;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.exam.ExamAnswerEntity;
import com.experiment.streaming.entity.exam.ExamEntity;
import com.experiment.streaming.entity.exam.ExamResultEntity;
import com.experiment.streaming.repository.base.EntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamResultRepository extends EntityRepository<ExamResultEntity> {

    List<ExamResultEntity> findByUserAndExam(UserEntity userEntity, ExamEntity examEntity);

    List<ExamResultEntity> findByUserAndAnswer(UserEntity userEntity, ExamAnswerEntity answerEntity);

    List<ExamResultEntity> findByExam(ExamEntity examEntity);

    List<ExamResultEntity> findByUser(UserEntity userEntity);
}
