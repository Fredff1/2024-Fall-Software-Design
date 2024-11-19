package com.lab.html_editor.utils.adapter;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.utils.decorator.DecoratorType;
import com.lab.html_editor.utils.decorator.HtmlSpellCheckDecorator;
import com.lab.html_editor.utils.adapter.provider.*;

import java.util.*;

public class TreeHtmlCompositeAdapter extends TreeContentProvider<HtmlComposite> implements TreeAdapter{  
    private boolean showId=true;
    public TreeHtmlCompositeAdapter(HtmlComposite composite,boolean showId){
        super(composite);
        this.showId=showId;
    }

    public boolean isShowId(){
        return showId;
    }

   

    public String getFeature(){
        HtmlComposite composite=(HtmlComposite)node;
        StringBuilder builder=new StringBuilder();
        builder.append(composite.getTagName().getTagString());
        HtmlSpellCheckDecorator decorator=(HtmlSpellCheckDecorator)composite.getDecorator(DecoratorType.HTML_SPELLCHECK_DECORATOR);
        if(decorator.hasSpellCheckErrors()){
            builder.append("[X]");
        }
        if(showId){
            builder.append(" #").append(composite.getId());
        }
        return builder.toString();
    }

    public String getText(){
        return "";
    }

    public List<TreeNode> getChildren(){
        var children=((HtmlComposite)node).getChildren();
        List<TreeNode> result=new ArrayList<>();
        for(var child:children){
            if((child instanceof HtmlText)&&(((HtmlText)child).getText().trim().isEmpty())){
                continue;
            }
            result.add(child);
        }
       return result;
    }
}
