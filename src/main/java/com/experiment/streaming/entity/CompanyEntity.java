package com.experiment.streaming.entity;

import com.experiment.streaming.entity.base.BaseEntityAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity extends BaseEntityAudit {

    @Column(name = "name")
    private String name;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

}
