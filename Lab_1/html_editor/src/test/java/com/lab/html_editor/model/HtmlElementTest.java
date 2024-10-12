package com.lab.html_editor.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.factory.html_factory.BasicHtmlFactory;
import com.lab.html_editor.model.htmlElement.HtmlAttribute;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.DivElement;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.ParagraphElement;


public class HtmlElementTest {
    private List<HtmlElement> elements;
    private BasicHtmlFactory htmlElementFactory;

    @Before
    public void setUp() {
        // 每个测试之前都会执行，初始化一个 div 元素
        elements=new ArrayList<>();
        htmlElementFactory=new BasicHtmlFactory();
        var elem_1 =htmlElementFactory.createDiv("div1");
        elements.add(elem_1);
    }

    @Test
    public void testElementCreation() {
        // 测试元素是否创建成功
        var div_element=elements.get(0);
        assertNotNull(div_element);
        assertEquals("div1", div_element.getId());
    }

    @Test
    public void testAddAttribute() {
        // 测试添加属性
        var div=(DivElement)elements.get(0);
        div.addAttribute(new HtmlAttribute("class", "container"));
        assertEquals("container", div.getAttribute("class").getValue());
    }

    @Test
    public void testUpdateAttribute() {
        // 测试更新属性
        var div=(DivElement)elements.get(0);
        div.addAttribute(new HtmlAttribute("class", "container"));
        div.addAttribute(new HtmlAttribute("class", "updated-container"));
        assertEquals("updated-container", div.getAttribute("class").getValue());
    }

    @Test
    public void testRemoveAttribute() {
        // 测试删除属性
        var div=(DivElement)elements.get(0);
        div.addAttribute(new HtmlAttribute("class", "container"));
        div.removeAttribute("class");
        assertNull(div.getAttribute("class"));
    }

    @Test
    public void testAddChild() {
        // 测试添加子元素
        var div=(DivElement)elements.get(0);
        ParagraphElement p = new ParagraphElement("p1");
        div.addChild(p);
        assertEquals(1, div.getChildren().size());
        assertEquals("p1", div.getChild(0).getId());
    }
}
