package com.lab.html_editor.utils.adapter.provider;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeNode;

public abstract class TreeCompositeProvider extends TreeContentProvider<TreeComposite> implements ChildrenProvider<TreeComposite>{
    public TreeCompositeProvider(TreeNode node){
        super(node);
    }
}
