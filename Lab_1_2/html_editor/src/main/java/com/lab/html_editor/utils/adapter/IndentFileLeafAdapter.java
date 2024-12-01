package com.lab.html_editor.utils.adapter;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.FileElement.DirectoryNode;
import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlFixedElement;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlIdAttribute;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.utils.adapter.provider.Adapter;
import com.lab.html_editor.utils.adapter.provider.IndentLeafProvider;
import com.lab.html_editor.utils.decorator.DecoratorType;
import com.lab.html_editor.utils.decorator.FileNodeUpdateStatusDecorator;
import com.lab.html_editor.utils.decorator.HtmlSpellCheckDecorator;

public class IndentFileLeafAdapter implements Adapter,IndentLeafProvider{
    private FileNode node;
    public IndentFileLeafAdapter(FileNode node){
        this.node=node;
    }

    public boolean isTextNode(){
        return false;
    }
    
    public String getPrefix(){
        FileNode file=(FileNode)node;
        StringBuilder builder=new StringBuilder();
        
        builder.append(file.getName());
        FileNodeUpdateStatusDecorator decorator=(FileNodeUpdateStatusDecorator)file.getDecorator(DecoratorType.FILE_NODE_UPDATE_STATUS_DECORATOR);
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
}
