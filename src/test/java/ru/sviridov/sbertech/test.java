package ru.sviridov.sbertech;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionFactoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sviridov.sbertech.integrator.BeforeTransactionCompletionInterceptor;
import ru.sviridov.sbertech.model.Product;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class test {

    @Autowired
    private CashData cashData;

    @PersistenceUnit(unitName="db1")
    private  EntityManagerFactory entityManagerFactory;

    @PersistenceUnit(unitName="db2")
    private  EntityManagerFactory entityManagerFactory2;

    private Transaction transaction;
    private Session session;

    @DisplayName("Test MessageService.get()")
    @Test
    void testGet() {

        try {
             //EntityManagerFactory emf = Persistence.createEntityManagerFactory("productEntityManagerFactory1");
            // EntityManagerFactory emf = Persistence.createEntityManagerFactory("db2");
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        Session session = sessionFactory
                .withOptions()
                .interceptor(new BeforeTransactionCompletionInterceptor(cashData,entityManagerFactory2)) // add interceptor to Session
                .openSession();

        Transaction transaction = session.getTransaction();

        transaction.begin();

        Product product=new Product("2","Java 8: A Beginner's Guide","Herbert Schildt");
        //product.setName("Java 8: A Beginner's Guide");
        //product.setAuthor("Herbert Schildt");
        session.persist(product);

        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    } finally {
        if (session != null) {
            session.close();
        }
    }

//        assertEquals("Hello JUnit 5", "");
    }

}
