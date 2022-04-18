package com.experiment.streaming.mapper.statistic;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.exam.ExamEntity;
import com.experiment.streaming.entity.exam.ExamResultEntity;
import com.experiment.streaming.mapper.UserMapper;
import com.experiment.streaming.model.statistic.ExamStatistic;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ExamStatisticMapper {

    public static ExamStatistic toModel(UserEntity userEntity, ExamEntity examEntity, List<ExamResultEntity> examResultEntities) {
        ExamStatistic examStatistic = new ExamStatistic();
        examStatistic.setExamId(examEntity.getId());
        examStatistic.setUser(UserMapper.toModel(userEntity));
        AtomicInteger wrongCount = new AtomicInteger();
        AtomicInteger trueCount = new AtomicInteger();
        AtomicInteger answeredCount = new AtomicInteger();

        examResultEntities.stream()
                .filter(examResultEntity -> examResultEntity.getUser().getId().equals(userEntity.getId()))
                .forEach(examResultEntity -> {
                    answeredCount.getAndIncrement();
                    if(examResultEntity.getAnswer().getIsRightAnswer()!=null) {
                        if (examResultEntity.getAnswer().getIsRightAnswer())
                            trueCount.getAndIncrement();
                        else
                            wrongCount.getAndIncrement();
                    } else if(examResultEntity.getIsCorrect() != null){
                        if (examResultEntity.getIsCorrect())
                            trueCount.getAndIncrement();
                        else
                            wrongCount.getAndIncrement();
                    }
                });
        examStatistic.setIsAssessed(trueCount.get()+wrongCount.get() == examEntity.getQuestions().size());
        examStatistic.setExamStatus(examEntity.getQuestions().size() == answeredCount.get() ? "Completed" : "Not Completed");
        examStatistic.setTrueWrongRate(trueCount.get() + "/" + wrongCount.get() + "/" + answeredCount.get());
        return examStatistic;
    }

    public static List<ExamStatistic> toModelList(List<UserEntity> userEntities, List<ExamResultEntity> examResultEntities, ExamEntity examEntity) {
        return userEntities.stream().map(userEntity -> toModel(userEntity,examEntity,examResultEntities)).collect(Collectors.toList());
    }
}
