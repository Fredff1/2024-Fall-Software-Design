package com.lab.html_editor.utils.decorator;

import com.lab.html_editor.model.FileElement.AbstractFileNode;


public class FileNodeUpdateStatusDecorator extends FileNodeDecorator{
    private boolean updated=false;

    public FileNodeUpdateStatusDecorator(AbstractFileNode node,boolean updated){
        super(DecoratorType.FILE_NODE_UPDATE_STATUS_DECORATOR, node);
        this.updated=updated;
    }

    public FileNodeUpdateStatusDecorator(AbstractFileNode node){
        super(DecoratorType.FILE_NODE_UPDATE_STATUS_DECORATOR, node);
    }

    public boolean getUpdateStatus(){
        return updated;
    }

    public void setUpdateStatus(boolean status){
        updated=status;
    }
}
