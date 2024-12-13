package com.lab.html_editor.utils.adapter;

import java.util.*;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlFixedElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlIdAttribute;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.utils.adapter.provider.Adapter;
import com.lab.html_editor.utils.adapter.provider.IndentContentProvider;
import com.lab.html_editor.utils.adapter.provider.IndentLeafProvider;
import com.lab.html_editor.utils.decorator.DecoratorType;
import com.lab.html_editor.utils.decorator.HtmlSpellCheckDecorator;

public class IndentHtmlLeafAdapter implements Adapter,IndentLeafProvider{
    private HtmlLeaf node;

    public IndentHtmlLeafAdapter(HtmlLeaf node){
        this.node=node;
    }

    public boolean isTextNode(){
        return node instanceof HtmlText;
    }
    
    public String getPrefix(){
        HtmlElement element=(HtmlElement)node;
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(element.getTagName().getTagString());

        // 添加属性
        if (!element.getAttributes().isEmpty()) {
            for (var entry : element.getAttributes()) {
                if(element instanceof HtmlFixedElement && entry instanceof HtmlIdAttribute){
                    continue; // title html head body 不显示默认的id
                }
                sb.append(" ").append(entry.toString());
            }
        }
        sb.append(">");
        return sb.toString();
    }

    public String getSuffix(){
        StringBuilder sb = new StringBuilder();
        HtmlElement element=(HtmlElement)node;
        sb.append("</").append(element.getTagName().getTagString()).append(">");
        return sb.toString();
    }
    
    public String getFeature(){
        HtmlLeaf composite=(HtmlLeaf)node;
        StringBuilder builder=new StringBuilder();
        builder.append(composite.getTagName().getTagString());
        HtmlSpellCheckDecorator decorator=(HtmlSpellCheckDecorator)composite.getDecorator(DecoratorType.HTML_SPELLCHECK_DECORATOR);
        if(decorator.hasSpellCheckErrors()){
            builder.append("[X]");
        }
       
        return builder.toString();
    }

    public String getText(){
        HtmlElement element=(HtmlElement)node;
        return element.getText();
    }

    
}
