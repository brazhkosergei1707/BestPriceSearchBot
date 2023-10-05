package com.example.telegrambot.config.dbconfig;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

//@Configuration
//@EnableTransactionManagement
public class HibernateConfig {
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Value("${spring.datasource.username}")
//    private String user;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Bean(name="entityManagerFactory")
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan(
//                new String[]{"com.example.telegrambot.entity.*"});
//        return sessionFactory;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        HikariDataSource ds = new HikariDataSource();
//        ds.setMaximumPoolSize(100);
//        ds.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
//        ds.addDataSourceProperty("url", url);
//        ds.addDataSourceProperty("user", user);
//        ds.addDataSourceProperty("password", password);
//        ds.addDataSourceProperty("cachePrepStmts", true);
//        ds.addDataSourceProperty("prepStmtCacheSize", 250);
//        ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
//        ds.addDataSourceProperty("useServerPrepStmts", true);
//        return ds;
//    }
//
//    @Bean(name="transactionManager")
//    public PlatformTransactionManager hibernateTransactionManager() {
//        HibernateTransactionManager transactionManager
//                = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(sessionFactory().getObject());
//        return transactionManager;
//    }
}
