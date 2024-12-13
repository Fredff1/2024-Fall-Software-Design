package com.lab.html_editor.controller.events;

public class FileUpdateEvent extends Event{
    private boolean updated=true;

    public FileUpdateEvent(boolean updated){
        super(EventType.FILE_UPDATE_EVENT);
        this.updated=updated;
    }

    public boolean updated(){
        return updated;
    }
}
