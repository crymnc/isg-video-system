package com.experiment.streaming.entity;

import com.experiment.streaming.entity.base.BaseCompanyEntity;
import com.experiment.streaming.entity.base.BaseEntityAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "content")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentEntity extends BaseCompanyEntity {

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="shortName")
    private String shortName;

    @Column(name="title")
    private String title;

    @Column(name="duration")
    private Double duration;

    @Column(name="path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "content_type_id")
    private ContentTypeEntity contentType;

}
