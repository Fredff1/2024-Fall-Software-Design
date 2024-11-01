package com.lab.html_editor.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.controller.exceptions.RedoFailedException;
import com.lab.html_editor.controller.exceptions.UndoFailedException;
import com.lab.html_editor.model.exceptions.HtmlAppendExcption;
import com.lab.html_editor.model.exceptions.HtmlDeleteException;
import com.lab.html_editor.model.exceptions.HtmlEditFailException;
import com.lab.html_editor.model.exceptions.HtmlInsertException;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.ConsoleCommandManager;
import com.lab.html_editor.view.HtmlView;

public class EventTest {
    private HtmlController controller;
    StatusEvent event=null;
    @Before
    public void setUp(){
        controller=new HtmlController(new HtmlView(), new ConsoleCommandManager(), new SpellCheckService());
        controller.initCommand("a", "");
    }

    @Test
    public void testNormalAppend(){
        controller.appendElement("li", "li_1", "body", " ");
        event=controller.getLastStatusEvent();
        assertTrue(event.isSuccessful());
        assertNull(event.getException());
    }

    @Test
    public void testNormalInsert(){
        controller.appendElement("li", "li_1", "body", " ");
        controller.insertElement("li", "li_2", "li_1", " ");
        event=controller.getLastStatusEvent();
        assertTrue(event.isSuccessful());
        assertNull(event.getException());
    }

    @Test
    public void testNormalDelete(){
        controller.appendElement("li", "li_1", "body", " ");
        controller.deleteElement("li_1");
        event=controller.getLastStatusEvent();
        assertTrue(event.isSuccessful());
        assertNull(event.getException());
    }

    @Test
    public void testNormalEditId(){
        controller.appendElement("li", "li_1", "body", " ");
        controller.editElementId("li_1", "li_2");
        event=controller.getLastStatusEvent();
        assertTrue(event.isSuccessful());
        assertNull(event.getException());
    }

    @Test
    public void testNormalEditText(){
        controller.appendElement("li", "li_1", "body", " ");
        controller.editElementText("li_1", "dsdsd");
        event=controller.getLastStatusEvent();
        assertTrue(event.isSuccessful());
        assertNull(event.getException());
    }

    @Test
    public void testNormalPrintIndent(){
        controller.printIndent(2);
        event=controller.getLastStatusEvent();
        assertTrue(event.isSuccessful());
        assertNull(event.getException());
    }

    @Test
    public void testNormalPrintTree(){
        controller.printTree();
        event=controller.getLastStatusEvent();
        assertTrue(event.isSuccessful());
        assertNull(event.getException());
    }

    @Test
    public void testNormalUndoRedo(){
        controller.appendElement("li", "li_1", "body", " ");
        controller.undoLastCommand();
        event=controller.getLastStatusEvent();
        assertTrue(event.isSuccessful());
        assertNull(event.getException());
        controller.redoLastCommand();
        event=controller.getLastStatusEvent();
        assertTrue(event.isSuccessful());
        assertNull(event.getException());
    }

    @Test
    public void testInvalidAppend(){
        controller.appendElement("li", "li_1", "boddy", " ");
        event=controller.getLastStatusEvent();
        assertFalse(event.isSuccessful());
        assert(event.getException() instanceof HtmlAppendExcption);
    }

    @Test
    public void testInvalidInsert(){
        controller.insertElement("li", "li_2", "li_1", " ");
        event=controller.getLastStatusEvent();
        assertFalse(event.isSuccessful());
        assert(event.getException() instanceof HtmlInsertException);
    }

    @Test
    public void testInvalidDelete(){
        controller.deleteElement("li_ddd1");
        event=controller.getLastStatusEvent();
        assertFalse(event.isSuccessful());
        assert(event.getException() instanceof HtmlDeleteException);
    }

    @Test
    public void testInvalidEditId(){
        controller.appendElement("li", "li_1", "body", " ");
        controller.editElementText("li_ss1", "dsdsd");
        event=controller.getLastStatusEvent();
        assertFalse(event.isSuccessful());
        assert(event.getException() instanceof HtmlEditFailException);
    }

    @Test
    public void testInvalidEditText(){
        controller.appendElement("li", "li_1", "body", " ");
        controller.editElementText("li_ss1", "dsdsd");
        event=controller.getLastStatusEvent();
        assertFalse(event.isSuccessful());
        assert(event.getException() instanceof HtmlEditFailException);
    }

    @Test
    public void testInvalidUndoRedo(){
        controller.appendElement("li", "li_1", "bodsy", " ");
        controller.undoLastCommand();
        event=controller.getLastStatusEvent();
        assertFalse(event.isSuccessful());
        assert(event.getException() instanceof UndoFailedException);
        controller.redoLastCommand();
        event=controller.getLastStatusEvent();
        assertFalse(event.isSuccessful());
        assert(event.getException() instanceof RedoFailedException);
    }

    
}
