package com.experiment.streaming.entity.survey;

import com.experiment.streaming.entity.base.BaseEntityAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "survey_answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswerEntity extends BaseEntityAudit {

    @Column(name="order_number")
    private Integer order;

    @Column(name="answer")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private SurveyQuestionEntity question;
}
