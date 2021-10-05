package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.bean.Administrator;
import com.example.bookmanagementsystem.service.AdministratorService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/selectAll")
    public PageInfo<Administrator> selectAll(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return administratorService.selectAll(page, pageSize);
    }

    /**
     * 根据账户查找管理员
     * url: http://localhost:8080/administrator/selectByAccount?account=xxx
     * @param account 账户
     * @return PageInfo对象
     */
    @GetMapping("/selectByAccount")
    public PageInfo<Administrator> selectByAccount(@RequestParam(name = "account") String account, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return administratorService.selectByAccount(account, page, pageSize);
    }

    @GetMapping("/deleteByAccount")
    public String deleteByAccount(@RequestParam(name = "account") String account) {
        return administratorService.deleteByAccount(account);
    }

//    /**
//     * 增加
//     * url: http://localhost:8080/administrator/insert
//     * @param administrator 由前端发来的json文件
//     * @return "insert fail:duplicate key" 账户已存在
//     *         "insert fail" 插入异常
//     *         "insert succeed" 插入成功
//     */
//    @PostMapping("/insert")
//    public String insert(@RequestBody Administrator administrator) {
//        return administratorService.insert(administrator);
//    }

    @PostMapping("/update")
    public String update(@RequestBody Administrator administrator) {
        return administratorService.update(administrator);
    }

    /**
     * 登入
     * url: http://localhost:8080/administrator/login?account=xxx&password=xxx
     * @param account 账户
     * @param sentPassword 密码
     * @return 登入成功返回用户信息
     *         登入失败返回null
     */
    @GetMapping("/login")
    public Administrator login(@RequestParam(name = "account") String account, @RequestParam(name = "password") String sentPassword) {
        return administratorService.login(account, sentPassword);
    }
}
