package ru.sviridov.sbertech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "productEntityManagerFactory1",
        transactionManagerRef = "productTransactionManager1",
        basePackages = {"ru.sviridov.sbertech.repo.db1"}
)

public class ProductConfigDB1 {

        @Autowired
        private Environment env;
        @Primary
        @Bean(name = "productDataSource1")
        @ConfigurationProperties(prefix = "app.datasource")
        public DataSource dataSource() {
            return  DataSourceBuilder.create().build();
        }

        @Primary
        @Bean(name = "productEntityManagerFactory1")
        public LocalContainerEntityManagerFactoryBean
        barEntityManagerFactory(
                EntityManagerFactoryBuilder builder,
                @Qualifier("productDataSource1") DataSource dataSource
        ) {
                final HashMap<String, Object> properties = new HashMap<String, Object>();
                properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
                properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
                properties.put("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
                properties.put("hibernate.show_sql", env.getProperty("spring.jpa.properties.hibernate.show_sql"));
                properties.put("hibernate.use_sql_comments", env.getProperty("spring.jpa.properties.hibernate.use_sql_comments"));
            return
                    builder
                            .dataSource(dataSource)
                            .properties(properties)
                            .packages("ru.sviridov.sbertech.model")
                            .persistenceUnit("db1")
                            .build();
        }
        @Primary
        @Bean(name = "productTransactionManager1")
        public PlatformTransactionManager productTransactionManager(
                @Qualifier("productEntityManagerFactory1") EntityManagerFactory
                        productEntityManagerFactory
        ) {
            return new JpaTransactionManager(productEntityManagerFactory);
        }
        @Primary
        @Bean(name = "exceptionTranslation1")
        public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
                return new PersistenceExceptionTranslationPostProcessor();
        }

}
