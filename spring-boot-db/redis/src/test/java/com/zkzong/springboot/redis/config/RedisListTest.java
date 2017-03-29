package com.zkzong.springboot.redis.config;

import com.zkzong.springboot.mysql.entity.Department;
import com.zkzong.springboot.mysql.entity.Role;
import com.zkzong.springboot.mysql.entity.User;
import com.zkzong.springboot.redis.repository.UserRedis;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Zong on 2017/3/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisConfigTest.class, UserRedis.class})
public class RedisListTest {
    private static Logger logger = LoggerFactory.getLogger(RedisListTest.class);

    @Autowired
    private UserRedis userRedis;

    @Before
    public void setup() {
        Department deparment = new Department();
        deparment.setName("开发部");

        Role role = new Role();
        role.setName("admin");

        User user = new User();
        user.setName("user");
        user.setCreatedate(new Date());
        user.setDepartment(deparment);

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        user.setRoles(roles);

        List<User> users = new ArrayList<>();
        users.add(user);

        userRedis.delete(this.getClass().getName() + ":userList:" + user.getName());
        userRedis.add(this.getClass().getName() + ":userList:" + user.getName(), 10L, users);
    }

    @Test
    public void get() {
        List<User> users = userRedis.getList(this.getClass().getName() + ":userList:user");
        Assert.notNull(users);
        for (User user : users) {
            logger.info("======user====== name:{}, deparment:{}, role:{}",
                    user.getName(), user.getDepartment().getName(), user.getRoles().get(0).getName());
        }
    }

}
