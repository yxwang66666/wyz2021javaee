package com.example.bookmanagementsystem.mapper;

import com.example.bookmanagementsystem.bean.FineRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface FineMapper {

    List<FineRecord> selectAll();

    List<FineRecord> selectByAccount(String userAccount);

    List<FineRecord> selectByDate(Date startDate, Date endDate);

    List<FineRecord> selectByDateAndAccount(Date startDate, Date endDate, String userAccount);

    void insert(Date fineDate, Double amount, String userAccount);
}
