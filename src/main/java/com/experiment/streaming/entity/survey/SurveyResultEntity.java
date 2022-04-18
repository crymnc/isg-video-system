package com.experiment.streaming.entity.survey;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.base.BaseEntityAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "survey_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResultEntity extends BaseEntityAudit {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyEntity survey;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private SurveyQuestionEntity question;

    @OneToOne
    @JoinColumn(name = "answer_id")
    private SurveyAnswerEntity answer;

    @Column(name = "answer_text")
    private String answerText;

}
