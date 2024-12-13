package com.lab.html_editor.utils.adapter;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.FileElement.AbstractFileNode;
import com.lab.html_editor.model.FileElement.DirectoryNode;
import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.utils.adapter.provider.Adapter;
import com.lab.html_editor.utils.adapter.provider.TreeCompositeProvider;
import com.lab.html_editor.utils.decorator.DecoratorType;
import com.lab.html_editor.utils.decorator.FileNodeUpdateStatusDecorator;
import com.lab.html_editor.utils.decorator.HtmlShowIdDecorator;
import com.lab.html_editor.utils.decorator.HtmlSpellCheckDecorator;

import java.util.List;

public class TreeDirectoryAdapter implements Adapter,TreeCompositeProvider{
    private DirectoryNode node;
    public TreeDirectoryAdapter(DirectoryNode composite){
        this.node=composite;
       
    }

  

   

    public String getFeature(){
        DirectoryNode dir=(DirectoryNode)node;
        StringBuilder builder=new StringBuilder();
        
        builder.append(dir.getName());
        FileNodeUpdateStatusDecorator decorator=(FileNodeUpdateStatusDecorator)dir.getDecorator(DecoratorType.FILE_NODE_UPDATE_STATUS_DECORATOR);
        if(decorator.getUpdateStatus()==true){
            builder.append("*");
        }
        return builder.toString();
    }

    public String getText(){
        return "";
    }

    public List<TreeNode> getChildren(){
        var children=((DirectoryNode)node).getChildren();
        
       return children;
    }
}
