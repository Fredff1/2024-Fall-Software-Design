package com.lab.html_editor.utils.visitor.html_visitor;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.utils.decorator.DecoratorType;
import com.lab.html_editor.utils.decorator.HtmlShowIdDecorator;

public class HtmlElementShowIdVisitor implements HtmlVisitor{

    private boolean showId=true;
    private boolean visitChild=true;

    public HtmlElementShowIdVisitor(boolean showId){
        this.showId=showId;
    }

    public HtmlElementShowIdVisitor(boolean showId,boolean visitChild){
        this.showId=showId;
        this.visitChild=visitChild;
    }


    @Override
    public void visit(HtmlComposite composite) {
        HtmlShowIdDecorator decorator=(HtmlShowIdDecorator)composite.getDecorator(DecoratorType.HTML_SHOWID_DECORATOR);
        if(decorator!=null){
            decorator.setShowId(showId);
        }
        if(visitChild){
            for (int i=0;i<composite.getChildrenSize();i++) {
                var child=composite.getChild(i);
                ((HtmlElement)child).accept(this);  // 递归统计子元素
            }
        }
        
    }

    @Override
    public void visit(HtmlLeaf leaf) {
        HtmlShowIdDecorator decorator=(HtmlShowIdDecorator)leaf.getDecorator(DecoratorType.HTML_SHOWID_DECORATOR);
        if(decorator!=null){
            decorator.setShowId(showId);
        }
    }
}
