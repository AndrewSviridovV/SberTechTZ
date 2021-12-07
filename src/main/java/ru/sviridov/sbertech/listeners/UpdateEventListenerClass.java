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
        System.out.println("The PostUpdateEvent comes here with data: "+ postUpdateEvent.getEntity().toString()+"\n");
        cashData.getCASHDATA().add(new MutablePair("update", (Product)postUpdateEvent.getEntity()));
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return true;
    }
}