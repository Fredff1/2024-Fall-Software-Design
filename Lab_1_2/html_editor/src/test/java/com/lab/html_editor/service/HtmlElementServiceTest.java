package com.lab.html_editor.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.model.exceptions.HtmlCreateException;
import com.lab.html_editor.model.exceptions.HtmlDeplicatedIdException;
import com.lab.html_editor.model.exceptions.HtmlServiceSearchException;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlAnchor;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlCustomComposite;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlDiv;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlListItem;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlParagraph;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlSpan;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlSubtitle;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlUnorderedList;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementCountVisitor;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlVisitor;

public class HtmlElementServiceTest {
    private HtmlService service;
    @Before
    public void setUp(){
        this.service=new HtmlService();
    }

    @Test(expected = HtmlCreateException.class)
    public void testCreate(){
        // 简单create
        HtmlDiv div_1=(HtmlDiv)service.createElement("div","div_1");
        assertNotNull(div_1);

        //大量创建
        for(int i=0;i<100;i++){
            service.createElement("div", "div_"+Integer.toString(i));
        }
        
        // 重复create
        HtmlDiv div_2=(HtmlDiv)service.createElement("div", "div_1");
        assertNull(div_2);

    }

    @Test
    public void testCreateType(){
        var anchor=service.createElement("a", "1");
        var custom=service.createElement("anchdddor", "2");
        var div=service.createElement("div", "3");
        var li=service.createElement("li", "4");
        var p=service.createElement("p", "5");
        var span=service.createElement("span", "6");
        var subtitle=service.createElement("h1", "7");
        var ul=service.createElement("ul", "8");
        assert(anchor instanceof HtmlAnchor);
        assert(custom instanceof HtmlCustomComposite);
        assert(div instanceof HtmlDiv);
        assert(li instanceof HtmlListItem);
        assert(p instanceof HtmlParagraph);
        assert(span instanceof HtmlSpan);
        assert(subtitle instanceof HtmlSubtitle);
        assert(ul instanceof HtmlUnorderedList);
    }


    @Test
    public void testSearch(){
        HtmlDiv div_1=(HtmlDiv)service.createElement("div","div_1");
        assertNotNull(div_1);
        HtmlDiv div_2=(HtmlDiv)service.getElementById("div_1");
        assertEquals(div_1, div_2);
    }

    @Test(expected = HtmlServiceSearchException.class)
    public void testInvalidSearch(){
        HtmlDiv div_1=(HtmlDiv)service.createElement("div","div_1");
        assertNotNull(div_1);
        HtmlDiv div_2=(HtmlDiv)service.getElementById("div_2");
        assertEquals(div_1, div_2);
    }

    @Test(expected = HtmlDeplicatedIdException.class)
    public void testUpdateIdToExistingId() {
        service.createElement("div", "div_1");
        service.createElement("p", "div_2");
        service.updateElementId("div_1", "div_2");  
    }


    @Test
    public void testUpdateId(){
        HtmlDiv div_1=(HtmlDiv)service.createElement("div","div_1");
        assertNotNull(div_1);
        boolean flag=service.updateElementId("div_1", "div_2");
        assertEquals(flag, true);
        assertEquals(div_1.getId(),"div_2");
    }

    @Test(expected = HtmlServiceSearchException.class)
    public void testInvalidUpdateId(){
        service.updateElementId("ds", "dsf");
    }

    @Test(expected = HtmlServiceSearchException.class)
    public void testInvalidUpdateContent(){
        service.updateElementContent("dssd", "huh");
    }

    @Test
    public void testUpdateContent(){
        HtmlDiv div_1=(HtmlDiv)service.createElement("div","div_1");
        assertNotNull(div_1);
        boolean flag=service.updateElementContent("div_1", "hello world");
        assertEquals(flag, true);
        assertEquals(div_1.getTextElement().getText(),"hello world");
    }

    @Test(expected = HtmlServiceSearchException.class)
    public void testRemove(){
        HtmlDiv div_1=(HtmlDiv)service.createElement("div","div_1");
        assertNotNull(div_1);
        service.removeElement("div_1");
        HtmlElement div_2=service.getElementById("div_1");
        assertNull(div_2);
    }

    @Test 
    public void testBatchAddRemove(){
        for(int i=0;i<100;i++){
            service.createElement("div", "div_"+Integer.toString(i));
        }
        for(int i=0;i<100;i+=5){
            service.removeElement( "div_"+Integer.toString(i));
        }
    }

    @Test
    public void testVisitELement(){
        HtmlDiv div_1=(HtmlDiv)service.createElement("div","div_1");
        HtmlVisitor visitor=new HtmlElementCountVisitor();
        service.visitElement("div_1", visitor);
        assertThrows(HtmlServiceSearchException.class, ()->{ 
            service.visitElement("dssdsds", visitor);
        });
    }

    @Test
    public void testSetAsChild(){
        HtmlDiv div_1=(HtmlDiv)service.createElement("div","div_1");
        HtmlDiv div_2=(HtmlDiv)service.createElement("div","div_2");
        service.setElementAsChild("div_2", div_1, 0);
    }
}
