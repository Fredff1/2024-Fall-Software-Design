package com.lab.html_editor.model.htmlElement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.model.exceptions.HtmlAppendExcption;
import com.lab.html_editor.model.exceptions.HtmlDeleteException;
import com.lab.html_editor.model.exceptions.HtmlDeplicatedIdException;
import com.lab.html_editor.model.exceptions.HtmlEditFailException;
import com.lab.html_editor.model.exceptions.HtmlInsertException;
import com.lab.html_editor.model.exceptions.HtmlOperationFailException;
import com.lab.html_editor.model.exceptions.HtmlServiceSearchException;
import com.lab.html_editor.service.HtmlService;


import com.lab.html_editor.utils.strategy.HtmlTreeRepresentation;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementCountVisitor;


/**
 * 对HtmlDocument模块的test
 */
public class HtmlDocumentTest {

    private HtmlDocument document;

    @Before
    public void setUp(){

        document=new HtmlDocument("test","test",new HtmlService());
        document.append("h1","sub_title_1","body","This is the title");
    }

    

    @Test
    public void testprint(){
        var output_indented=document.toHtmlString(0);
        System.out.println(output_indented);
        document.getRoot().setRepresentationStrategy(new HtmlTreeRepresentation());
        var output_tree=document.toHtmlString(0);
        System.out.println(output_tree);
        
    }

    @Test 
    public void testAddElement(){
        
        document.append("p","descreption","body","this is a paragraph");
        document.append("ul", "list_1", "body", "This is a list");
        document.append("li", "li_it1", "list_1", "first");
        document.append("li", "li_it2", "list_1", "second");
        document.append("div", "div_1", "body", "this is a div");
        document.append("p", "p_2", "div_1", "Last updated: 2024-01-01");
        document.append("p", "p_3", "div_1", "Copyright © 2021 MyWebpage.com");
        System.out.println(document.toHtmlString(2));
        var p=document.search("descreption");
        var body=document.search("body");
        var parent_p=(HtmlElement)p.getFather();
        assert(parent_p.equals(body));
    }

    @Test
    public void testSearch(){
        document.append("div", "div_2", "body","");
        var div=document.search("div_2");
        assertNotNull(div);
    }

    @Test
    public void testInsert(){
        document.append("div", "div_2", "body","");
        document.insert("p", "p_4", "div_2", "p4 test");
        var p=document.search("p_4");
        var div=document.search("div_2");
        assertEquals(p.getId(), "p_4");
        assert(p.getFather().equals(div.getFather()));
    }

    @Test (expected=HtmlServiceSearchException.class)
    public void testDelete(){
        document.append("div", "div_2", "body","");
        document.delete("div_2");
        document.search("div_2");

    }

    @Test
    public void testEditId(){
        document.append("div", "div_2", "body","");
        document.editID("div_2", "div_3");
        var div=document.search("div_3");
        assertEquals(div.getId(), "div_3");
    }

    @Test
    public void testEditContent(){
        document.append("div", "div_2", "body","");
        document.editContent("div_2", "llm");
        var div=document.search("div_2");
        assertEquals(div.getText(), "llm");
    }

    @Test(expected =HtmlAppendExcption.class)
    public void testAppendDeplicatedExceptions(){
        document.append("div", "div_2", "body","");
        document.append("div", "div_2", "body","");
    }

    @Test(expected =HtmlAppendExcption.class)
    public void testAppendNonExistExceptions(){
        document.append("div", "div_2", "body","");
        document.append("div", "div_2", "fdfdfdfdfd","");
    }

    @Test(expected = HtmlAppendExcption.class)
    public void testAppendToLeaf(){
        document.append("p", "p_1", "body","");
        document.append("p", "p_2", "p_1","");
    }

    @Test(expected =HtmlInsertException.class)
    public void testInsertDeplicatedException(){
        document.append("div", "div_2", "body","");
        document.insert("p", "p_4", "div_2", "p4 test");
        document.insert("p", "p_4", "div_2", "p4 test");
    }

    @Test(expected =HtmlInsertException.class)
    public void testInsertNonExistException(){
        document.append("div", "div_2", "body","");
        document.insert("p", "p_4", "div_2", "p4 test");
        document.insert("p", "p_4", "sdfsdfdsfsd", "p4 test");
    }

   

    @Test(expected =HtmlServiceSearchException.class)
    public void testSearchException(){
        document.search("114514");
    }

    @Test(expected =HtmlDeleteException.class)
    public void testDeleteExceptions(){
        document.delete("uns");
    }

    @Test(expected =HtmlEditFailException.class)
    public void testEditNonExceptions(){
        document.editID("2r", "dsds");
       
    }

    @Test(expected =HtmlDeplicatedIdException.class)
    public void testEditDeplicatedExceptions(){
        document.append("div", "div_1", "body","");
        document.append("div", "div_2", "body","");
        document.editID("div_1", "div_2");
    }

    @Test(expected = HtmlAppendExcption.class)
    public void testAppendNull(){
        document.append("div", "", "body","");
    }

    @Test(expected = HtmlInsertException.class)
    public void testInsertNull(){
        document.append("div", "1", "body","");
        document.insert("div", "", "1","");
    }

    @Test
    public void testLargeNumberOfElements() {
        

        // 添加大量元素
        int elementCount = 10000;
        for (int i = 1; i <= elementCount; i++) {
            document.append("p", "p" + i, "body", "Content " + i);
        }

        HtmlElementCountVisitor countVisitor=new HtmlElementCountVisitor();
        document.visitRoot(countVisitor);

        // 检查数量是否正确
        assertEquals(elementCount+5, countVisitor.getTotalCount());
    }

    @Test
    public void testDeepNesting() {
       
        // 模拟深层嵌套结构
        String parentId = "body";
        for (int i = 1; i <= 100; i++) {
            String id = "div" + i;
            document.append("div", id, parentId, "Nested div " + i);
            parentId = id;
        }

        // 检查最深层元素
        assertEquals("Nested div 100", document.search("div100").getText());
    }

    @Test
    public void testInvalidDestination(){
        assertThrows(HtmlOperationFailException.class, ()->{
            document.append("div", "div_1", "html", "Next div");
        });

        assertThrows(HtmlOperationFailException.class, ()->{
            document.insert("div", "div_1", "body", "Next div");
        });

        assertThrows(HtmlOperationFailException.class, ()->{
            document.delete("html");
        });

        assertThrows(HtmlOperationFailException.class, ()->{
            document.editID("html","htmls");
        });


    }

   

    @Test
    public void testSpecialCharacterContent() {
        document.append("p", "p1", "body", "<div>Special Content & Test</div>");
        assertEquals("<div>Special Content & Test</div>", document.search("p1").getText());
    }



    
}
