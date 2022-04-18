package com.experiment.streaming.entity.statistic;

import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.base.BaseEntityAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class StatisticEntity  extends BaseEntityAudit {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name="content_finished")
    private Boolean isContentFinished;

    @Column(name="started_at")
    private Date startedAt;

    @Column(name="finished_at")
    private Date finishedAt;
}
