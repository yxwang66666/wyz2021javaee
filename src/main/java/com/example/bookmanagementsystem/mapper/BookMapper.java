package com.example.bookmanagementsystem.mapper;

import com.example.bookmanagementsystem.bean.Book;
import com.example.bookmanagementsystem.bean.BookKind;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BookMapper {
    // 查询所有
    List<Book> selectAll();

    // 根据isb查询条形码
    List<String> selectBarCode(String isbn);

    // 查询所有
    List<BookKind> selectAllISBN();

    // 查询所有
    List<BookKind> selectAllISBN2();

    // 根据条形码查询
    Book selectByBarCode(String barCode);

    // 模糊查询、多条件复合查询
    List<BookKind> select(Book book);

    List<Book> select2(Book book);

    List<BookKind> select3(Book book);

    // 增加
    void insertBook(Book book);

    void insertBarCode(Book book);

    // 修改
    void update(Book book);

    // 借书、还书
    void updateIsBorrowed(String barCode, int isBorrowed);

    // 根据条形码删除
    void deleteByBarCode(String barCode);

    //根据ISBN删除
    void deleteByISBN(String ISBN);
}
