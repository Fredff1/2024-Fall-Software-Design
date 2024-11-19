package com.lab.html_editor.controller.events;

public class StatusEvent extends Event{
    private boolean isSuccessful=true;
    private Exception excpetion;
    private String message="";

    public StatusEvent(){
        super(EventType.STATUS_EVENT);
    }
    public StatusEvent(String message,boolean isSuccessful){
        this();
        this.message=message;
        this.isSuccessful=isSuccessful;
    }

    public StatusEvent(String message,boolean isSuccessful,Exception e){
        this(message,isSuccessful);
        this.excpetion=e;

    }

    

    public void setException(Exception e){
        this.excpetion=e;
    }

    public Exception getException(){
        return this.excpetion;
    }

    public boolean isSuccessful(){
        return this.isSuccessful;
    }

    public String getMessage(){
        return this.message;
    }
}
