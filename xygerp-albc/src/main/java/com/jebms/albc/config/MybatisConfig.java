package com.jebms.albc.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.jebms.comm.mybatis.MapWrapperFactory;

@Configuration
@EnableTransactionManagement
public class MybatisConfig implements TransactionManagementConfigurer{

    private static Log logger = LogFactory.getLog(MybatisConfig.class);

//  配置类型别名
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

//  配置mapper的扫描，找到所有的mapper.xml映射文件
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

//  加载全局的配置文件
    @Value("${mybatis.config-location}")
    private String configLocation;

    @Autowired
    private DataSource dataSource;
    // DataSource配置
//  @Bean
//  @ConfigurationProperties(prefix = "spring.datasource")
//  public DruidDataSource dataSource() {
//      return new com.alibaba.druid.pool.DruidDataSource();
//  }
    
    /*@Bean  
    public ConfigurationCustomizer configurationCustomizer() {  
            ConfigurationCustomizer configurationCustomizer = new ConfigurationCustomizer() {  
                @Override  
                public void customize(org.apache.ibatis.session.Configuration configuration) {  
                	System.out.println("MybatisConfig:customize");
                    configuration.setObjectWrapperFactory(new MapWrapperFactory());  
                }  
            };  
            return configurationCustomizer;  
        }  */

    // 提供SqlSeesion
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        try {
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource);

            // 读取配置 
            sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources(mapperLocations);
            sessionFactoryBean.setMapperLocations(resources);

            sessionFactoryBean.setConfigLocation(
                    new DefaultResourceLoader().getResource(configLocation));
            
            // 设置Map结果的转换2018.2.1
            // http://blog.csdn.net/isea533/article/details/73435439
            // http://blog.csdn.net/javahighness/article/details/53044655 
            sessionFactoryBean.setObjectWrapperFactory(new MapWrapperFactory());

            return sessionFactoryBean.getObject();
        } catch (IOException e) {
            logger.warn("mybatis resolver mapper*xml is error");
            return null;
        } catch (Exception e) {
            logger.warn("mybatis sqlSessionFactoryBean create error");
            return null;
        }
    }


//  @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }

//  @Bean
//  public PlatformTransactionManager transactionManager(){
//      return new DataSourceTransactionManager(dataSource);
//  }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
    

}