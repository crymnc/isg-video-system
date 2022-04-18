package com.experiment.streaming.entity.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@ToString
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1122124123123L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    protected Long id;

    @Column(name="discontinue_date")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date discontinueDate;

    public Boolean isNew(){
        return getId() == null;
    }

}
