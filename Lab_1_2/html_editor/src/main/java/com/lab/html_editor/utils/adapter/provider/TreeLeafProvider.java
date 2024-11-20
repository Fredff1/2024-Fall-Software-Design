package com.lab.html_editor.utils.adapter.provider;

import com.lab.html_editor.model.TreeLeaf;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;

public abstract class TreeLeafProvider extends TreeContentProvider<TreeLeaf>{
    public TreeLeafProvider(TreeNode node){
        super(node);
    }

    public abstract boolean isTextNode();
}
