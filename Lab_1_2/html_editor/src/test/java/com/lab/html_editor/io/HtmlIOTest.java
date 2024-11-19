package com.lab.html_editor.io;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.HtmlService;
import com.lab.html_editor.service.io.HtmlIO;
import com.lab.html_editor.service.io.JsoupHtmlIO;

public class HtmlIOTest {
    private HtmlIO io;

    @Before
    public void setUp(){
        io=new JsoupHtmlIO();
    }

    @Test
    public void testRead(){
        HtmlDocument document=null;
        try{
            document=io.read("1.txt", new HtmlService());
        }catch (IOException e){
            return;
        }
        assert(document!=null);
        var visitor=document.getAllText();
        assert(visitor.getTextSize()>0);
    }
}
