package com.zkzong.springboot.mysql.repository;

import com.zkzong.springboot.mysql.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Zong on 2017/3/28.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
