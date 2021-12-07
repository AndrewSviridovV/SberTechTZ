package ru.sviridov.sbertech;

import org.apache.commons.lang3.RandomStringUtils;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SberTechApplicationTests {

    @Autowired
    private CashData cashData;

    @PersistenceUnit(unitName = "db1")
    private EntityManagerFactory entityManagerFactory1;

    @PersistenceUnit(unitName = "db2")
    private EntityManagerFactory entityManagerFactory2;

    private Transaction transaction;
    private Session session;

    private EntityManager entityManager2;

    @DisplayName("Test Insert after Update")
    @Test
    void testInsertUpdate() {

        try {
            SessionFactoryImpl sessionFactory = entityManagerFactory1.unwrap(SessionFactoryImpl.class);
            Session session = sessionFactory
                    .withOptions()
                    .interceptor(new BeforeTransactionCompletionInterceptor(cashData, entityManagerFactory2)) // add interceptor to Session
                    .openSession();

            Transaction transaction1 = session.getTransaction();

            transaction1.begin();

            Product product = new Product("22", "Java 8: A Beginner's Guide", "Herbert Schildt");
            System.out.println("First Transaction -> " + product.toString() + "\n");
            session.persist(product);

            transaction1.commit();
            assertNotNull(session.find(Product.class, "22"));
            entityManager2 = entityManagerFactory2.createEntityManager();
            assertEquals(session.find(Product.class, "22").toString(), entityManager2.find(Product.class, "22").toString());

            entityManager2.clear();

            //----------------------------------------------------------------------------------------------
            Transaction transaction2 = session.getTransaction();

            transaction2.begin();

            System.out.println("update to another DB ");
            Product product2 = session.find(Product.class, "22");
            product2.setAttribute(RandomStringUtils.random(6, true, true));

            System.out.println("Second Transaction -> " + product2.toString() + "\n");

            transaction2.commit();
            assertNotNull(session.find(Product.class, "22"));
            assertEquals(session.find(Product.class, "22").toString(), entityManager2.find(Product.class, "22").toString());

            session.close();
            entityManager2.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            if (entityManager2.getTransaction().isActive()) {
                entityManager2.getTransaction().rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
            if (entityManager2 != null) {
                entityManager2.close();
            }

        }

    }

}
