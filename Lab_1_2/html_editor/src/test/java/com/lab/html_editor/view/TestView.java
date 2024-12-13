package com.lab.html_editor.view;

import org.junit.Before;
import org.junit.Test;

public class TestView {
    private HtmlView view;
    
    @Before
    public void setUp(){
        view=new HtmlView();
    }

    @Test
    public void testPrint(){
        view.displaySplitLine();
        view.displayMessage("Test");
    }
}
