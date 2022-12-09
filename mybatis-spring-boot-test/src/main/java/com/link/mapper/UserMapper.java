package com.link.mapper;

import com.link.entity.User;

import java.util.List;

/**
 * @author lin 2022/12/9 22:19
 */
public interface UserMapper {
    void insert(User user);

    List<User> getAll();
}
