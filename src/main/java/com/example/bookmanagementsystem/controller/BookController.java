package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.bean.Book;
import com.example.bookmanagementsystem.bean.BookKind;
import com.example.bookmanagementsystem.service.BookService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * 根据网页的url返回相应书的信息
 * url: http:/localhost:8080/book/
 */

@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 分页查询所有书籍
     * url: http://localhost:8080/book/selectAll
     * @param page 请求的页数
     * @param pageSize 每页大小
     * @return PageInfo对象
     */
    @GetMapping("/selectAll")
    public PageInfo<Book> selectAll(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return bookService.selectAll(page, pageSize);
    }

    /**
     * 根据isbn查询所有的barCode
     * url: http://localhost:8080/book/selectBarCode
     */
    @GetMapping("/selectBarCode")
    public List<String> selectBarCode(@RequestParam(name="isbn") String isbn) {
        return bookService.selectBarCode(isbn);
    }

    /**
     * 分页查询所有isbn
     * url: http://localhost:8080/book/selectAllISBN
     * @param page 请求的页数
     * @param pageSize 每页大小
     * @return PageInfo对象
     */
    @GetMapping("/selectAllISBN")
    public PageInfo<BookKind> selectAllISBN(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return bookService.selectAllISBN(page, pageSize);
    }

    /**
     * 根据条形码查找书
     * url: http://localhost:8080/book/selectByBarCode?barCode=xxxxxxxxxxxxx
     * @param barCode 条形码
     * @return PageInfo对象
     */
    @GetMapping("/selectByBarCode")
    public PageInfo<Book> selectByBarCode(@RequestParam(name = "barCode") String barCode, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return bookService.selectByBarCode(barCode, page, pageSize);
    }

    /**
     * 根据条形码删除
     * url: http://localhost:8080/book/deleteByBarCode?barCode=xxxxxxxxxxxxx
     * @param barCode 条形码
     * @return "delete fail: is borrowed" 本书已被借走，删除失败
     *         "delete fail" 删除失败
     *         "delete succeed" 删除成功
     */
    @GetMapping("/deleteByBarCode")
    public String deleteByBarCode(@RequestParam(name = "barCode") String barCode) {
        return bookService.deleteByBarCode(barCode);
    }

    /**
     * 根据ISBN删除
     * url: http://localhost:8080/book/ISBN?ISBN=xxxxxxxxxxxxx
     * @param ISBN 条形码
     * @return "delete fail: is borrowed" 本书已被借走，删除失败
     *         "delete fail" 删除失败
     *         "delete succeed" 删除成功
     */
    @GetMapping("/deleteByISBN")
    public String deleteByISBN(@RequestParam(name = "ISBN") String ISBN) {
        return bookService.deleteByISBN(ISBN);
    }

    /**
     * 模糊查询、多条件复合查询
     * url: http://localhost:8080/book/select?title=xxx&author=xxx&publisher=xxx&isbn=xxxxxxxxxxxxx&page=x&limit=x
     * @return PageInfo<BookKind>
     */
    @GetMapping("/select")
    public PageInfo<BookKind> select(@RequestParam(name = "title") String title,
                                     @RequestParam(name = "author") String author,
                                     @RequestParam(name = "publisher") String publisher,
                                     @RequestParam(name = "isbn") String isbn,
                                     @RequestParam(name = "page") int page, @RequestParam(name = "limit") int pageSize) {
        return bookService.select(
                new Book(null, title, author, publisher, isbn, null, null, null), page, pageSize);
    }

    /**
     * 增加
     * url: http://localhost:8080/book/insert
     * @return "insert fail: duplicate key" 条形码已存在
     *         "insert fail: unknown isbn" isbn 不存在于数据库但是插入时只给出了 barCode 和 isbn，其他信息省略
     *         "insert fail" 插入失败
     *         "insert succeed" 插入成功
     */
    @PostMapping("/insert")
    public Map<String, String> insert(@RequestParam(name = "file", required = false) MultipartFile file,
                                      @RequestParam(name = "barCode") String barCode,
                                      @RequestParam(name = "title") String title,
                                      @RequestParam(name = "author") String author,
                                      @RequestParam(name = "publisher") String publisher,
                                      @RequestParam(name = "isbn") String isbn,
                                      @RequestParam(name = "price") String price,
                                      @RequestParam(name = "introduction") String introduction) {
        return bookService.insert(file, barCode, title, author, publisher, isbn, price, introduction);
    }

    /**
     * 修改图书信息
     * url: http://localhost:8080/book/update
     * @param book 由前端发来的json文件
     *             根据 isbn 修改书本信息，json 文件中可不给出 barCode
     */
    @PostMapping("/update")
    public String update(@RequestBody Book book) {
        return bookService.update(book);
    }

    /**
     * 修改图书封面
     * url: http://localhost:8080/book/updateImage
     */
    @PostMapping("/updateImage")
    public Map<String, String> updateImage(@RequestParam(name = "file", required = false) MultipartFile file, @RequestParam(name = "isbn") String isbn) {
        return bookService.updateImage(file, isbn);
    }
}