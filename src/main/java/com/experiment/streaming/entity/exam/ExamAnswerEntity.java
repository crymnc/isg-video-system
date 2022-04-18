package com.experiment.streaming.entity.exam;

import com.experiment.streaming.entity.base.BaseEntityAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "exam_answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamAnswerEntity extends BaseEntityAudit {

    @Column(name="order_number")
    private Integer order;

    @Column(name="answer")
    private String answer;

    @Column(name="right_answer")
    private Boolean isRightAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private ExamQuestionEntity question;

    @OneToMany(
            mappedBy = "answer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    List<ExamResultEntity> results = new ArrayList<>();
}
