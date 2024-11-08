package com.lab.html_editor.utils.adapter;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;

public class HtmlLeafAdapter extends HtmlElementAdapter implements TreeLeafAdapter{
    
    public HtmlLeafAdapter(HtmlLeaf leaf){
        super(leaf);
    }

    public HtmlLeafAdapter(HtmlLeaf leaf,boolean showId){
        super(leaf, showId);
    }
    
    public boolean isTextNode(){
        return node instanceof HtmlText;
    }
    
    public String getFeature(){
        HtmlLeaf leaf=(HtmlLeaf)node;
        StringBuilder builder=new StringBuilder();
        if(leaf instanceof HtmlText){
            String text = leaf.getText().trim();
            if (!text.isEmpty()) {
                builder.append(text).append("\n");
            }
        }else{
            builder.append(leaf.getTagName().getTagString());
            if(leaf.getDecorator().hasSpellCheckErrors()){
                builder.append("[X]");
            }
            if(showId){
                builder.append(" #").append(leaf.getId());
            }
        }
        return builder.toString();
    }

    public String getText(){
        HtmlLeaf leaf=(HtmlLeaf)node;
        return leaf.getText();
    }

    
}
