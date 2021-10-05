package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.bean.BorrowRecord;
import com.example.bookmanagementsystem.service.BorrowService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @GetMapping(path = "/selectAll")
    public PageInfo<BorrowRecord> selectAll(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return borrowService.selectAll(page, pageSize);
    }

    /**
     * 用户查询自己当前借的书
     * url: http://localhost:8080/borrow/selectByAccount?userAccount=xxxxxx&page=x&limit=x
     * @param account  用户账户
     * @param page     page
     * @param pageSize limit
     * @return pageInfo
     */
    @GetMapping(path = "/selectByAccount")
    public PageInfo<BorrowRecord> selectByAccount(@RequestParam(name = "userAccount") String account, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return borrowService.selectByAccount(account, page, pageSize);
    }

    /**
     * 用户查询自己当前借的书
     * url: http://localhost:8080/borrow/selectByAccountAndBarCode?userAccount=xxx&bookBarCode=xxxxxx&page=x&limit=x
     * @param barCode  用户账户
     * @param page     page
     * @param pageSize limit
     * @return pageInfo
     */
    @GetMapping(path = "/selectByAccountAndBarCode")
    public PageInfo<BorrowRecord> selectByAccountAndBarCode(@RequestParam(name = "userAccount") String account, @RequestParam(name = "bookBarCode") String barCode, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return borrowService.selectByAccountAndBarCode(account, barCode, page, pageSize);
    }

    /**
     * 根据条形码插入用户借书记录
     * url: http://localhost:8080/borrow/insertByBarCode?userAccount=xxx&bookBarCode=xxxxxxxxxxxxx
     * @param account 用户账户
     * @param barCode 书籍条形码
     * @return "insert fail: maximum number of books borrowed" 达到最大借书数
     *         "insert fail: has book overtime" 有书超时未还
     *         "insert fail: has borrowed" 该书已被借走
     *         "insert fail: error bar code or account" 输入错误的账户或条形码
     *         "insert fail" 借书失败
     */
    @GetMapping(path = "insertByBarCode")
    public String insertByBarCode(@RequestParam(name = "userAccount") String account, @RequestParam(name = "bookBarCode") String barCode) {
        return borrowService.insertByBarCode(account, barCode);
    }

//    /**
//     * 模糊查询后按照ISBN插入用户借书记录
//     * 在多本书中随机选择一本书借给用户
//     * url: http://localhost:8080/borrow/insertByIsbn?userAccount=xxx&isbn=xxxxxxxxxxxxx
//     * @param account 用户账户
//     * @param isbn 书籍条形码
//     */
//    @GetMapping(path = "insertByIsbn")
//    public String insertByIsbn(@RequestParam(name = "userAccount") String account, @RequestParam(name = "isbn") String isbn) {
//        return borrowService.insertByIsbn(account, isbn);
//    }

    /**
     * 续借
     * url: http://localhost:8080/borrow/renew?userAccount=xxx&bookBarCode=xxxxxxxxxxxxx
     * @param account 用户账户
     * @param barCode 书籍条形码
     * @return
     */
    @GetMapping(path = "/renew")
    public String renew(@RequestParam(name = "userAccount") String account, @RequestParam(name = "bookBarCode") String barCode) {
        return borrowService.renew(account, barCode);
    }
}
