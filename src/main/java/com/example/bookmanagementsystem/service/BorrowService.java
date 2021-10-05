package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.bean.Book;
import com.example.bookmanagementsystem.bean.BorrowRecord;
import com.example.bookmanagementsystem.mapper.BookMapper;
import com.example.bookmanagementsystem.mapper.BorrowMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BorrowService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    // 没人最大借书数
    private static final int MAXIMUM = 10;

    public PageInfo<BorrowRecord> selectAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(borrowMapper.selectAll());
    }

    /**
     * 用户查询自己当前借的书
     * @param account 用户账户
     * @param page page
     * @param pageSize limit
     * @return 借书记录列表
     */
    public PageInfo<BorrowRecord> selectByAccount(String account, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(borrowMapper.selectByAccount(account));
    }

    public PageInfo<BorrowRecord> selectByAccountAndBarCode(String account, String barCode, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<BorrowRecord> borrowRecordList = new ArrayList<>();
        borrowRecordList.add(borrowMapper.selectByAccountAndBarCode(account, barCode));
        return new PageInfo<>(borrowRecordList);
    }

    /**
     * 根据条形码插入用户借书记录
     * @param account 用户账户
     * @param barCode 书籍条形码
     * @return "insert fail: maximum number of books borrowed" 达到最大借书数
     *         "insert fail: has book overtime" 有书超时未还
     *         "insert fail: has borrowed" 该书已被借走
     *         "insert fail: error bar code or account" 输入错误的账户或条形码
     *         "insert fail" 借书失败
     */
    public String insertByBarCode(String account, String barCode) {
        try {
            Date currentTime = Calendar.getInstance().getTime();
            // 达到最大借书数
            List<BorrowRecord> borrowRecordList = borrowMapper.selectByAccount(account);
            if (borrowRecordList.size() >= MAXIMUM) {
                return "insert fail: maximum number of books borrowed";
            }
            // 有书超时未还
            for (BorrowRecord record : borrowRecordList) {
                if (record.getBorrowDate().getTime() + (long)record.getDays() * 24 * 60 * 60 * 1000 < currentTime.getTime()) {
                    return "insert fail: has book overtime";
                }
            }
            // 判断书是否已被借出
            if (bookMapper.selectByBarCode(barCode).getIsBorrowed()) {
                return "insert fail: has borrowed";
            }
            borrowMapper.insert(new BorrowRecord(account, barCode,null, currentTime));
            bookMapper.updateIsBorrowed(barCode, 1);
        } catch (DataIntegrityViolationException | NullPointerException e) {
            return "insert fail: error bar code or account";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "insert fail";
        }
        return "insert succeed";
    }

//    /**
//     * 模糊查询后按照ISBN插入用户借书记录
//     * 在多本书中随机选择一本书借给用户
//     * @param account 用户账户
//     * @param isbn 书籍条形码
//     * @return "insert fail: all book is borrowed" 该isbn书全被借走，无法再借
//     */
//    public String insertByIsbn(String account, String isbn) {
//        try {
//            Calendar calendar = Calendar.getInstance();
//
//            Book book = new Book();
//            book.setIsbn(isbn);
//            List<Book> bookList = bookMapper.select2(book);
//
//            for (Book b : bookList) {
//                if (!b.getIsBorrowed()) {
//                    borrowMapper.insert(new BorrowRecord(account, b.getBarCode(),null, calendar.getTime()));
//                    bookMapper.updateIsBorrowed(b.getBarCode(), 1);
//                    return "insert succeed";
//                }
//            }
//
//            return "insert fail: all book is borrowed";
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return "insert fail";
//        }
//    }

    /**
     * 续借
     * @param account 用户账户
     * @param barCode 书籍条形码
     * @return
     */
    public String renew(String account, String barCode) {
        try {
            borrowMapper.renew(account, barCode, 30);
        } catch (UncategorizedSQLException ex) {
            return "insert fail: maximum number of renewals";
        } catch (Exception ex) {
            return "insert fail";
        }
        return "renew succeed";
    }
}
