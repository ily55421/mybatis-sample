package com.link.controller;

import com.link.entity.User;
import com.link.mapper.UserMapper;
import com.link.mybatis.entity.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author lin 2022/12/9 22:22
 */
@RestController
public class UserController {
    @Autowired
    SqlSessionFactoryBean sqlSessionFactory;

    @RequestMapping("add")
    public String addOne() throws Exception {
        User user=new User();
        user.setAccount("James");
        user.setPassword("123");

        UserMapper mapper = Objects.requireNonNull(sqlSessionFactory.getObject()).openSession().getMapper(UserMapper.class);
        mapper.insert(user);
        return "插入成功";
    }
}
