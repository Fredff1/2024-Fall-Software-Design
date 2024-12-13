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
        app.simulateInput("load 30.txt");
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

    /*Test if command can be parsed and content can be reloaded after exiting */
    @Test
    public void testFileOperations(){
        app.simulateInput("load 4.txt");
        app.simulateInput("load 5.txt");
        app.simulateInput("close");
        assert(app.getController().getActiveDocument().getDocumentName().equals("4.txt"));
        app.simulateInput("load 5.txt");
        app.simulateInput("exit");
        app.setIsRunning(true);
        assert(app.getController().getActiveDocument().getDocumentName().equals("5.txt"));
        assert(app.getController().getDocumentManager().getEditors().entrySet().size()==2);
        assert(app.getController().getDocumentManager().getActiveEditor().isShowId()==true);
        app.simulateInput("edit 4.txt");
        assert(app.getController().getActiveDocument().getDocumentName().equals("4.txt"));
        app.simulateInput("load test.html");
        app.simulateInput("showid false");
        assert(app.getController().getDocumentManager().getActiveEditor().isShowId()==false);
        app.simulateInput("save");
        app.simulateInput("load test.html");
        assert(app.getController().getDocumentManager().getActiveEditor().isUpdated()==false);
        assert(app.getController().getDocumentManager().getActiveEditor().isShowId()==false);
        app.simulateInput("dir-tree");
        app.simulateInput("dir-indent");
        app.simulateInput("editor-list");

    }

    @Test
    public void testExitAndRestore(){
        app.simulateInput("load 4.txt");
        app.simulateInput("save");
        app.simulateInput("load 5.txt");
        app.simulateInput("save");
        app.simulateInput("showid false");
        app.simulateInput("save");
        assertFalse(app.getController().getDocumentManager().getActiveEditor().isShowId());
        app.simulateInput("exit");
        app.getController().restoreHistory();
        assert(app.getController().getActiveDocument().getDocumentName().equals("5.txt"));
        assert(app.getController().getDocumentManager().getEditors().entrySet().size()==2);
        assert(app.getController().getDocumentManager().getActiveEditor().isShowId()==false);
        assertFalse(app.getController().getDocumentManager().getActiveEditor().isShowId());
    }



    

    

   
}
