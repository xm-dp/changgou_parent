package com.changgou.test;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class TestBcrypt{

    public static void main(String[] args)
    {
        String genSalt = BCrypt.gensalt();
        System.out.println("盐："+genSalt);
        String saltPw = BCrypt.hashpw("123456", genSalt);
        System.out.println("密文："+saltPw);

        boolean result = BCrypt.checkpw("123456", saltPw);
        System.out.println("结果："+result);
    }
}

