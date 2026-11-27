package com.yohan.gladiators;
import java.util.ArrayList;
import java.util.List;

public class EventBus {


    List<DamageListener> listeners = new ArrayList<>();

    public void subscribe(DamageListener listener) {
        listeners.add(listener);
    }

    public void publish(DamageEvent event) {

        for (DamageListener listener : listeners) {
            listener.onDamage(event);
        }
        
    }
} 


    

