package com.lab.html_editor.utils.strategy;

import com.lab.html_editor.model.FileElement.AbstractFileNode;
import com.lab.html_editor.utils.factory.adapter_Factory.IndentAdapterFactory;

public class FileIndentedRepresentation extends IndentedRepresentation implements FileRepresentationStrategy{
    
    private int indent;


    public FileIndentedRepresentation(int indent){
        this.indent=indent;
    }
    
    public String toStringRepresentation(AbstractFileNode element){
        String str="";
        str=toString(IndentAdapterFactory.createAdapter(element), indent);

        return str;
    }
}
