package com.lab.html_editor.utils.factory.adapter_Factory;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.utils.adapter.TreeHtmlCompositeAdapter;
import com.lab.html_editor.utils.adapter.TreeHtmlLeafAdapter;
import com.lab.html_editor.utils.adapter.provider.Adapter;
import com.lab.html_editor.utils.adapter.provider.TreeContentProvider;

public class TreeAdapterFactory<T> {
    public static Adapter createAdapter(TreeNode node){
        if(node instanceof HtmlComposite){
            return new TreeHtmlCompositeAdapter((HtmlComposite)node, false);
        }else if(node instanceof HtmlLeaf){
            return new TreeHtmlLeafAdapter((HtmlLeaf)node, false);
        }else{
            return null;
        }
    }
}
