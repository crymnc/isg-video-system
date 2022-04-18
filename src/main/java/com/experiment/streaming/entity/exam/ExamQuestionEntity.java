package com.experiment.streaming.entity.exam;

import com.experiment.streaming.entity.base.BaseEntityAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "exam_question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamQuestionEntity extends BaseEntityAudit {

    @Column(name="order_number")
    private Integer order;

    @Column(name="question")
    private String question;

    @Column(name="multiple_choice")
    private Boolean isMultipleChoice;

    @Column(name="title")
    private String title;

    @Column(name="image_name")
    private String imageName;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<ExamAnswerEntity> answers = new ArrayList<>();

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    List<ExamResultEntity> results = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private ExamEntity exam;
}
