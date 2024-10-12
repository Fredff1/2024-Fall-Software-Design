package com.lab.html_editor.factory.html_factory;



import com.lab.html_editor.model.htmlElement.concreteHtmlElements.*;

public interface HtmlElementFactory {
    AnchorElement createAnchor(String id);
    DivElement createDiv(String id);
    ParagraphElement createParagraph(String id);
    SpanElement createSpan(String id);
    TextElement createText(String id,String text);
    HtmlTopElement createHtmlTop();
    BodyElement createBody();
    HeadElement createHead();
    TitleElement createTitle(String title);
    
}
