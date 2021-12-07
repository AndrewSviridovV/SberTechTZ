package ru.sviridov.sbertech.integrator;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sviridov.sbertech.CashData;
import ru.sviridov.sbertech.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@Component
public class BeforeTransactionCompletionInterceptor extends EmptyInterceptor {

    public BeforeTransactionCompletionInterceptor(CashData cashData, EntityManagerFactory entityManagerFactory2) {
        this.cashData = cashData;
        this.entityManagerFactory2 = entityManagerFactory2;
    }

    //@Autowired
    private CashData cashData;

    //@PersistenceUnit(name="db2")
    private  EntityManagerFactory entityManagerFactory2;


    @Override
    public void beforeTransactionCompletion(Transaction tx) {

       // EntityManagerFactory emf = Persistence.createEntityManagerFactory("db2");
        System.out.println(" beforeCommit "+tx.getStatus().name());
        EntityManager entityManager2=entityManagerFactory2.createEntityManager();

        try {

            //cashData.getMapCASHDATA().forEach(e ->);

            //cashData.getMapCASHDATA().
//            items.forEach(new Consumer<String>()
/*
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(firstEntity);
                entityManager.persist(secondEntity);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
            }
            */
  /*
            SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);

            Session session = sessionFactory.openSession();
*/

            cashData.getMapCASHDATA().forEach((Pair<String, Product> item) -> {
                System.out.println("persisit to another DB"+item.toString());

               entityManager2.getTransaction().begin();

               if (item.getKey().equals("insert")){
                   entityManager2.persist(item.getValue());
               }else if (item.getKey().equals("update")){
                   Product product = entityManager2.find(Product.class,item.getValue().getId());
                   product.setId(item.getValue().getId());
                   product.setName(item.getValue().getName());
                   product.setAttribute(item.getValue().getAttribute());
               }

                entityManager2.getTransaction().commit();
                //entityManager.close();

            });

        }catch (Exception e){
            entityManager2.getTransaction().rollback();
        }

/*
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // do some work
   ...
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        */

        //cashData.getMapCASHDATA().add(cashData.getCounter(),)
        // do something before commit
    }

/*

    public static void persistProduct( Product item,EntityManager em) {
        System.out.println("-- persisting --");


        em.getTransaction().begin();
        em.persist(article);
        em.getTransaction().commit();
        em.close();
        System.out.println("Article persisted: " + article);
    }

    public static void updateProduct() {
        System.out.println("-- loading and updating --");
        EntityManager em = entityManagerFactory.createEntityManager();
        Article article = em.find(Article.class, 1);
        em.getTransaction().begin();
        article.setContent("new updated content");
        em.getTransaction().commit();
        em.close();
        System.out.println("Article updated: " + article);
    }
    */

}