package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.bean.User;
import com.example.bookmanagementsystem.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 分页查找所有用户
     * @param page 页数
     * @param pageSize 每页大小(改为limit了)
     * @return PageInfo对象
     */
    @GetMapping("/selectAll")
    public PageInfo<User> selectAll(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return userService.selectAll(page, pageSize);
    }

    /**
     * 根据账户查找用户
     * url: http://localhost:8080/user/selectByAccount?account=xxx
     * @param account 账户
     * @return PageInfo对象
     */
    @GetMapping("/selectByAccount")
    public PageInfo<User> selectByAccount(@RequestParam(name = "account") String account, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return userService.selectByAccount(account, page, pageSize);
    }

    @GetMapping("/deleteByAccount")
    public String deleteByAccount(@RequestParam(name = "account") String account) {
        return userService.deleteByAccount(account);
    }

    /**
     * 增加
     * url: http://localhost:8080/user/insert
     * @param user 由前端发来的json文件
     * @return "insert fail:duplicate key" 账户已存在
     *         "insert fail" 插入异常
     *         "insert succeed" 插入成功
     */
    @PostMapping("/insert")
    public String insert(@RequestBody User user) {
        return userService.insert(user);
    }

    @PostMapping("/update")
    public String update(@RequestBody User user) {
        return userService.update(user);
    }

    /**
     * 登入
     * url: http://localhost:8080/user/login?account=xxx&password=xxx
     * @param account 账户
     * @param sentPassword 密码
     * @return 登入成功返回用户信息
     *         登入失败返回null
     */
    @GetMapping("/login")
    public User login(@RequestParam(name = "account") String account, @RequestParam(name = "password") String sentPassword) {
        return userService.login(account, sentPassword);
    }
}
