package ru.sviridov.sbertech;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.sviridov.sbertech.model.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;


@Component
@Configuration
public class CashData {

    @PostConstruct
    protected void init() {
        this.CASHDATA=new ArrayList<Pair<String,Product>>();
    }

    public CashData() {
    }

    private static ArrayList<Pair<String,Product>> CASHDATA;

    public ArrayList<Pair<String,Product>> getCASHDATA() {
        return CASHDATA;
    }

    public  void setCASHDATA(ArrayList<Pair<String,Product>> CASHDATA) {
        CashData.CASHDATA = CASHDATA;
    }

}
