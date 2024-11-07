package com.lab.html_editor.app;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.controller.exceptions.UninitializedException;
import com.lab.html_editor.model.exceptions.HtmlServiceSearchException;
import com.lab.html_editor.model.htmlElement.HtmlDocument;

public class HtmlEditorAppTest {
    private HtmlEditorApp app;
    private HtmlController controller;
    

    @Before
    public void setUp(){
        this.app=new HtmlEditorApp();
        controller=app.getController();
    }

    @Test
    public void testInit(){
        assertNotNull(app);
        assertNotNull(app.getController());
        assertNotNull(app.getParser());
        assertNotNull(app.getView());
        assertThrows(UninitializedException.class, ()->{
            assertNull(app.getController().getActiveDocument());
        });
        
    }

    @Test
    public void testNormalCommand(){
        app.simulateInput("init");
        HtmlDocument document=controller.getActiveDocument();
        app.simulateInput("append li li_1 body Li 1");
        assert(document.search("li_1").getText().equals("Li 1"));
        app.simulateInput("insert li li_0 li_1 Li 0");
        assert(document.search("li_0").getText().equals("Li 0"));
        app.simulateInput("delete li_0");
        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("li_0");
        });
        app.simulateInput("edit-id li_1 li_2");
        assert(document.search("li_2").getText().equals("Li 1"));
        assertThrows(HtmlServiceSearchException.class, ()->{
            document.search("li_1");
        });
        app.simulateInput("edit-text li_2 Li 2");
        assert(document.search("li_2").getText().equals("Li 2"));

        app.simulateInput("undo");
        assert(document.search("li_2").getText().equals("Li 1"));
        app.simulateInput("redo");
        assert(document.search("li_2").getText().equals("Li 2"));
        
        app.simulateInput("print-indent 2");
        app.simulateInput("print-tree");
        app.simulateInput("spell-check");
        app.simulateInput("help");
    }

    @Test
    public void testIoCommand(){
        app.simulateInput("init");
        app.simulateInput("read 3.txt");
        app.simulateInput("edit-text body Body");
        String text=app.getController().getActiveDocument().toHtmlString(2);
        app.simulateInput("save 4.txt");
        app.simulateInput("read 4.txt");
        String text_2=app.getController().getActiveDocument().toHtmlString(2);
        assertEquals(text,text_2);
    }

    

    

   
}
