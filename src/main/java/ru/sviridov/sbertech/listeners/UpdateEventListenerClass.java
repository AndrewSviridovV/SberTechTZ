package ru.sviridov.sbertech.listeners;

import org.apache.commons.lang3.tuple.MutablePair;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sviridov.sbertech.CashData;
import ru.sviridov.sbertech.model.Product;

import java.util.Arrays;

@Component
public class UpdateEventListenerClass implements PostUpdateEventListener {

    @Autowired
    private CashData cashData;

    @Override
    public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
        //Subscriber to the update events on your entities.
        //System.out.println(postUpdateEvent.getOldState());
        System.out.println("The PostUpdateEvent comes here with data: "+ Arrays.toString(postUpdateEvent.getState()));
        System.out.println("PostUpdateEvent");
        cashData.getCASHDATA().add(new MutablePair("update", (Product)postUpdateEvent.getEntity()));
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