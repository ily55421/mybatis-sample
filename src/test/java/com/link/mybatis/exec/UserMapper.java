package com.link.mybatis.exec;

import java.util.List;

/**
 * @author lin 2022/12/7 19:38
 */
public interface UserMapper {
    void insert(User user);

    List<User> getAll();
}

