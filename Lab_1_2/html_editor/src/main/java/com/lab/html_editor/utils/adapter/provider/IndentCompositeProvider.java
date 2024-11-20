package com.lab.html_editor.utils.adapter.provider;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeNode;

public abstract class IndentCompositeProvider extends IndentContentProvider<TreeComposite> implements ChildrenProvider<TreeComposite>{
    public IndentCompositeProvider(TreeNode node){
        super(node);
    }
}
