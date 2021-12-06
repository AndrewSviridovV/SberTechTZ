package ru.sviridov.sbertech.service;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;


@Service
public class HibernateListener {
    @Autowired
    private final EntityManagerFactory entityManagerFactory;

    public HibernateListener(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }



    @PostConstruct
    private void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(new InsertEventListenerClass());
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(new UpdateEventListenerClass());
    }
}