package com.lab.html_editor.utils.decorator;

public interface Decorative {
    public void addDecorator(Decorator decorator);

    public void removeDEcorator(DecoratorType type);

    public Decorator getDecorator(DecoratorType type);
    
} 
