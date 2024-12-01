package com.lab.html_editor.utils.factory.adapter_Factory;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.FileElement.DirectoryNode;
import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.utils.adapter.IndentDirectoryAdapter;
import com.lab.html_editor.utils.adapter.IndentFileLeafAdapter;
import com.lab.html_editor.utils.adapter.IndentHtmlCompositeAdapter;
import com.lab.html_editor.utils.adapter.IndentHtmlLeafAdapter;
import com.lab.html_editor.utils.adapter.provider.Adapter;

public class IndentAdapterFactory {
    public static Adapter createAdapter(TreeNode node){
        if(node instanceof HtmlComposite){
            return new IndentHtmlCompositeAdapter((HtmlComposite)node);
        }else if(node instanceof HtmlLeaf){
            return new IndentHtmlLeafAdapter((HtmlLeaf)node);
        }else if(node instanceof DirectoryNode){
            return new IndentDirectoryAdapter((DirectoryNode)node);
        }else if(node instanceof FileNode){
            return new IndentFileLeafAdapter((FileNode)node);
        }else{
            return null;
        }
    }
}
