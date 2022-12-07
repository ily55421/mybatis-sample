package com.link.mybatis.exec;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lin 2022/12/7 19:38
 */
@Setter
@Getter
@ToString
public class User {
    private int id;
    private String account;
    private String password;
}
