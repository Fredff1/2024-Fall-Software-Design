package com.lab.html_editor.utils.adapter;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.FileElement.DirectoryNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlFixedElement;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlIdAttribute;
import com.lab.html_editor.utils.adapter.provider.Adapter;
import com.lab.html_editor.utils.adapter.provider.IndentCompositeProvider;
import com.lab.html_editor.utils.decorator.DecoratorType;
import com.lab.html_editor.utils.decorator.FileNodeUpdateStatusDecorator;
import com.lab.html_editor.utils.decorator.HtmlSpellCheckDecorator;

import java.util.List;

public class IndentDirectoryAdapter implements IndentCompositeProvider,Adapter{
    private DirectoryNode node;
    public IndentDirectoryAdapter(DirectoryNode node){
        this.node=node;
    }
    
    public String getPrefix(){
        DirectoryNode dir=(DirectoryNode)node;
        StringBuilder builder=new StringBuilder();
        
        builder.append(dir.getName());
        FileNodeUpdateStatusDecorator decorator=(FileNodeUpdateStatusDecorator)dir.getDecorator(DecoratorType.FILE_NODE_UPDATE_STATUS_DECORATOR);
        if(decorator.getUpdateStatus()==true){
            builder.append("*");
        }
        return builder.toString();
    }

    public String getSuffix(){
       
        return "";
    }

    public String getFeature(){
        return "";
    }

    public String getText(){
        
        return "";
    }

    public List<TreeNode> getChildren(){
        var children=((DirectoryNode)node).getChildren();
        
       return children;
    }
}
