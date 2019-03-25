package com.dullon.demoboot.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlSessionFactoryUtil {

    //工厂对象
    private static SqlSessionFactory sqlSessionFactory = null;
    //类锁
    private static final Class CLASS_LOCK = SqlSessionFactoryUtil.class;
    //私有构造方法
    private SqlSessionFactoryUtil(){}

    /**
     * 构建SqlSessionFactory
     * @return
     */
    public static SqlSessionFactory initSqlSessionFactory(){
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
           //inputStream = getClass().getClassLoader().getResourceAsStream(resource);

            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            Logger.getLogger(SqlSessionFactoryUtil.class.getName()).log(Level.SEVERE,null,e);

        }
        synchronized (CLASS_LOCK){
            if (sqlSessionFactory == null){
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            }
        }
        return sqlSessionFactory;
    }

    /**
     * 打开SqlSession
     * @return
     */
    public static SqlSession openSqlSession(){
        if (sqlSessionFactory == null){
            initSqlSessionFactory();
        }
        return sqlSessionFactory.openSession();
    }
}
