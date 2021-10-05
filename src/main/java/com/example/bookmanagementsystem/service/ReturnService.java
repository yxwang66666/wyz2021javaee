package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.bean.BorrowRecord;
import com.example.bookmanagementsystem.bean.ReturnRecord;
import com.example.bookmanagementsystem.mapper.BookMapper;
import com.example.bookmanagementsystem.mapper.BorrowMapper;
import com.example.bookmanagementsystem.mapper.FineMapper;
import com.example.bookmanagementsystem.mapper.ReturnMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReturnService {
//    // 超时一天罚款
//    private static final double DAILY_FINE = 1;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ReturnMapper returnMapper;

    @Autowired
    private FineMapper fineMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    public PageInfo<ReturnRecord> selectAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(returnMapper.selectAll());
    }

    /**
     * 根据账户查找还书记录
     * @param account 条形码
     * @return Book对象
     */
    public PageInfo<ReturnRecord> selectByAccount(String account, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(returnMapper.selectByAccount(account));
    }

    /**
     * 还书
     * @param account 账户
     * @param barCode 条形码
     */
    public String returnBook(String account, String barCode, Double fine) {
        try {
            Calendar calendar = Calendar.getInstance();
            Date returnDate = calendar.getTime();

            BorrowRecord borrowRecord = borrowMapper.selectByAccountAndBarCode(account, barCode);

            borrowMapper.delete(account, barCode);

            bookMapper.updateIsBorrowed(barCode, 0);

            returnMapper.insert(new ReturnRecord(
                    borrowRecord.getUserAccount(),
                    borrowRecord.getBookBarCode(),
                    borrowRecord.getTitle(),
                    borrowRecord.getBorrowDate(),
                    returnDate));

//            // 超时天数
//            int overtimeDays = 0;
//            overtimeDays = (int)((returnDate.getTime() - borrowRecord.getBorrowDate().getTime()) / (1000 * 60 * 60 * 24))
//                    - borrowRecord.getDays();
//            // 超时
//            if (overtimeDays > 0) {
//                fineMapper.insert(returnDate, overtimeDays * DAILY_FINE, borrowRecord.getUserAccount());
//            }
            if (fine != 0) {
                fineMapper.insert(returnDate, fine, borrowRecord.getUserAccount());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "return fail";
        }
        return "return succeed";
    }
}
