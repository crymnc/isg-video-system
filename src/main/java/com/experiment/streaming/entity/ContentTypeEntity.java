package com.experiment.streaming.entity;

import com.experiment.streaming.entity.base.BaseConstantEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "content_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentTypeEntity extends BaseConstantEntity {
    @OneToMany(
            mappedBy = "contentType",
            fetch = FetchType.LAZY
    )
    private Set<ContentEntity> contents = new HashSet<>();
}
