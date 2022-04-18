package com.experiment.streaming.mapper.exam;

import com.experiment.streaming.entity.*;
import com.experiment.streaming.entity.exam.ExamAnswerEntity;
import com.experiment.streaming.entity.exam.ExamEntity;
import com.experiment.streaming.entity.exam.ExamQuestionEntity;
import com.experiment.streaming.entity.exam.ExamResultEntity;
import com.experiment.streaming.model.exam.Exam;
import com.experiment.streaming.model.exam.Result;

import java.util.List;
import java.util.stream.Collectors;

public class ExamResultMapper {

    public static ExamResultEntity toEntity(Result result){
        if(result == null)
            return null;
        ExamResultEntity resultEntity = new ExamResultEntity();
        resultEntity.setId(result.getId());
        resultEntity.setAnswerText(result.getAnswerText());
        resultEntity.setDiscontinueDate(result.getDiscontinueDate());
        UserEntity userEntity = new UserEntity();
        userEntity.setId(result.getUserId());
        resultEntity.setUser(userEntity);

        ExamEntity examEntity = new ExamEntity();
        examEntity.setId(result.getExamId());
        resultEntity.setExam(examEntity);

        ExamQuestionEntity questionEntity = new ExamQuestionEntity();
        questionEntity.setId(result.getQuestionId());
        resultEntity.setQuestion(questionEntity);

        ExamAnswerEntity answerEntity = new ExamAnswerEntity();
        answerEntity.setId(result.getAnswerId());
        resultEntity.setAnswer(answerEntity);
        return resultEntity;
    }

    public static Result toModel(ExamResultEntity resultEntity){
        if(resultEntity == null)
            return null;
        Result result = new Result();
        result.setId(resultEntity.getId());
        result.setAnswerText(resultEntity.getAnswerText());
        result.setDiscontinueDate(resultEntity.getDiscontinueDate());
        result.setUserId(resultEntity.getUser().getId());
        result.setExamId(resultEntity.getExam().getId());
        result.setQuestionId(resultEntity.getQuestion().getId());
        result.setAnswerId(resultEntity.getAnswer().getId());
        return result;
    }

    public static List<Result> toModelList(List<ExamResultEntity> examResultEntityList){
        return examResultEntityList.stream().map(examResultEntity -> toModel(examResultEntity)).collect(Collectors.toList());
    }

    public static List<ExamResultEntity> toEntityList(List<Result> resultList){
        return resultList.stream().map(result -> toEntity(result)).collect(Collectors.toList());
    }
}
