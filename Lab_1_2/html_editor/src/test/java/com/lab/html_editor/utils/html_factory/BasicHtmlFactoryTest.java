package com.lab.html_editor.utils.html_factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlAnchor;
import com.lab.html_editor.utils.factory.html_factory.BasicHtmlFactory;

/**
 * 具体的测试都在service层进行测试
 */
public class BasicHtmlFactoryTest {
    private BasicHtmlFactory factory;

    @Before
    public void setUp(){
        this.factory=new BasicHtmlFactory();
    }

    @Test
    public void test_creator(){
        var div_1=factory.createDiv("div_1","");
        assertEquals(div_1.getId(),"div_1");
        var anchor_1=factory.createComponent("anchor_1", "a");
        assertNotNull(anchor_1);
        assertTrue(anchor_1 instanceof HtmlAnchor);
        assertEquals(((HtmlElement)anchor_1).getId(), "anchor_1");
    }

   
}
