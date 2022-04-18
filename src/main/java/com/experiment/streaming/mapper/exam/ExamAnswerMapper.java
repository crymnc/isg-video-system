package com.experiment.streaming.mapper.exam;

import com.experiment.streaming.entity.exam.ExamAnswerEntity;
import com.experiment.streaming.entity.exam.ExamQuestionEntity;
import com.experiment.streaming.model.exam.Answer;

public class ExamAnswerMapper {

    public static ExamAnswerEntity toEntity(Answer answer){
        if(answer == null)
            return null;
        ExamAnswerEntity answerEntity = new ExamAnswerEntity();
        answerEntity.setId(answer.getId());
        answerEntity.setAnswer(answer.getAnswer());
        answerEntity.setOrder(answer.getOrder());
        answerEntity.setIsRightAnswer(answer.getIsRightAnswer());
        answerEntity.setDiscontinueDate(answer.getDiscontinueDate());
        ExamQuestionEntity questionEntity = new ExamQuestionEntity();
        questionEntity.setId(answer.getQuestionId());
        answerEntity.setQuestion(questionEntity);
        return answerEntity;
    }

    public static Answer toModel(ExamAnswerEntity answerEntity){
        if(answerEntity == null)
            return null;
        Answer answer = new Answer();
        answer.setId(answerEntity.getId());
        answer.setAnswer(answerEntity.getAnswer());
        answer.setOrder(answerEntity.getOrder());
        answer.setIsRightAnswer(answerEntity.getIsRightAnswer());
        answer.setDiscontinueDate(answerEntity.getDiscontinueDate());
        answer.setQuestionId(answerEntity.getQuestion().getId());
        return answer;
    }
}
