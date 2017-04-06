package com.zkzong.springboot.mysql.repository;

import com.zkzong.springboot.dbexpand.jpa.repository.ExpandJpaRepository;
import com.zkzong.springboot.mysql.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ExpandJpaRepository<Role, Long> {

}
