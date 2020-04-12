package com.quickapp.data.repositories;

import com.quickapp.data.entities.DepartmentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<DepartmentEntity,String> {
}
