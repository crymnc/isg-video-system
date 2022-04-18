package com.experiment.streaming.service.base;

import com.experiment.streaming.entity.CompanyEntity;
import com.experiment.streaming.entity.base.BaseCompanyEntity;
import com.experiment.streaming.repository.base.BaseCompanyRepository;
import com.experiment.streaming.utils.LoggedUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BaseCompanyService extends BaseService{
    @Autowired
    protected BaseCompanyRepository baseCompanyRepository;

    @Transactional
    public <T extends BaseCompanyEntity> T save(T baseCompanyEntity) {
        if(baseCompanyEntity != null) {
            if(baseCompanyEntity.getCompany() != null)
                LoggedUserUtils.checkPermissionToCompany(baseCompanyEntity.getCompany().getId());
            return (T) baseCompanyRepository.save(baseCompanyEntity);
        }
        return null;
    }

    @Transactional
    public <T extends BaseCompanyEntity> List<T> saveAll(List<T> baseCompanyEntities) {
        if(baseCompanyEntities != null && baseCompanyEntities.size() > 0) {
            baseCompanyEntities.forEach(baseCompanyEntity -> LoggedUserUtils.checkPermissionToCompany(baseCompanyEntity.getCompany().getId()));
            return (List<T>) baseCompanyRepository.saveAll(baseCompanyEntities);
        }
        return null;
    }

    public <T extends BaseCompanyEntity> Optional<T> find(Long id, Class<T> c) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll();
        T instance = createInstance(c);
        instance.setId(id);
        if(id != null) {
            Optional<T> baseCompanyEntity = baseCompanyRepository.findOne(Example.of(instance, matcher));
            baseCompanyEntity.ifPresent(t -> {
                if(t.getCompany() != null)
                    LoggedUserUtils.checkPermissionToCompany(t.getCompany().getId());
            });
            return baseCompanyEntity;
        }
        return Optional.empty();
    }

    public <T extends BaseCompanyEntity> List<T> findAll(Class<T> c) {
        T instance = createInstance(c);
        List<T> baseCompanyEntities = baseCompanyRepository.findAll(Example.of(instance));
        if(!CollectionUtils.isEmpty(baseCompanyEntities)){
            for(T baseCompanyEntity:baseCompanyEntities){
                if(baseCompanyEntity.getCompany() != null)
                    LoggedUserUtils.checkPermissionToCompany(baseCompanyEntity.getCompany().getId());
            }
        }
        return baseCompanyEntities;
    }

    public <T extends BaseCompanyEntity> List<T> findAllByCompanyId(Long companyId,Class<T> c) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(companyId);
        T instance = createInstance(c);
        instance.setCompany(companyEntity);
        List<T> baseCompanyEntities = baseCompanyRepository.findAll(Example.of(instance));
        if(!CollectionUtils.isEmpty(baseCompanyEntities)){
            for(T baseCompanyEntity:baseCompanyEntities){
                if(baseCompanyEntity.getCompany() != null)
                    LoggedUserUtils.checkPermissionToCompany(baseCompanyEntity.getCompany().getId());
            }
        }
        return baseCompanyEntities;
    }

    public <T extends BaseCompanyEntity> Optional<T> delete(Long id, Class<T> c){
        Optional<T> deleteEntity = find(id,c);
        T instance = createInstance(c);
        instance.setId(id);
        baseCompanyRepository.delete(instance);
        return deleteEntity;
    }
}
