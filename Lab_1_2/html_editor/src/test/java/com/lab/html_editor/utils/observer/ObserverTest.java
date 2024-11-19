package com.lab.html_editor.utils.observer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lab.html_editor.controller.events.Event;
import com.lab.html_editor.controller.events.StatusEvent;

public class ObserverTest {
    class testObserver implements Observer{
        public void update(Event event){
            var statusEvent=(StatusEvent)event;
            var text=statusEvent.getMessage();
            assert(text.equals("Test"));
        }
    }

    class testObservable implements Observable{
        private List<Observer> observers=new ArrayList<>();

        public void addObserver(Observer observer){
            observers.add(observer);
        }
    
        public void removeObserver(Observer observer){
            observers.remove(observer);
        }
    
        public void notifyObservers(Event event){
            for(var observer:observers){
                observer.update(event);
            }
        }

        public void tryNotify(){
            notifyObservers(new StatusEvent("Test",true));
        }
    }

    private testObservable observable=new testObservable();
    private testObserver observer=new testObserver();

    @Test
    public void test(){
        observable.addObserver(observer);
        observable.tryNotify();
    }
}
