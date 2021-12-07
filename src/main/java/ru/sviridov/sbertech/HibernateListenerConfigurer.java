package ru.sviridov.sbertech;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sviridov.sbertech.integrator.BeforeTransactionCompletionInterceptor;
import ru.sviridov.sbertech.listeners.InsertEventListenerClass;
import ru.sviridov.sbertech.listeners.UpdateEventListenerClass;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Component
public class HibernateListenerConfigurer {

    @PersistenceUnit(unitName="db1")
    private  EntityManagerFactory entityManagerFactory;

    @Autowired
    private  UpdateEventListenerClass updateListener;
    @Autowired
    private  InsertEventListenerClass insertListener;


    public HibernateListenerConfigurer(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @PostConstruct
    protected void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);


        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(updateListener);
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(insertListener);
    }
}
