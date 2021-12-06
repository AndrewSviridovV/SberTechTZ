package ru.sviridov.sbertech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "productEntityManagerFactory2",
        transactionManagerRef = "productTransactionManager2",
        basePackages = {"ru.sviridov.sbertech.repo.db2"}
)

public class ProductConfigDB2 {

        @Autowired
        private Environment env;

        @Bean(name = "productDataSource2")
        @ConfigurationProperties(prefix = "app2.datasource")
        public DataSource dataSource() {
            return  DataSourceBuilder.create().build();
        }

        @Bean(name = "productEntityManagerFactory2")
        public LocalContainerEntityManagerFactoryBean
        barEntityManagerFactory(
                EntityManagerFactoryBuilder builder,
                @Qualifier("productDataSource2") DataSource dataSource
        ) {
                final HashMap<String, Object> properties = new HashMap<String, Object>();
                properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.properties.hibernate.ddl-auto"));
                properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
                properties.put("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
                properties.put("hibernate.show_sql", env.getProperty("spring.jpa.properties.hibernate.show_sql"));
                properties.put("hibernate.use_sql_comments", env.getProperty("spring.jpa.properties.hibernate.use_sql_comments"));
            return
                    builder
                            .dataSource(dataSource)
                            .properties(properties)
                            .packages("ru.sviridov.sbertech.model")
                            .persistenceUnit("db2")
                            .build();
        }

        @Bean(name = "productTransactionManager2")
        public PlatformTransactionManager productTransactionManager(
                @Qualifier("productEntityManagerFactory2") EntityManagerFactory
                        productEntityManagerFactory
        ) {
            return new JpaTransactionManager(productEntityManagerFactory);
        }

        @Bean(name = "exceptionTranslation2")
        public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
                return new PersistenceExceptionTranslationPostProcessor();
        }

}
