package com.experiment.streaming.repository.base;

import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.entity.base.BaseCompanyEntity;
import com.experiment.streaming.entity.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseCompanyRepository<T extends BaseCompanyEntity> extends EntityRepository<T>{
}
