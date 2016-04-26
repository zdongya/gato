package com.yunjing.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;



public class MyBatisFactory {
	
	private static SqlSessionFactory singleton;
    private MyBatisFactory(){}
    static {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        singleton = new SqlSessionFactoryBuilder().build(reader);
    }
    public static SqlSessionFactory getInstance(){
        return singleton;
    }

}
