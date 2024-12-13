package com.lab.html_editor.model.exceptions;

public class HtmlCreateException extends HtmlServiceException{
    public String id;

    public HtmlCreateException(String msg){
        super(msg);
    }

    public HtmlCreateException(String msg,String id){
        super(msg);
        this.id=id;
    }
}
