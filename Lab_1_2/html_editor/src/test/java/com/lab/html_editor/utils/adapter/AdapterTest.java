package com.lab.html_editor.utils.adapter;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.model.FileElement.FileNode;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlAnchor;
import com.lab.html_editor.utils.decorator.FileNodeUpdateStatusDecorator;
import com.lab.html_editor.utils.decorator.HtmlShowIdDecorator;
import com.lab.html_editor.utils.decorator.HtmlSpellCheckDecorator;

public class AdapterTest {
    private HtmlElement element;
    private FileNode node;
    @Before
    public void setUp(){
        element=new HtmlAnchor("a");
        node=new FileNode("a", "a");
        element.addDecorator(new HtmlShowIdDecorator(element, false));
        element.addDecorator(new HtmlSpellCheckDecorator(element));
        node.addDecorator(new FileNodeUpdateStatusDecorator(node,false));
    }

    @Test
    public void testAdapter(){
        IndentFileLeafAdapter fileLeafAdapter=new IndentFileLeafAdapter(node);
        IndentHtmlCompositeAdapter compositeAdapter=new IndentHtmlCompositeAdapter((HtmlAnchor)element);
        assertNotNull(compositeAdapter);
        assertNotNull(fileLeafAdapter);
        assertNotNull(compositeAdapter.getPrefix());
        assertNotNull(compositeAdapter.getSuffix());
        assertNotNull(compositeAdapter.getText());
        assertNotNull(compositeAdapter.getFeature());
        assertNotNull(fileLeafAdapter.getPrefix());
        assertNotNull(fileLeafAdapter.getSuffix());
        assertNotNull(fileLeafAdapter.getText());
        assertNotNull(fileLeafAdapter.getFeature());
    }
}
