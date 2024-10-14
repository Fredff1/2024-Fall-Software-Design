package com.lab.html_editor.model;

public abstract class TreeLeaf extends TreeNode{
    public TreeLeaf(String id){
        super(id);
    }
    
    public abstract String toStringRepresentation(int indentLevel);

}
