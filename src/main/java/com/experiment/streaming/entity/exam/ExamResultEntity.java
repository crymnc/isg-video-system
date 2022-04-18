package com.experiment.streaming.entity.exam;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.base.BaseEntityAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "exam_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamResultEntity extends BaseEntityAudit {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private ExamEntity exam;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private ExamQuestionEntity question;

    @OneToOne
    @JoinColumn(name = "answer_id")
    private ExamAnswerEntity answer;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name="correct")
    private Boolean isCorrect;

}
