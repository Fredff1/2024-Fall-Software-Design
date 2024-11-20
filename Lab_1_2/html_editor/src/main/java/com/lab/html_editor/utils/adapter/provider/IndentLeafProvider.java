package com.lab.html_editor.utils.adapter.provider;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeNode;

public abstract class IndentLeafProvider extends IndentContentProvider<TreeComposite>{
    public IndentLeafProvider(TreeNode node){
        super(node);
    }

    public abstract boolean isTextNode();
}
