package com.experiment.streaming.entity;

import com.experiment.streaming.entity.base.BaseConstantEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseConstantEntity {

    @OneToMany(
            mappedBy = "role",
            fetch = FetchType.LAZY
    )
    private Set<UserEntity> users = new HashSet<>();
}
