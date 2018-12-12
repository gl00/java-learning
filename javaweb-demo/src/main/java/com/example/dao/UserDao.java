package com.example.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UserDao {
    public void update() {
        // 通过类加载器读取资源文件
//        test1();
        test2();
    }

    private void test1() {
        // 直接通过类加载器读取资源文件
        // 有一个问题，无法获取资源文件更新后的数据
        // 因为类只装载一次。如果资源文件更新了，就获取不到更新了
        try (InputStream in = UserDao.class.getClassLoader().getResourceAsStream("db.properties")) {
            useRes(in);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    private void test2() {
        // 通过类加载器得到资源文件的路径
        // 再用普通文件 IO 流读取资源文件，这样可以读取到资源文件更新后的数据
        String cfgPath = UserDao.class.getClassLoader().getResource("db.properties").getPath();
        try (InputStream in = new FileInputStream(cfgPath)) {
            useRes(in);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    private void useRes(InputStream in) throws IOException {
        Properties props = new Properties();
        props.load(in);

        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        System.out.println(url);
        System.out.println(username);
        System.out.println(password);
    }
}
