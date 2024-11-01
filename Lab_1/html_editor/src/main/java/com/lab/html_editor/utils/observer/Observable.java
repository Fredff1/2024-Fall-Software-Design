package com.lab.html_editor.utils.observer;

import com.lab.html_editor.controller.events.Event;

public interface Observable {
    public void addObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyObservers(Event event);
}
