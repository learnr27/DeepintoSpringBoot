package com.zkzong.springboot.mysql.repository;

import com.zkzong.springboot.dbexpand.jpa.repository.ExpandJpaRepository;
import com.zkzong.springboot.mysql.entity.Department;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends ExpandJpaRepository<Department, Long> {
}
