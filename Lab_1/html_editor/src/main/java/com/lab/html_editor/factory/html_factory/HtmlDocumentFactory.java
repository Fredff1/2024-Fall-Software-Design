package com.lab.html_editor.factory.html_factory;

import com.lab.html_editor.model.htmlElement.HtmlDocument;

public class HtmlDocumentFactory {
    public HtmlDocument createHtmlDocument(String documentName,String title){
        return new HtmlDocument(documentName,title);
    }
}
