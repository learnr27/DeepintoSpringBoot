package com.zkzong.springboot.mysql.service;

import com.zkzong.springboot.dbexpand.jpa.parameter.LinkEnum;
import com.zkzong.springboot.dbexpand.jpa.parameter.Operator;
import com.zkzong.springboot.dbexpand.jpa.parameter.PredicateBuilder;
import com.zkzong.springboot.mysql.entity.Role;
import com.zkzong.springboot.mysql.model.RoleQo;
import com.zkzong.springboot.mysql.redis.RoleRedis;
import com.zkzong.springboot.mysql.repository.RoleRepository;
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
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleRedis roleRedis;

    @Cacheable(value = "mysql:findById:role", keyGenerator = "simpleKey")
    public Role findById(Long id) {
        return roleRepository.findOne(id);
    }

    @CachePut(value = "mysql:findById:role", keyGenerator = "objectId")
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @CachePut(value = "mysql:findById:role", keyGenerator = "objectId")
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @CacheEvict(value = "mysql:findById:role", keyGenerator = "simpleKey")
    public void delete(Long id) {
        roleRepository.delete(id);
    }

    public List<Role> findAll() {
        List<Role> roleList = roleRedis.getList("mysql:findAll:role");
        if (roleList == null) {
            roleList = roleRepository.findAll();
            if (roleList != null)
                roleRedis.add("mysql:findAll:role", 5L, roleList);
        }
        return roleList;
    }

    public Page<Role> findPage(RoleQo roleQo) {
        Pageable pageable = new PageRequest(roleQo.getPage(), roleQo.getSize(), new Sort(Sort.Direction.ASC, "id"));

        PredicateBuilder pb = new PredicateBuilder();

        if (!StringUtils.isEmpty(roleQo.getName())) {
            pb.add("name", "%" + roleQo.getName() + "%", LinkEnum.LIKE);
        }

        Page<Role> pages = roleRepository.findAll(pb.build(), Operator.AND, pageable);
        return pages;
    }

}
