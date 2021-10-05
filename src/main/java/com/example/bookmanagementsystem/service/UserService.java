package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.bean.User;
import com.example.bookmanagementsystem.mapper.UserMapper;
import com.example.bookmanagementsystem.util.Encoder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public PageInfo<User> selectAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<User> userList = userMapper.selectAll();
        for (User user : userList) {
            user.setPassword("******");
        }
        return new PageInfo<>(userList);
    }

    /**
     * 根据账户查找用户
     * @param account 账户
     * @return PageInfo对象
     */
    public PageInfo<User> selectByAccount(String account, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<User> userList = new ArrayList<>();
        User user = userMapper.selectByAccount(account);
        if (user != null) {
            user.setPassword("******");
            userList.add(user);
        }
        return new PageInfo<>(userList);
    }

    public String deleteByAccount(String account) {
        try {
            userMapper.deleteByAccount(account);
        } catch (DataIntegrityViolationException e) {
            return "delete fail: has book borrowed";
        } catch (Exception e) {
            e.printStackTrace();
            return "delete fail";
        }
        return "delete succeed";
    }

    /**
     * 增加
     * @param user 由前端发来的json文件
     * @return "insert fail: duplicate key" 账户已存在
     *         "insert fail" 插入异常
     *         "insert succeed" 插入成功
     */
    public String insert(User user) {
        try {
            if (!Encoder.encode(user)) {
                return "insert fail";
            }
            userMapper.insert(user);
        } catch (DuplicateKeyException e) {
            return "insert fail: duplicate key";
        } catch (Exception e) {
            e.printStackTrace();
            return "insert fail";
        }
        return "insert succeed";
    }

    public String update(User user) {
        try {
            if (!Encoder.encode(user)) {
                return "update fail";
            }
            userMapper.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "update fail";
        }
        return "update succeed";
    }

    /**
     * 登入
     * @param account 账户
     * @param sentPassword 密码
     * @return 登入成功返回用户信息
     *         登入失败返回null
     */
    public User login(String account, String sentPassword) {
        User user = userMapper.selectByAccount(account);
        if (user != null) {
            String savedPassword = user.getPassword();
            if (Encoder.matches(sentPassword, savedPassword)) {
                user.setPassword("******");
                return user;
            }
        }
        return null;
    }
}
