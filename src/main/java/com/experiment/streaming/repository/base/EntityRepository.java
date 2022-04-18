package com.experiment.streaming.repository.base;

import com.experiment.streaming.entity.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository<T extends BaseEntity>  extends JpaRepository<T, Long>, JpaSpecificationExecutor<T>{

}
