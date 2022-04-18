package com.experiment.streaming.service.base;

import com.experiment.streaming.entity.base.BaseEntity;
import com.experiment.streaming.repository.base.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EntityService extends BaseService{
    @Autowired
    protected EntityRepository entityRepository;
    @Transactional
    public <T extends BaseEntity> T save(T baseEntity) {
        if(baseEntity != null)
            return (T)entityRepository.save(baseEntity);
        return null;
    }

    @Transactional
    public <T extends BaseEntity> List<T> saveAll(List<T> baseEntities) {
        if(baseEntities != null && baseEntities.size() > 0)
            return (List<T>)entityRepository.saveAll(baseEntities);
        return null;
    }

    public <T extends BaseEntity> Optional<T> find(Long id, Class<T> c) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll();
        T instance = createInstance(c);
        instance.setId(id);
        if(id != null)
            return entityRepository.findOne(Example.of(instance, matcher));
        return null;
    }

    public <T extends BaseEntity> List<T> findAll(Class<T> c) {
        T instance = createInstance(c);
        return entityRepository.findAll(Example.of(instance));
    }

    public <T extends BaseEntity> void delete(Long id, Class<T> c){
        T instance = createInstance(c);
        instance.setId(id);
        entityRepository.delete(instance);
    }
}
