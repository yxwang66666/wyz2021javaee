package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.bean.FineRecord;
import com.example.bookmanagementsystem.mapper.FineMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FineService {
    @Autowired
    private FineMapper fineMapper;

    public PageInfo<FineRecord> selectAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(fineMapper.selectAll());
    }

    public PageInfo<FineRecord> selectByAccount(String account, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(fineMapper.selectByAccount(account));
    }

    public PageInfo<FineRecord> selectByDate(Map<String, Object> params) {
        int page = (Integer) params.get("page");
        int pageSize = (Integer) params.get("limit");

        PageHelper.startPage(page, pageSize);

        Date startDate = null;
        Date endDate = null;

        try {
            startDate = DateUtils.parseDate((String) params.get("startDate"), "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            endDate = DateUtils.parseDate((String) params.get("endDate"), "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (startDate == null && endDate == null)
            return new PageInfo<>(fineMapper.selectAll());

        return new PageInfo<>(fineMapper.selectByDate(startDate, endDate));
    }

    public PageInfo<FineRecord> selectByDateAndAccount(Map<String, Object> params) {
        int page = (Integer) params.get("page");
        int pageSize = (Integer) params.get("limit");

        System.out.println(params);

        PageHelper.startPage(page, pageSize);

        Date startDate = null;
        Date endDate = null;
        String userAccount = null;

        try {
            startDate = DateUtils.parseDate((String) params.get("startDate"), "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            endDate = DateUtils.parseDate((String) params.get("endDate"), "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }

        userAccount = (String) params.get("userAccount");

        if (startDate == null && endDate == null && userAccount != null)
            return new PageInfo<>(fineMapper.selectByAccount(userAccount));

        return new PageInfo<>(fineMapper.selectByDateAndAccount(startDate, endDate, userAccount));
    }
}
