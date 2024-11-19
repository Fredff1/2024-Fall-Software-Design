package com.lab.html_editor.controller.events;

public abstract class Event {
    private EventType eventType;

    public Event(EventType type){
        this.eventType=type;
    }

    public EventType getEventType(){
        return this.eventType;
    }
}
