package com.lab.html_editor.utils.decorator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.controller.HtmlController;
import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlAnchor;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.view.HtmlView;

public class DecoratorTest {
    private HtmlElement element;
    private FileNode node;
    @Before
    public void setUp(){
        element=new HtmlAnchor("a");
        node=new FileNode("a", "a");
    }

    @Test
    public void testHtmlELementDecorator(){
        element.addDecorator(new HtmlShowIdDecorator(element, false));
        HtmlShowIdDecorator showIdDecorator=(HtmlShowIdDecorator)element.getDecorator(DecoratorType.HTML_SHOWID_DECORATOR);
        assertNotNull(showIdDecorator);
        assertFalse(showIdDecorator.isShowId());
        element.addDecorator(new HtmlSpellCheckDecorator(element));
        HtmlSpellCheckDecorator spellCheckDecorator=(HtmlSpellCheckDecorator)element.getDecorator(DecoratorType.HTML_SPELLCHECK_DECORATOR);
        assertNotNull(spellCheckDecorator);
        
    }

    @Test
    public void testFileNodeDecorator(){
        node.addDecorator(new FileNodeUpdateStatusDecorator(node,false));
        FileNodeUpdateStatusDecorator fileNodeUpdateStatusDecorator=(FileNodeUpdateStatusDecorator)node.getDecorator(DecoratorType.FILE_NODE_UPDATE_STATUS_DECORATOR);
        assertNotNull(fileNodeUpdateStatusDecorator);
        
    }

    @Test
    public void testUpdate(){
        HtmlController controller=new HtmlController(new HtmlView(), new SpellCheckService());
        controller.loadFile("test.txt");
        controller.editElementText("title", "114514");
        assert(controller.getDocumentManager().getActiveEditor().isUpdated()==true);
        var updatedEditor=(FileNodeUpdateStatusDecorator)controller.getDocumentManager().getActiveEditor().getFileNode().getDecorator(DecoratorType.FILE_NODE_UPDATE_STATUS_DECORATOR);
        assert(updatedEditor.getUpdateStatus()==true);
        var showIdDecorator=(HtmlShowIdDecorator)controller.getActiveDocument().search("title").getDecorator(DecoratorType.HTML_SHOWID_DECORATOR);
        assert(showIdDecorator.isShowId()==true);
        controller.showIdCommand(false);
        assert(showIdDecorator.isShowId()==false);
        controller.editElementText("title", "dsdgrniushfnu");
        var spellCheckDecorator=(HtmlSpellCheckDecorator)controller.getActiveDocument().search("title").getDecorator(DecoratorType.HTML_SPELLCHECK_DECORATOR);
        assert(spellCheckDecorator.hasSpellCheckErrors()==true);
        
    }
}
