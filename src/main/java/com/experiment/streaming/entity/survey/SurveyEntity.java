package com.experiment.streaming.entity.survey;

import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.base.BaseCompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "survey")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyEntity extends BaseCompanyEntity {

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
            mappedBy = "survey",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    List<SurveyQuestionEntity> questions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "content_id")
    private ContentEntity content;

}
