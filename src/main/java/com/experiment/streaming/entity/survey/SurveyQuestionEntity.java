package com.experiment.streaming.entity.survey;

import com.experiment.streaming.entity.base.BaseEntityAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "survey_question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionEntity extends BaseEntityAudit {

    @Column(name="order_number")
    private Integer order;

    @Column(name="question")
    private String question;

    @Column(name="multiple_choice")
    private Boolean isMultipleChoice;

    @Column(name="title")
    private String title;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<SurveyAnswerEntity> answers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyEntity survey;
}
