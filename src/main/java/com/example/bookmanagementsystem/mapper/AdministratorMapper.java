package com.example.bookmanagementsystem.mapper;

import com.example.bookmanagementsystem.bean.Administrator;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AdministratorMapper {
    // 查询所有
    List<Administrator> selectAll();

    // 根据账号查询
    Administrator selectByAccount(String account);

    // 增加
    void insert(Administrator Administrator);

    // 修改
    void update(Administrator Administrator);

    // 根据账号删除
    void deleteByAccount(String account);
}
