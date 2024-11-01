package com.lab.html_editor.controller.exceptions;

public class UndoFailedException extends RuntimeException{
    public UndoFailedException(String msg){
        super(msg);
    }
}
