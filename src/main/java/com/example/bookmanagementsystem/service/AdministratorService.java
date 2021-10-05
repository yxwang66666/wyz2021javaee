package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.bean.Administrator;
import com.example.bookmanagementsystem.mapper.AdministratorMapper;
import com.example.bookmanagementsystem.util.Encoder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorMapper administratorMapper;

    public PageInfo<Administrator> selectAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Administrator> administratorList = administratorMapper.selectAll();
        for (Administrator administrator : administratorList) {
            administrator.setPassword("******");
        }
        return new PageInfo<>(administratorList);
    }

    /**
     * 根据账户查找管理员
     * @param account 账户
     * @return PageInfo对象
     */
    public PageInfo<Administrator> selectByAccount(String account, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Administrator> administratorList = new ArrayList<>();
        Administrator administrator = administratorMapper.selectByAccount(account);
        if (administrator != null) {
            administrator.setPassword("******");
            administratorList.add(administrator);
        }
        return new PageInfo<>(administratorList);
    }

    public String deleteByAccount(String account) {
        try {
            administratorMapper.deleteByAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
            return "delete fail";
        }
        return "delete succeed";
    }

    /**
     * 增加
     * @param administrator 由前端发来的json文件
     * @return "insert fail:duplicate key" 账户已存在
     *         "insert fail" 插入异常
     *         "insert succeed" 插入成功
     */
    public String insert(Administrator administrator) {
        try {
            if (!Encoder.encode(administrator)) {
                return "insert fail";
            }
            administratorMapper.insert(administrator);
        } catch (DuplicateKeyException e) {
            return "insert fail: duplicate key";
        } catch (Exception e) {
            e.printStackTrace();
            return "insert fail";
        }
        return "insert succeed";
    }

    public String update(Administrator administrator) {
        try {
            if (!Encoder.encode(administrator)) {
                return "update fail";
            }
            administratorMapper.update(administrator);
        } catch (Exception e) {
            e.printStackTrace();
            return "update fail";
        }
        return "insert succeed";
    }

    /**
     * 登入
     * @param account 账户
     * @param sentPassword 密码
     * @return 登入成功返回用户信息
     *         登入失败返回null
     */
    public Administrator login(String account, String sentPassword) {
        Administrator administrator = administratorMapper.selectByAccount(account);
        if (administrator != null) {
            String savedPassword = administrator.getPassword();
            if (Encoder.matches(sentPassword, savedPassword)) {
                administrator.setPassword("******");
                return administrator;
            }
        }
        return null;
    }
}
