package com.lab.html_editor.model.htmlElement;



import java.util.ArrayList;
import java.util.List;

import com.lab.html_editor.model.TreeLeaf;
import com.lab.html_editor.service.spellcheck.SpellCheckError;
import com.lab.html_editor.utils.strategy.HtmlIndentedRepresentation;

import com.lab.html_editor.utils.visitor.html_visitor.HtmlVisitor;

public abstract class HtmlLeaf extends HtmlElement implements TreeLeaf {
    private String text;
    

    /*Leaf不包含文本节点，因而拥有单独的文本属性 */
    public HtmlLeaf(String id,String tagName){
        super(id,tagName);
        this.text="";
        setRepresentationStrategy(new HtmlIndentedRepresentation());
    }

    public HtmlLeaf(String id,String tagName,String textContent){
      super(id, tagName);
      this.text=textContent;
      setRepresentationStrategy(new HtmlIndentedRepresentation());
    }

    

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text=text;
    }

    @Override
    public void accept(HtmlVisitor visitor){
        visitor.visit(this);
    }



}
