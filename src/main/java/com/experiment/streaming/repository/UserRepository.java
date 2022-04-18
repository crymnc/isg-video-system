package com.experiment.streaming.repository;

import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.repository.base.BaseCompanyRepository;
import com.experiment.streaming.repository.base.EntityRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseCompanyRepository<UserEntity> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByCompany(CompanyEntity company);

    @Transactional
    void deleteByEmail(String email);


}
