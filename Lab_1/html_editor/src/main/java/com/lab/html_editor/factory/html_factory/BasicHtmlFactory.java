package com.lab.html_editor.factory.html_factory;

import com.lab.html_editor.model.htmlElement.concreteHtmlElements.*;


public class BasicHtmlFactory implements HtmlElementFactory{
    @Override
    public AnchorElement createAnchor(String id){
        return new AnchorElement(id);
    }

    @Override
    public DivElement createDiv(String id){
        return new DivElement(id);
    }

    @Override
    public ParagraphElement createParagraph(String id){
        return new ParagraphElement(id);
    }

    @Override
    public SpanElement createSpan(String id){
        return new SpanElement(id);
    }

    @Override
    public TextElement createText(String id,String text){
        return new TextElement(id,  text);
    }

    @Override
    public HtmlTopElement createHtmlTop(){
        return new HtmlTopElement();
    }

    @Override
    public BodyElement createBody(){
        return new BodyElement();
    }

    @Override
    public HeadElement createHead(){
        return new HeadElement();
    }

    @Override
    public TitleElement createTitle(String title){
        return new TitleElement(title);
    }
    
}