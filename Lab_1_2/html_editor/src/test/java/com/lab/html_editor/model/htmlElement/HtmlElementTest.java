package com.lab.html_editor.model.htmlElement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.model.exceptions.HtmlAttributeOperationFailException;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlAnchor;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlCustomComposite;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlDiv;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlParagraph;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlSpan;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlSubtitle;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlUnorderedList;
import com.lab.html_editor.utils.factory.html_factory.BasicHtmlFactory;
import com.lab.html_editor.utils.strategy.HtmlIndentedRepresentation;
import com.lab.html_editor.utils.strategy.HtmlTreeRepresentation;

/**
 * 对HtmlElement attribute tagName模块的测试
 */
public class HtmlElementTest {
    private List<HtmlComposite> elements;
    private BasicHtmlFactory htmlElementFactory;

    @Before
    public void setUp() {
        // 每个测试之前都会执行，初始化一个 div 元素
        elements=new ArrayList<>();
        htmlElementFactory=new BasicHtmlFactory();
        var elem_1 =htmlElementFactory.createDiv("div_1"," ");
        elements.add(elem_1);
    }

    @Test
    public void testElementCreation() {
        // 测试元素是否创建成功
        var div_element=elements.get(0);
        assertNotNull(div_element);
        assertEquals("div_1", div_element.getId());
    }

    @Test
    public void testAddAttribute() {
        // 测试添加属性
        var div=(HtmlDiv)elements.get(0);
        div.addAttribute("class", "container");
        assertEquals("container", div.getAttribute("class").getValue());
    }

    @Test(expected=HtmlAttributeOperationFailException.class)
    public void testUpdateAttribute() {
        // 测试更新属性
        var div=(HtmlDiv)elements.get(0);
        div.addAttribute("class","container");
        div.addAttribute("class", "updated-container");
        assertEquals("updated-container", div.getAttribute("class").getValue());
    }

    @Test
    public void testRemoveAttribute() {
        // 测试删除属性
        var div=(HtmlDiv)elements.get(0);
        div.addAttribute("class", "container");
        div.removeAttribute("class");
        assertNull(div.getAttribute("class"));
    }

    @Test
    public void testTextElement(){
        var div_1=new HtmlDiv("div_1");
        var div_2=new HtmlDiv("div_2", new HtmlText("div 2"));
        assert(div_1.getTextElement()==null);
        assert(div_2.getTextElement().getText().equals("div 2"));
    }

    @Test
    public void testAddChild() {
        // 测试添加子元素
        var div=(HtmlDiv)elements.get(0);
        HtmlParagraph p = new HtmlParagraph("p1");
        div.addChild(p);
        assertEquals(2, div.getChildrenSize());
        assertEquals("p1", ((HtmlElement)div.getChild(1)).getId());
    }

    @Test
    public void testRepresentationStrategyChange(){
        var div_1=new HtmlDiv("div_1");
        var div_2=new HtmlDiv("div_2", new HtmlText("div 2"));
        div_2.addChild(div_1);
        div_2.convertAllRepresentationStrategies(new HtmlTreeRepresentation());
        assert(div_1.getRepresentationStrategy() instanceof HtmlTreeRepresentation);
        assert(div_2.getRepresentationStrategy() instanceof HtmlTreeRepresentation);
        div_2.convertAllRepresentationStrategies(new HtmlIndentedRepresentation());
        assert(div_1.getRepresentationStrategy() instanceof HtmlIndentedRepresentation);
        assert(div_2.getRepresentationStrategy() instanceof HtmlIndentedRepresentation);

    }

    @Test
    public void testinitText(){
        var div=(HtmlDiv)elements.get(0);
        div.setText("Div 1");
        assertEquals(div.getText(), "Div 1");
    }

    @Test
    public void testEditId(){
        var div=(HtmlDiv)elements.get(0);
        div.setId("div2");
        assertEquals(div.getId(), "div2");
    }

    @Test
    public void testCreateBatch(){
        var anchor=htmlElementFactory.createAnchor("a","");
        var custom=(HtmlCustomComposite)htmlElementFactory.createCustomComposite("custom","custom" ,"This is a custom");
        var div=htmlElementFactory.createDiv("div_1","");
        var span=htmlElementFactory.createSpan("dsd","");
        var sub=htmlElementFactory.createSubtitle("sub_1", "h1", "title1");
        var ul=htmlElementFactory.createUnorderedList("list_3","");
        assert(anchor instanceof HtmlAnchor);
        assert(custom instanceof HtmlCustomComposite);
        assert(div instanceof HtmlDiv);
        assert(span instanceof HtmlSpan);
        assert(sub instanceof HtmlSubtitle);
        assert(ul instanceof HtmlUnorderedList);
        assert(anchor.getTagName().getTagNameEnum()==HtmlTagNameEnum.ANCHOR);
        assert(custom.getTagName().getTagNameEnum()==HtmlTagNameEnum.CUSTOMCOMPOSITE);
        assert(div.getTagName().getTagNameEnum()==HtmlTagNameEnum.DIV);
        assert(span.getTagName().getTagNameEnum()==HtmlTagNameEnum.SPAN);
        assert(sub.getTagName().getTagNameEnum()==HtmlTagNameEnum.H1);
        assert(ul.getTagName().getTagNameEnum()==HtmlTagNameEnum.UNORDEREDLIST);
    }
}
