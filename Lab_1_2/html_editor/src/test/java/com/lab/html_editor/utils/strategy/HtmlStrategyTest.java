package com.lab.html_editor.utils.strategy;



import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.HtmlService;

public class HtmlStrategyTest {
    
    private HtmlDocument document;

    @Before
    public void setUp(){
        document=new HtmlDocument("1", "1", new HtmlService());
    }

    @Test
    public void testStrategy(){
        document.setRepresentationStrategy(new HtmlIndentedRepresentation());
        var text=document.getRenderInfo().geStringRepresentation();
        assert(text!=null);
           
    
        document.setRepresentationStrategy(new HtmlTreeRepresentation());
        text=document.getRenderInfo().geStringRepresentation();
        assert(text!=null);
        document.setRepresentationStrategy(new HtmlIndentedRepresentation());
        text=document.getRenderInfo().geStringRepresentation();
        assert(text!=null);
    }
}
