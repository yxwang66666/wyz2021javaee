package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.bean.FineRecord;
import com.example.bookmanagementsystem.service.FineService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/fine")
public class FineController {
    @Autowired
    private FineService fineService;

    @GetMapping("/selectAll")
    public PageInfo<FineRecord> selectAll(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return fineService.selectAll(page, pageSize);
    }

    @GetMapping("/selectByAccount")
    public PageInfo<FineRecord> selectByAccount(@RequestParam(name = "userAccount") String account, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return fineService.selectByAccount(account, page, pageSize);
    }

    /**
     * 按日期查询
     * url: http://localhost:8080/fine/selectByDate
     * @param params json文件，包含起始日期，结束日期，可只给出起始日期或结束日期，page，limit
     * @return pageInfo，当日期全未给出时返回全部记录
     */
    @PostMapping("/selectByDate")
    public PageInfo<FineRecord> selectByDate(@RequestBody Map<String, Object> params) {
        return fineService.selectByDate(params);
    }

    /**
     * 按日期和账户查询
     * url: http://localhost:8080/fine/selectByDateAndAccount
     * @param params json文件，包含账户(userAccount)，起始日期，结束日期，page，limit，且可只给出起始日期或结束日期
     * @return pageInfo，当日期全未给出时返回全部记录
     */
    @PostMapping("/selectByDateAndAccount")
    public PageInfo<FineRecord> selectByDateAndAccount(@RequestBody Map<String, Object> params) {
        return fineService.selectByDateAndAccount(params);
    }
}
