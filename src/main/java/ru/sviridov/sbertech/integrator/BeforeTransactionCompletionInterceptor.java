package ru.sviridov.sbertech.integrator;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import ru.sviridov.sbertech.CashData;
import ru.sviridov.sbertech.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;

@Component
public class BeforeTransactionCompletionInterceptor extends EmptyInterceptor {

    public BeforeTransactionCompletionInterceptor(CashData cashData, EntityManagerFactory entityManagerFactory2) {
        this.cashData = cashData;
        this.entityManagerFactory2 = entityManagerFactory2;
    }

    private CashData cashData;

    private  EntityManagerFactory entityManagerFactory2;


    @Override
    public void beforeTransactionCompletion(Transaction tx) {

        System.out.println(" beforeCommit ");
        EntityManager entityManager2 = entityManagerFactory2.createEntityManager();

        try {

            cashData.getCASHDATA().forEach((Pair<String, Product> item) -> {
                System.out.println("persist to another DB " + item.getValue().toString()+"\n");

                entityManager2.getTransaction().begin();

                if (item.getKey().equals("insert")) {
                    System.out.println("insert to another DB ");
                    entityManager2.persist(item.getValue());
                } else if (item.getKey().equals("update")) {
                    System.out.println("update to another DB ");
                    Product product = entityManager2.find(Product.class, item.getValue().getId());
                    product.setId(item.getValue().getId());
                    product.setName(item.getValue().getName());
                    product.setAttribute(item.getValue().getAttribute());
                }

                entityManager2.getTransaction().commit();
                entityManager2.clear();
                entityManager2.close();

            });

            cashData.setCASHDATA(new ArrayList<>());
        } catch (Exception e) {
            entityManager2.getTransaction().rollback();
        }
    }


}