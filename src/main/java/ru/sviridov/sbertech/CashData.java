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

    private static Integer counter;

    @PostConstruct
    protected void init() {
        this.CASHDATA=new ArrayList<Pair<String,Product>>();
        this.counter=0;
    }

    @Bean
    public CashData getCashData(){
        return new CashData();
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

    public  Integer getCounter() {
        return counter++;
    }
}
