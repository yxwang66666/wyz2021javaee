package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.bean.Book;
import com.example.bookmanagementsystem.bean.BookKind;
import com.example.bookmanagementsystem.mapper.BookMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    /**
     * 分页查询所有书籍
     * @param page 请求的页数
     * @param pageSize 每页大小
     * @return PageInfo对象
     */
    public PageInfo<Book> selectAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(bookMapper.selectAll());
    }

    /**
     * 根据isbn查询所有的barCode
     */
    public List<String> selectBarCode(String isbn) {
        return bookMapper.selectBarCode(isbn);
    }

    /**
     * 分页查询所有isbn
     * @param page 请求的页数
     * @param pageSize 每页大小
     * @return PageInfo对象
     */
    public PageInfo<BookKind> selectAllISBN(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<BookKind> sumBookList = bookMapper.selectAllISBN2();
        List<BookKind> numBookList = bookMapper.selectAllISBN();

        for (BookKind bSum : sumBookList) {
            for (BookKind bNum : numBookList) {
                if (bNum.getIsbn().equals(bSum.getIsbn())) {
                    bSum.setNum(bNum.getNum());
                    break;
                }
            }
        }

        return new PageInfo<>(sumBookList);
    }

    /**
     * 根据条形码查找书
     * @param barCode 条形码，由0/1组成的字符串
     * @return PageInfo对象
     */
    public PageInfo<Book> selectByBarCode(String barCode, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookMapper.selectByBarCode(barCode));
        return new PageInfo<>(bookList);
    }

    /**
     * 根据条形码删除
     * @param barCode 条形码，由0/1组成的字符串
     * @return "delete fail: is borrowed" 本书已被借走，删除失败
     *         "delete fail" 删除失败
     *         "delete succeed" 删除成功
     */
    public String deleteByBarCode(String barCode) {
        try {
            Book book = bookMapper.selectByBarCode(barCode);
            if (book.getIsBorrowed()) {
                return "delete fail: is borrowed";
            }
            bookMapper.deleteByBarCode(barCode);
        } catch (Exception e) {
            e.printStackTrace();
            return "delete fail";
        }
        return "delete succeed";
    }

    /**
     * 根据ISBN删除
     * @param ISBN
     * @return "delete fail: is borrowed" 本书已被借走，删除失败
     *         "delete fail" 删除失败
     *         "delete succeed" 删除成功
     */
    public String deleteByISBN(String ISBN) {
        try {
            Book book = new Book();
            book.setIsbn(ISBN);
            List<Book> list = bookMapper.select2(book);
            for (Book b : list) {
                if (b.getIsBorrowed()) {
                    return "delete fail: is borrowed";
                }
            }
            bookMapper.deleteByISBN(ISBN);
        } catch (Exception e) {
            e.printStackTrace();
            return "delete fail";
        }
        return "delete succeed";
    }

    /**
     * 模糊查询、多条件复合查询
     * @param book 由前端发来的json文件
     * @param page 页数
     * @param pageSize 每页大小
     * @return PageInfo
     */
    public PageInfo<BookKind> select(Book book, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<BookKind> sumBookList = bookMapper.select3(book);
        List<BookKind> numBookList = bookMapper.select(book);

        for (BookKind bSum : sumBookList) {
            for (BookKind bNum : numBookList) {
                if (bNum.getIsbn().equals(bSum.getIsbn())) {
                    bSum.setNum(bNum.getNum());
                    break;
                }
            }
        }

        return new PageInfo<>(sumBookList);
    }

    /**
     * 增加
     * @return "insert fail: duplicate key" 条形码已存在
     *         "insert fail: unknown isbn" isbn 不存在于数据库但是插入时只给出了 barCode 和 isbn，其他信息省略
     *         "insert fail" 插入失败
     *         "insert succeed" 插入成功
     */
    public Map<String, String> insert(MultipartFile file, String barCode, String title, String author, String publisher, String isbn, String price, String introduction) {
        Map<String, String> result = new HashMap<>();
        Book book;
        try {
            book = new Book(barCode, title, author, publisher, isbn, Double.parseDouble(price), introduction, null);
        } catch (NumberFormatException e) {
            book = new Book(barCode, title, author, publisher, isbn, null, introduction, null);
        }
//        if (price == null || price.equals(""))
//            book = new Book(barCode, title, author, publisher, isbn, null, introduction, null);
//        else
//            book = new Book(barCode, title, author, publisher, isbn, Double.parseDouble(price), introduction, null);
        String path = randomImagePath(file);

        Book isbnBook = new Book();
        isbnBook.setIsbn(isbn);
        List<Book> list = bookMapper.select2(isbnBook);
        if (list.isEmpty()) {   // 数据库中不存在该 isbn
            try {
                if (path == null) {
                    book.setImage("https://img1.doubanio.com/view/subject/l/public/s25041427.jpg");
                } else {
                    book.setImage(path);
                }
                bookMapper.insertBook(book);
                if (path != null) {
                    saveImage(file, path);  // 为了防止插入出错仍会保存图片，saveImage应该放在bookMapper.insertBook(book)之后
                }
            } catch (DataIntegrityViolationException e) {
                // 信息未给全
                result.put("return", "insert fail: unknown isbn");
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                result.put("return", "insert fail");
                return result;
            }
        }

        try {
            bookMapper.insertBarCode(book);
        } catch (DuplicateKeyException e) {
            result.put("return", "insert fail: duplicate key");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("return", "insert fail");
            return result;
        }
        result.put("return", "insert succeed");
        return result;
    }

    /**
     * 修改图书信息
     * @param book 由前端发来的json文件
     *             根据 isbn 修改书本信息，json 文件中可不给出 barCode
     */
    public String update(Book book) {
        try {
            bookMapper.update(book);
        } catch (Exception e) {
            e.printStackTrace();
            return "update fail";
        }
        return "update succeed";
    }

    /**
     * 修改图书封面
     */
    public Map<String, String> updateImage(MultipartFile file, String isbn) {
        Map<String, String> result = new HashMap<>();
        try {
            // isbn 查找，删除原本的封面
            Book book = new Book();
            book.setIsbn(isbn);
            List<Book> bookList = bookMapper.select2(book);
            String path = bookList.get(0).getImage();
            if (!path.startsWith("http")) {
                new File(bookList.get(0).getImage()).delete();
            }
            // 替换为新封面
            path = randomImagePath(file);
            book.setImage(path);
            bookMapper.update(book);
            saveImage(file, path);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("return", "update fail");
            return result;
        }
        result.put("return", "update succeed");
        return result;
    }

    /**
     * 保存图片
     */
    private void saveImage(MultipartFile file, String path) {
        try {
            file.transferTo(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成唯一路径
     */
    private String randomImagePath(MultipartFile file) {
        if (file == null) {
            return null;
        }
        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            return null;
        }
        String savePath = "src/main/webapp/resources/static/images/";
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            savePathFile.mkdirs();
        }
        //通过UUID生成唯一文件名
        String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
        return savePathFile.getAbsolutePath() + "\\" + filename;
    }
}
