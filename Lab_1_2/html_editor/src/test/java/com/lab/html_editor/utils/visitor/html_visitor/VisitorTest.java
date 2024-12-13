package com.lab.html_editor.utils.visitor.html_visitor;

import org.junit.Test;

import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlDiv;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.service.HtmlService;

public class VisitorTest {
    private HtmlDocument document=new HtmlDocument("1", "2", new HtmlService());

    @Test
    public void testCount(){
        HtmlElementCountVisitor visitor=new HtmlElementCountVisitor();
        document.visitRoot(visitor);
        int count=visitor.getTotalCount();
        int compositeCount=visitor.getCompositeCount();
        int leafCount=visitor.getLeafCount();
        assert(leafCount==1);
        assert(compositeCount==3);
        assert(count==4);
    }

    @Test
    public void testUpdate(){
        HtmlElementIdUpdateVisitor visitor=new HtmlElementIdUpdateVisitor("d", new HtmlService());
        HtmlDiv div=new HtmlDiv("dsd");
        div.accept(visitor);
        assert(div.getId().equals("d"));
        HtmlElementTextUpdateVisitor visitor2=new HtmlElementTextUpdateVisitor(new HtmlText("hello"));
        div.accept(visitor2);
        assert(div.getText().equals("hello"));
    }

    @Test
    public void testTextVisitor(){
        HtmlElementRenderVisitor visitor=new HtmlElementRenderVisitor();
        HtmlDiv div=new HtmlDiv("dsd");
        div.accept(visitor);
       
        div.setText("dsd");
                        HtmlElementTextVisitor visitor2=new HtmlElementTextVisitor();
        div.accept(visitor2);
        assert(visitor2.getTextSize()==1);
        assert(visitor2.getText(0).equals("dsd"));
    }
}
