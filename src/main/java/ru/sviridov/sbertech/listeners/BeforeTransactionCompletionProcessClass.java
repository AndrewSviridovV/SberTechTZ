package ru.sviridov.sbertech.listeners;

import org.hibernate.action.spi.BeforeTransactionCompletionProcess;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sviridov.sbertech.CashData;

public class BeforeTransactionCompletionProcessClass implements BeforeTransactionCompletionProcess {

    @Override
    public void doBeforeTransactionCompletion(SessionImplementor session) {

    }
}