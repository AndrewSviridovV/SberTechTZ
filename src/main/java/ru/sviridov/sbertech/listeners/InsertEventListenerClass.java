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
        System.out.println("The PostInsertEvent comes here with data: "+postInsertEvent.getEntity().toString()+"\n");
        cashData.getCASHDATA().add(new MutablePair<>("insert", (Product)postInsertEvent.getEntity()));
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return true;
    }
}