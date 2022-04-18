package com.experiment.streaming.entity.base;

import com.experiment.streaming.service.UserService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntityAudit extends BaseEntity{

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    protected Long createdBy;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedAt;

    @Column(name = "updated_by", nullable = false)
    protected Long updatedBy;

    @PrePersist
    private void setCreationParameters() {
        Long userId = UserService.findLoggedUser().getId();
        this.createdBy = userId;
        this.updatedBy = userId;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    private void setUpdateParameters() {
        this.updatedBy = UserService.findLoggedUser().getId();
        this.updatedAt = new Date();
    }


}
