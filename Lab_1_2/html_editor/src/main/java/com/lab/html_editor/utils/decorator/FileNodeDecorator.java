package com.lab.html_editor.utils.decorator;

import com.lab.html_editor.model.FileElement.AbstractFileNode;

public class FileNodeDecorator extends Decorator{
    protected AbstractFileNode fileNode;
    public FileNodeDecorator(DecoratorType type,AbstractFileNode node){
        super(type);
        this.fileNode=node;
    }

    
}
