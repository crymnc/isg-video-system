package com.experiment.streaming.mapper.statistic;

import com.experiment.streaming.entity.exam.ExamQuestionEntity;
import com.experiment.streaming.entity.exam.ExamResultEntity;
import com.experiment.streaming.model.statistic.ExamAnswerStatisticDetail;
import com.experiment.streaming.model.statistic.ExamQuestionStatisticDetail;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExamQuestionStatisticDetailMapper {
    public static ExamQuestionStatisticDetail toModel(ExamQuestionEntity questionEntity, List<ExamResultEntity> resultEntityList) {

        ExamQuestionStatisticDetail examQuestionStatisticDetail = new ExamQuestionStatisticDetail();
        examQuestionStatisticDetail.setId(questionEntity.getId());
        examQuestionStatisticDetail.setQuestion(questionEntity.getQuestion());
        examQuestionStatisticDetail.setTitle(questionEntity.getTitle());
        examQuestionStatisticDetail.setIsMultipleChoice(questionEntity.getIsMultipleChoice());
        examQuestionStatisticDetail.setDiscontinueDate(questionEntity.getDiscontinueDate());
        examQuestionStatisticDetail.setOrder(questionEntity.getOrder());

        questionEntity.getAnswers().forEach(examAnswerEntity -> {
            Optional<ExamResultEntity> userAnsweredResult = resultEntityList.stream().filter(examResultEntity -> examResultEntity.getAnswer().getId().equals(examAnswerEntity.getId())).findFirst();
            ExamAnswerStatisticDetail examAnswerStatisticDetail = new ExamAnswerStatisticDetail();
            examAnswerStatisticDetail.setId(examAnswerEntity.getId());
            if(userAnsweredResult.isPresent()) {
                examAnswerStatisticDetail.setIsUserAnswer(true);
                examAnswerStatisticDetail.setIsUserAnswerCorrect(userAnsweredResult.get().getIsCorrect());
            }
            examAnswerStatisticDetail.setIsRightAnswer(examAnswerEntity.getIsRightAnswer());
            examAnswerStatisticDetail.setDiscontinueDate(examAnswerEntity.getDiscontinueDate());
            examAnswerStatisticDetail.setOrder(examAnswerEntity.getOrder());
            String userAnswerText = null;
            if(userAnsweredResult.isPresent()) {
                if (questionEntity.getIsMultipleChoice())
                    userAnswerText = examAnswerEntity.getAnswer();
                else
                    userAnswerText = userAnsweredResult.get().getAnswerText();
            }
            else{
                userAnswerText = examAnswerEntity.getAnswer();
            }
            examAnswerStatisticDetail.setAnswerText(userAnswerText);
            examQuestionStatisticDetail.getAnswers().add(examAnswerStatisticDetail);
        });

        return examQuestionStatisticDetail;
    }

    public static List<ExamQuestionStatisticDetail> toModelList(List<ExamQuestionEntity> questionEntityList, List<ExamResultEntity> resultEntityList) {
        return questionEntityList.stream().map(questionEntity -> toModel(questionEntity, resultEntityList)).collect(Collectors.toList());
    }
}
