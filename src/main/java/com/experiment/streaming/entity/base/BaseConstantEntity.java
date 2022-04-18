package com.experiment.streaming.entity.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@MappedSuperclass
public class BaseConstantEntity extends BaseEntityAudit{

    @Column(name="name")
    @NotEmpty(message = "{constantEntity.name.NotEmpty}")
    @Length(max = 30,message = "{constantEntity.name.Length}")
    protected String name;

    @Column(name = "dsc")
    @Length(max = 255, message = "{constantEntity.desc.Length}")
    protected String dsc;

}
