package com.lab.html_editor.utils.adapter;

import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.utils.adapter.provider.Adapter;
import com.lab.html_editor.utils.adapter.provider.TreeLeafProvider;
import com.lab.html_editor.utils.decorator.DecoratorType;
import com.lab.html_editor.utils.decorator.FileNodeUpdateStatusDecorator;
import com.lab.html_editor.utils.decorator.HtmlShowIdDecorator;
import com.lab.html_editor.utils.decorator.HtmlSpellCheckDecorator;

public class TreeFileLeafAdapter  implements Adapter,TreeLeafProvider{
    private FileNode node;
    public TreeFileLeafAdapter(FileNode leaf){
        this.node=leaf;

    }

   
    
    public boolean isTextNode(){
        return false;
    }

   
    
    public String getFeature(){
        FileNode leaf=(FileNode)node;
        StringBuilder builder=new StringBuilder();
        
        builder.append(leaf.getName());
        FileNodeUpdateStatusDecorator decorator=(FileNodeUpdateStatusDecorator)leaf.getDecorator(DecoratorType.FILE_NODE_UPDATE_STATUS_DECORATOR);
        if(decorator.getUpdateStatus()==true){
            builder.append("*");
        }
        return builder.toString();
    }

    public String getText(){
        return "";
    }
}
