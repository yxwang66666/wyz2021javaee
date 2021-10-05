package com.example.bookmanagementsystem.util;

import com.example.bookmanagementsystem.bean.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder {
    /**
     * 加密次数
     */
    private static final int TIMES = 10;

    /**
     * 匹配数据库中的密码和用户输入的密码
     * @param sentPassword 用户传入的密码
     * @param savedPassword 数据库存储的密码
     * @return true 密码正确
     *         false 密码错误
     */
    public static boolean matches(String sentPassword, String savedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(TIMES);
        return encoder.matches(sentPassword, savedPassword);
    }

//    /**
//     *将用户输入生成的管理员对象中的密码进行加密
//     * @param administrator 用户进行注册生成的管理员对象
//     * @return 加密后的管理员对象
//     */
//    public static Administrator encode(Administrator administrator) {
//        if (administrator.getPassword() == null)
//            return administrator;
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(TIMES);
//        String encodePassword = encoder.encode(administrator.getPassword());
//        administrator.setPassword(encodePassword);
//        return administrator;
//    }

    /**
     * 将用户输入生成的管理员对象中的密码进行加密
     * @param user 用户进行注册生成的用户对象
     * @return 加密后的用户对象
     */
    public static boolean encode(User user) {
        if (user.getPassword() == null)
            return false;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(TIMES);
        String encodePassword = encoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        return true;
    }
}
