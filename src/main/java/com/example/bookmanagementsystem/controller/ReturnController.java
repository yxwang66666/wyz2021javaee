package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.bean.ReturnRecord;
import com.example.bookmanagementsystem.service.ReturnService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/return")
public class ReturnController {
    @Autowired
    private ReturnService returnService;

    @GetMapping("selectAll")
    public PageInfo<ReturnRecord> selectAll(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return returnService.selectAll(page, pageSize);
    }

    /**
     * 根据账户查找还书记录
     * url: http://localhost:8080/return/selectByAccount?userAccount=xxx&page=x&limit=x
     * @param account 条形码
     * @return Book对象
     */
    @GetMapping("selectByAccount")
    public PageInfo<ReturnRecord> selectByAccount(@RequestParam(name = "userAccount") String account, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return returnService.selectByAccount(account, page, pageSize);
    }

    /**
     * 还书
     * url: http://localhost:8080/return/return?userAccount=xxx&bookBarCode=xxxxxxxxxxxxx&fine=x
     * @param account 账户
     * @param barCode 条形码
     */
    @GetMapping("return")
    public String returnBook(@RequestParam(name = "userAccount") String account, @RequestParam(name = "bookBarCode") String barCode, @RequestParam(name = "fine") Double fine) {
        return returnService.returnBook(account, barCode, fine);
    }
}
