package com.link.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lin 2022/12/9 22:21
 */
@Setter
@Getter
@ToString
public class User {
    private int id;
    private String account;
    private String password;
}
