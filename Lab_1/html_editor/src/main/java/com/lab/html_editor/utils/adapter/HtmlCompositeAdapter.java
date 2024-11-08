package com.lab.html_editor.utils.adapter;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;

import java.util.*;

public class HtmlCompositeAdapter extends HtmlElementAdapter implements TreeCompositeAdapter{  
    
    public HtmlCompositeAdapter(HtmlComposite composite){
        super(composite);
    }

    public HtmlCompositeAdapter(HtmlComposite composite,boolean showId){
        super(composite, showId);
    }

    public String getFeature(){
        HtmlComposite composite=(HtmlComposite)node;
        StringBuilder builder=new StringBuilder();
        builder.append(composite.getTagName().getTagString());
        if(composite.getDecorator().hasSpellCheckErrors()){
            builder.append("[X]");
        }
        if(showId){
            builder.append(" #").append(composite.getId());
        }
        return builder.toString();
    }

    public List<TreeNodeAdapter> getChildren(){
        List<TreeNodeAdapter> children=new ArrayList<>();
        HtmlComposite composite=(HtmlComposite)node;
        int childrenSize = composite.getChildrenSize();
        for (int i = 0; i < childrenSize; i++) {
            TreeNode child = composite.getChild(i);
            if (child instanceof HtmlText && ((HtmlText) child).getText().trim().isEmpty()) {
                continue;
            }
            if(child instanceof HtmlComposite){
                children.add(new HtmlCompositeAdapter((HtmlComposite)child,showId));
            }else if(child instanceof HtmlLeaf){
                children.add(new HtmlLeafAdapter((HtmlLeaf)child,showId));
            }
            
        }

        return children;
    }
}
