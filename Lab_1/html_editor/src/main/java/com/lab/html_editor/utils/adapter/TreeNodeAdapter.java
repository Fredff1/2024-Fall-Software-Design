package com.lab.html_editor.utils.adapter;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlElement;

public abstract class TreeNodeAdapter implements TreeNode{
    protected TreeNode node;
    public TreeNodeAdapter(TreeNode node){
        this.node=node;
    }

    public TreeNode getFather(){
        return node.getFather();
    }
    
    public void setFather(TreeComposite father){
        node.setFather(father);
    }

    public TreeNode getELement(){
        return node;
    }

    public abstract String getFeature();

    
}
