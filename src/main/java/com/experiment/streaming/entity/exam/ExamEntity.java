package com.experiment.streaming.entity.exam;

import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.base.BaseCompanyEntity;
import com.experiment.streaming.entity.base.BaseEntityAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "exam")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamEntity extends BaseCompanyEntity {

    @Column(name="name")
    private String name;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="startDate")
    private Date startDate;

    @Column(name="finishDate")
    private Date finishDate;

    @OneToMany(
            mappedBy = "exam",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    List<ExamQuestionEntity> questions = new ArrayList<>();

    @OneToMany(
            mappedBy = "exam",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    List<ExamResultEntity> results = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "content_id")
    private ContentEntity content;

}
