package com.lab.html_editor.utils.decorator;

public abstract class Decorator {
    protected DecoratorType type;

    public Decorator(DecoratorType type){
        this.type=type;
    }

    public DecoratorType getType(){
        return type;
    }
    
} 
