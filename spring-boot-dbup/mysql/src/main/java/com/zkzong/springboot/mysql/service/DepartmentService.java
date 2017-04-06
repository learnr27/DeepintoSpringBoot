package com.zkzong.springboot.mysql.service;

import com.zkzong.springboot.dbexpand.jpa.parameter.LinkEnum;
import com.zkzong.springboot.dbexpand.jpa.parameter.Operator;
import com.zkzong.springboot.dbexpand.jpa.parameter.PredicateBuilder;
import com.zkzong.springboot.mysql.entity.Department;
import com.zkzong.springboot.mysql.model.DepartmentQo;
import com.zkzong.springboot.mysql.redis.DepartmentRedis;
import com.zkzong.springboot.mysql.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentRedis departmentRedis;


    @Cacheable(value = "mysql:findById:deparment", keyGenerator = "simpleKey")
    public Department findById(Long id) {
        return departmentRepository.findOne(id);
    }

    @CachePut(value = "mysql:findById:deparment", keyGenerator = "objectId")
    public Department create(Department deparment) {
        return departmentRepository.save(deparment);
    }

    @CachePut(value = "mysql:findById:deparment", keyGenerator = "objectId")
    public Department update(Department role) {
        return departmentRepository.save(role);
    }

    @CacheEvict(value = "mysql:findById:deparment", keyGenerator = "simpleKey")
    public void delete(Long id) {
        departmentRepository.delete(id);
    }

    public List<Department> findAll() {
        List<Department> deparments = departmentRedis.getList("mysql:findAll:deparment");
        if (deparments == null) {
            deparments = departmentRepository.findAll();
            if (deparments != null)
                departmentRedis.add("mysql:findAll:deparment", 5L, deparments);
        }
        return deparments;
    }

    public Page<Department> findPage(DepartmentQo deparmentQo) {
        Pageable pageable = new PageRequest(deparmentQo.getPage(), deparmentQo.getSize(), new Sort(Sort.Direction.ASC, "id"));

        PredicateBuilder pb = new PredicateBuilder();

        if (!StringUtils.isEmpty(deparmentQo.getName())) {
            pb.add("name", "%" + deparmentQo.getName() + "%", LinkEnum.LIKE);
        }

        Page<Department> pages = departmentRepository.findAll(pb.build(), Operator.AND, pageable);
        return pages;
    }

}
