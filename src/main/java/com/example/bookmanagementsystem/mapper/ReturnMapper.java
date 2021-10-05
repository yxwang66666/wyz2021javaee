package com.example.bookmanagementsystem.mapper;

import com.example.bookmanagementsystem.bean.ReturnRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ReturnMapper {
    // 查询所有
    List<ReturnRecord> selectAll();

    // 根据账户查询
    List<ReturnRecord> selectByAccount(String account);

    // 增加归还记录，如果超时罚款，则添加一条罚款记录
    void insert(ReturnRecord returnRecord);
}
