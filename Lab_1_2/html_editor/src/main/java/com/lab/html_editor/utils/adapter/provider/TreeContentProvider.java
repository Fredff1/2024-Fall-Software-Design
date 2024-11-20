package com.lab.html_editor.utils.adapter.provider;

import com.lab.html_editor.model.TreeNode;

public abstract class TreeContentProvider<T> implements FeatureProvider<T>,TextProvider<T>{
    protected TreeNode node;
    
    public TreeContentProvider(TreeNode node){
        this.node=node;
    }
}
