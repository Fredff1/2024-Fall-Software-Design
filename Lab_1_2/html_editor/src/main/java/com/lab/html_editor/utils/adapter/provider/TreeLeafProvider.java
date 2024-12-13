package com.lab.html_editor.utils.adapter.provider;

import com.lab.html_editor.model.TreeLeaf;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;

public interface TreeLeafProvider extends TreeContentProvider<TreeLeaf>{
   

    public abstract boolean isTextNode();
}
