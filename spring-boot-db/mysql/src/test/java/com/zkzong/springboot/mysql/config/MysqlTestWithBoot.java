package com.zkzong.springboot.mysql.config;

import com.zkzong.springboot.mysql.MySQLApplication;
import com.zkzong.springboot.mysql.entity.User;
import com.zkzong.springboot.mysql.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Created by Zong on 2017/3/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MySQLApplication.class)
@SpringBootTest
public class MysqlTestWithBoot {
    private static Logger logger = LoggerFactory.getLogger(MysqlTestWithBoot.class);

    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private DepartmentRepository departmentRepository;
//    @Autowired
//    private RoleRepository roleRepository;

//    @Before
//    public void initData() {
//        userRepository.deleteAll();
//        roleRepository.deleteAll();
//        departmentRepository.deleteAll();
//
//        Department department = new Department();
//        department.setName("开发部");
//        departmentRepository.save(department);
//        Assert.notNull(department.getId());
//
//        Role role = new Role();
//        role.setName("admin");
//        roleRepository.save(role);
//        Assert.notNull(role.getId());
//
//        User user = new User();
//        user.setName("user");
//        user.setCreatedate(new Date());
//        user.setDepartment(department);
//
//        List<Role> roles = roleRepository.findAll();
//        Assert.notNull(roles);
//        user.setRoles(roles);
//
//        userRepository.save(user);
//        Assert.notNull(user.getId());
//    }

    @Test
    public void findPage() {
        Pageable pageable = new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "id"));
        Page<User> page = userRepository.findAll(pageable);
        Assert.notNull(page);
        for (User user : page.getContent()) {
            logger.info("====user==== user name:{}, department name:{}, role name:{}",
                    user.getName(), user.getDepartment().getName(), user.getRoles().get(0).getName());
        }
    }

    @Test
    public void test() {
        User user1 = userRepository.findByNameLike("u%");
        Assert.notNull(user1);
        System.out.println(user1);

        User user2 = userRepository.readByName("user");
        Assert.notNull(user2);
        System.out.println(user2);

        List<User> users = userRepository.getByCreatedateLessThan(new Date());
        Assert.notNull(users);
        System.out.println(users);

    }
}
