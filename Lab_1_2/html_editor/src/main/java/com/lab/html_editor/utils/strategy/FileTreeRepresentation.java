package com.lab.html_editor.utils.strategy;

import java.util.ArrayList;

import com.lab.html_editor.model.FileElement.AbstractFileNode;
import com.lab.html_editor.utils.factory.adapter_Factory.IndentAdapterFactory;
import com.lab.html_editor.utils.factory.adapter_Factory.TreeAdapterFactory;

public class FileTreeRepresentation extends TreeRepresentation implements FileRepresentationStrategy{
    public String toStringRepresentation(AbstractFileNode element){
        StringBuilder sb = new StringBuilder();

        buildRepresentation(TreeAdapterFactory.createAdapter(element), sb, new ArrayList<>(), true);
        return sb.toString();
    }
}
