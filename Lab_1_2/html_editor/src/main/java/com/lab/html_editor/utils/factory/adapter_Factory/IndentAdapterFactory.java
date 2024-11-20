package com.lab.html_editor.utils.factory.adapter_Factory;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.utils.adapter.IndentHtmlCompositeAdapter;
import com.lab.html_editor.utils.adapter.IndentHtmlLeafAdapter;
import com.lab.html_editor.utils.adapter.provider.Adapter;

public class IndentAdapterFactory {
    public static Adapter createAdapter(TreeNode node){
        if(node instanceof HtmlComposite){
            return new IndentHtmlCompositeAdapter(node);
        }else if(node instanceof HtmlLeaf){
            return new IndentHtmlLeafAdapter(node);
        }else{
            return null;
        }
    }
}
