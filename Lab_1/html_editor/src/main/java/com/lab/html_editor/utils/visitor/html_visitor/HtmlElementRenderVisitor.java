package com.lab.html_editor.utils.visitor.html_visitor;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;



/**
 * 用于得到渲染（在这里是控制台打印）的信息
 */
public class HtmlElementRenderVisitor implements HtmlVisitor{
    private int indent=2;

    private String stringRepresentation;
    private String strategy="indent";

    public HtmlElementRenderVisitor(){

    }

    public HtmlElementRenderVisitor(int indent){
        this.indent=indent;
    }

    public HtmlElementRenderVisitor(int indent,String strategy){
        this(indent);
        this.strategy=strategy;
    }

    public String getStategy(){
        return this.strategy;
    }

    
    

    @Override
    public void visit(HtmlComposite composite){
        //previousStrategy=composite.getRepresentationStrategy();
        //composite.convertAllRepresentationStrategies(previousStrategy);
        this.stringRepresentation=composite.toStringRepresentation(indent);
    }

    @Override
    public void visit(HtmlLeaf leaf){
        this.stringRepresentation =leaf.toStringRepresentation(indent);
    }

    public String geStringRepresentation(){
        return this.stringRepresentation;
    }

}
