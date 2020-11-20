package com.liu.mapper;

import com.liu.pojo.User;

import java.util.List;

public interface UserMapper {

    User selectOne(User user);

    <E> List<E> selectList(User user);

    int update(User user);

    boolean insert(User user);

    boolean delete(int id);
}
