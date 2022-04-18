package com.experiment.streaming.mapper.exam;

import com.experiment.streaming.entity.exam.ExamEntity;
import com.experiment.streaming.entity.exam.ExamQuestionEntity;
import com.experiment.streaming.entity.statistic.VideoStatisticEntity;
import com.experiment.streaming.model.exam.Question;
import com.experiment.streaming.model.statistic.VideoStatistic;

import java.util.List;
import java.util.stream.Collectors;

public class ExamQuestionMapper {

    public static ExamQuestionEntity toEntity(Question question){
        if(question == null)
            return null;
        ExamQuestionEntity questionEntity = new ExamQuestionEntity();
        questionEntity.setId(question.getId());
        questionEntity.setQuestion(question.getQuestion());
        questionEntity.setOrder(question.getOrder());
        questionEntity.setTitle(question.getQuestion());
        questionEntity.setIsMultipleChoice(question.getIsMultipleChoice());
        questionEntity.setDiscontinueDate(question.getDiscontinueDate());
        ExamEntity examEntity = new ExamEntity();
        examEntity.setId(question.getExamId());
        questionEntity.setExam(examEntity);
        return questionEntity;
    }

    public static Question toModel(ExamQuestionEntity questionEntity){
        if(questionEntity == null)
            return null;
        Question question = new Question();
        question.setId(questionEntity.getId());
        question.setQuestion(questionEntity.getQuestion());
        question.setOrder(questionEntity.getOrder());
        question.setTitle(questionEntity.getTitle());
        question.setIsMultipleChoice(questionEntity.getIsMultipleChoice());
        question.setDiscontinueDate(questionEntity.getDiscontinueDate());
        question.setExamId(questionEntity.getExam().getId());
        return question;
    }

    public static List<Question> toModelList(List<ExamQuestionEntity> entityList){
        return entityList.stream().map(entity -> toModel(entity)).collect(Collectors.toList());
    }

    public static List<ExamQuestionEntity> toEntityList(List<Question> modelList){
        return modelList.stream().map(model -> toEntity(model)).collect(Collectors.toList());
    }
}
