package com.example.bookmanagementsystem.mapper;

import com.example.bookmanagementsystem.bean.BorrowRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BorrowMapper {
    // 选择所有
    List<BorrowRecord> selectAll();

    // 根据用户名选择借书记录
    List<BorrowRecord> selectByAccount(String userAccount);

    // 根据用户名和条形码选择记录
    BorrowRecord selectByAccountAndBarCode(String userAccount, String bookBarCode);

    // 插入借书记录
    void insert(BorrowRecord record);

    // 删除借书记录
    void delete(String userAccount, String bookBarCode);

    // 续借
    void renew(String userAccount, String bookBarCode, Integer day);
}
