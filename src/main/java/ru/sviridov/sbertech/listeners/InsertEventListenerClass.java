package ru.sviridov.sbertech.listeners;

import org.apache.commons.lang3.tuple.MutablePair;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sviridov.sbertech.CashData;
import ru.sviridov.sbertech.model.Product;

import java.util.Arrays;

@Component
public class InsertEventListenerClass implements PostInsertEventListener {

    @Autowired
    private CashData cashData;

    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
        //Subscriber to the insert events on your entities.
        System.out.println("The PostInsertEvent comes here with data: "+ Arrays.toString(postInsertEvent.getState()));
        System.out.println("onPostInsert");
        cashData.getCASHDATA().add(new MutablePair<>("insert", (Product)postInsertEvent.getEntity()));
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        // Does this listener require that after transaction hooks be registered?
        // Typically this is true for post-insert event listeners,
        // but may not be, for example,
        // in JPA cases where there are no callbacks defined for the particular entity.
        return true;
    }
}