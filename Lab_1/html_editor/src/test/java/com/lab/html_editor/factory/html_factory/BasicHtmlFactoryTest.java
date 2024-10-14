package com.lab.html_editor.factory.html_factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;



import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.model.htmlElement.HtmlTagName;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlAnchor;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlDiv;

public class BasicHtmlFactoryTest {
    private BasicHtmlFactory factory;

    @Before
    public void setUp(){
        this.factory=new BasicHtmlFactory();
    }

    @Test
    public void test_creator(){
        var div_1=factory.createDiv("div_1");
        assertEquals(div_1.getId(),"div_1");
        var anchor_1=factory.createComponent("anchor_1", HtmlTagName.ANCHOR);
        assertNotNull(anchor_1);
        assertTrue(anchor_1 instanceof HtmlAnchor);
        assertEquals(anchor_1.getId(), "anchor_1");
    }

    @Test
    public void testRepeat(){
        factory.createComponent("div_1", HtmlTagName.DIV);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // 触发异常的代码
            factory.createComponent("div_1", HtmlTagName.DIV);
        });

        var e_msg=exception.getMessage();
        System.out.println(e_msg);
    }
}
