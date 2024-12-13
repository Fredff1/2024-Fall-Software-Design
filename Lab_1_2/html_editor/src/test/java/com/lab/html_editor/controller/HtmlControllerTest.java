package com.lab.html_editor.controller;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import com.lab.html_editor.controller.exceptions.UninitializedException;
import com.lab.html_editor.model.exceptions.HtmlServiceSearchException;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.service.HtmlService;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.ConsoleCommandManager;
import com.lab.html_editor.view.HtmlView;

public class HtmlControllerTest {
    private HtmlController controller;
    private HtmlDocument document;

    private void reInit(){
        this.document = new HtmlDocument("testController", "test",new HtmlService());
        this.controller = new HtmlController(new HtmlView(),new SpellCheckService());
        this.controller.loadFile("11.txt");
        this.document=controller.getActiveDocument();
        controller.appendElement("div", "div_1", "body", "this is a div");
    }
  
    

    @Before
    public void setUp() {
        
        this.controller = new HtmlController(new HtmlView(),new SpellCheckService());
        this.controller.loadFile("11.txt");
        this.document=controller.getActiveDocument();
        controller.appendElement("h1", "sub_title_1", "body", "This is the title");
        controller.appendElement("p", "description", "body", "This is a paragraph");
        controller.appendElement("ul", "list_1", "body", "This is a list");
        controller.appendElement("li", "li_it1", "list_1", "first");
        controller.appendElement("li", "li_it2", "list_1", "second");
        controller.appendElement("div", "div_1", "body", "this is a div");
        controller.appendElement("p", "p_2", "div_1", "Last updated: 2024-01-01");
        controller.printCommandForTest();
    }

    @Test
    public void testCommandAppend() {
        controller.appendElement("p", "p_3", "div_1", "Copyright © 2021 MyWebpage.com");
        assertNotNull(document.search("p_3"));  // 检查元素是否成功添加
        controller.undoLastCommand();
        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("p_3");
        });
        controller.redoLastCommand();
        
        assertNotNull(document.search("p_3"));
        
           // 检查元素是否被撤销
    }

    @Test
    public void testCommandDelete() {
        controller.deleteElement("p_2");

        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("p_2");
        });
        
        controller.undoLastCommand();
        var ele=document.search("p_2");
        assertNotNull(ele);  // 检查撤销删除后元素是否存在

        controller.redoLastCommand();
        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("p_2");
        });
    }

    @Test
    public void testCommandInsert() {
        // 在列表中插入一个新元素
        controller.insertElement("li", "li_it3", "li_it2", "third");
        HtmlElement insertedElement = document.search("li_it3");
        assertNotNull(insertedElement);  // 检查新插入的元素是否存在
        //assertEquals("li_it2", (((HtmlComposite) insertedElement).getFather()).getChildren().get(1).getId());
        controller.undoLastCommand();
        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("li_it3");
        });
        controller.redoLastCommand();
        insertedElement = document.search("li_it3");
        assertNotNull(insertedElement);  
    }

    @Test
    public void testCommandEditText() {
        // 修改元素内容
        controller.editElementText("p_2", "Last updated: 2024-02-01");
        HtmlElement editedElement = document.search("p_2");
        assertEquals("Last updated: 2024-02-01", ((HtmlLeaf) editedElement).getText());
        controller.undoLastCommand();
        assertEquals("Last updated: 2024-01-01", ((HtmlLeaf) editedElement).getText());  // 撤销后检查内容是否恢复
        controller.redoLastCommand();
        assertEquals("Last updated: 2024-02-01", ((HtmlLeaf) editedElement).getText());
    }

    @Test
    public void testCommandEditId() {
        // 修改元素内容
        controller.editElementId("p_2", "p_6");
        HtmlElement editedElement = document.search("p_6");
        assertEquals("p_6", ((HtmlLeaf) editedElement).getId());
        controller.undoLastCommand();
        assertEquals("p_2", ((HtmlLeaf) editedElement).getId());  // 撤销后检查内容是否恢复
        controller.redoLastCommand();
        assertEquals("p_6", ((HtmlLeaf) editedElement).getId());
    }

   

    @Test
    public void testCommandAll() {
        // 添加元素
        controller.appendElement("p", "p_4", "div_1", "New paragraph for testing");
        assertNotNull(document.search("p_4"));
        
        // 删除元素
        controller.deleteElement("p_4");
        assertThrows(HtmlServiceSearchException.class,()->{document.search("p_4");});

        // 插入元素
        controller.insertElement("li", "li_it3", "li_it2", "third");
        assertNotNull(document.search("li_it3"));

        // 编辑元素
        controller.editElementText("p_2", "Last updated: 2024-02-01");
        HtmlElement editedElement = document.search("p_2");
        assertEquals("Last updated: 2024-02-01", ((HtmlLeaf) editedElement).getText());

        controller.editElementId("p_2", "p_5");
        HtmlElement editedIdElement = document.search("p_5");
        assertEquals("Last updated: 2024-02-01", ((HtmlLeaf) editedIdElement).getText());
        // 测试撤销和重做

    }

    

    @Test
    public void testCommandAppendFail() {
        reInit();
        controller.appendElement("p", "p_3", "div_1dsdsds", "Copyright © 2021 MyWebpage.com");
        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("p_3");
        });
        controller.undoLastCommand();
        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("p_3");
        });
        controller.redoLastCommand();
        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("p_3");
        });
       
 
    }

    @Test
    public void testCommandDeleteFail() {
        reInit();
        controller.deleteElement("p_2dd");

        

        assertNotNull(document.search("div_1"));
        
        
        controller.undoLastCommand();
        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("div_1");
        });

        controller.redoLastCommand();
        assertNotNull(document.search("div_1"));
    }

    @Test
    public void testCommandInsertFail() {
        reInit();
        // 在列表中插入一个新元素
        controller.insertElement("li", "li_it3", "li_itewewewe2", "third");
        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("li_it3");
        });
      
        //assertEquals("li_it2", (((HtmlComposite) insertedElement).getFather()).getChildren().get(1).getId());
        controller.undoLastCommand();
        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("li_it3");
        });
        controller.redoLastCommand();
        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("li_it3");
        });
    }

    @Test
    public void testCommandEditTextFail() {
        reInit();
        // 修改元素内容
        controller.editElementText("p_ewewewew2", "Last updated: 2024-02-01");
        HtmlElement editedElement = document.search("div_1");
        assertEquals("this is a div", ( editedElement).getText());
        controller.undoLastCommand();
        assertEquals("this is a div", ( editedElement).getText());  // 撤销后检查内容是否恢复
        controller.redoLastCommand();
        assertEquals("this is a div", (editedElement).getText());
    }

    @Test
    public void testCommandEditIdFail() {
        reInit();
        // 修改元素内容
        controller.editElementId("pewewewewewe_2", "p_eweweewewwew6");
        HtmlElement editedElement = document.search("div_1");
        assertEquals("div_1", (editedElement).getId());
        controller.undoLastCommand();
        assertEquals("div_1", ( editedElement).getId());  // 撤销后检查内容是否恢复
        controller.redoLastCommand();
        assertEquals("div_1", ( editedElement).getId());
    }

    @Test
    public void testSwitchAndLoad(){
        controller.loadFile("3.txt");
        assert(controller.getActiveDocument().getDocumentName().equals("3.txt"));
        controller.loadFile("4.txt");
        assert(controller.getActiveDocument().getDocumentName().equals("4.txt"));
        controller.switchEditor("3.txt");
        assert(controller.getActiveDocument().getDocumentName().equals("3.txt"));
        controller.closeActiveEditor();
        assert(controller.getActiveDocument().getDocumentName().equals("11.txt"));
        controller.appendElement("li", "li_30", "body", "");
        controller.printDirIndent(2);
        controller.printDirTree();
    }

    
}
