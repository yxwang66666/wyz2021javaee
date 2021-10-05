package com.example.bookmanagementsystem.mapper;

import com.example.bookmanagementsystem.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
    // 查询所有
    List<User> selectAll();

    // 根据账号查询
    User selectByAccount(String account);

    // 增加
    void insert(User user);

    // 修改
    void update(User user);

    // 根据账号删除
    void deleteByAccount(String account);
}
