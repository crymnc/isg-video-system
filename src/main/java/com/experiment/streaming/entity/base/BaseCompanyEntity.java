package com.experiment.streaming.entity.base;

import com.experiment.streaming.entity.CompanyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class BaseCompanyEntity extends BaseEntityAudit{

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private CompanyEntity company;
}
