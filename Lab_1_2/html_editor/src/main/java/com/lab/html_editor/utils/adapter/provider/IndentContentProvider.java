package com.lab.html_editor.utils.adapter.provider;

import com.lab.html_editor.model.TreeNode;

public abstract class IndentContentProvider<T> implements FeatureProvider<T>,ChildrenProvider<T>,TextProvider<T>{
    protected TreeNode node;

    public IndentContentProvider(TreeNode node){
        this.node=node;
    }
}
