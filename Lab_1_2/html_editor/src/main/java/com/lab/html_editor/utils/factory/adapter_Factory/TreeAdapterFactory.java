package com.lab.html_editor.utils.factory.adapter_Factory;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.FileElement.DirectoryNode;
import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.utils.adapter.IndentDirectoryAdapter;
import com.lab.html_editor.utils.adapter.IndentFileLeafAdapter;
import com.lab.html_editor.utils.adapter.TreeDirectoryAdapter;
import com.lab.html_editor.utils.adapter.TreeFileLeafAdapter;
import com.lab.html_editor.utils.adapter.TreeHtmlCompositeAdapter;
import com.lab.html_editor.utils.adapter.TreeHtmlLeafAdapter;
import com.lab.html_editor.utils.adapter.provider.Adapter;
import com.lab.html_editor.utils.adapter.provider.TreeContentProvider;

public class TreeAdapterFactory<T> {
    public static Adapter createAdapter(TreeNode node){
        if(node instanceof HtmlComposite){
            return new TreeHtmlCompositeAdapter((HtmlComposite)node);
        }else if(node instanceof HtmlLeaf){
            return new TreeHtmlLeafAdapter((HtmlLeaf)node);
        }else if(node instanceof DirectoryNode){
            return new TreeDirectoryAdapter((DirectoryNode)node);
        }else if(node instanceof FileNode){
            return new TreeFileLeafAdapter((FileNode)node);
        }else{
            return null;
        }
    }
}
