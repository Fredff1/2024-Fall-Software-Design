package com.lab.html_editor.utils.adapter;

import java.util.List;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.utils.decorator.DecoratorType;
import com.lab.html_editor.utils.decorator.HtmlShowIdDecorator;
import com.lab.html_editor.utils.decorator.HtmlSpellCheckDecorator;
import com.lab.html_editor.utils.adapter.provider.*;

public class TreeHtmlLeafAdapter  implements Adapter,TreeLeafProvider{
    private HtmlLeaf node;
    public TreeHtmlLeafAdapter(HtmlLeaf leaf){
        this.node=leaf;

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
            HtmlSpellCheckDecorator spellcheckDecorator=(HtmlSpellCheckDecorator)leaf.getDecorator(DecoratorType.HTML_SPELLCHECK_DECORATOR);
            if(spellcheckDecorator.hasSpellCheckErrors()){
                builder.append("[X]");
            }
            HtmlShowIdDecorator idDecorator=(HtmlShowIdDecorator)leaf.getDecorator(DecoratorType.HTML_SHOWID_DECORATOR);
            if(idDecorator.isShowId()){
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
