package com.lab.html_editor.model.htmlElement;

import com.lab.html_editor.factory.html_factory.BasicHtmlFactory;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HeadElement;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.TitleElement;


public class HtmlDocument {
    private HtmlElement root;
    private String documentname;
    private BasicHtmlFactory htmlElementFactory;

    public HtmlDocument(String documentName,String title){
        this.htmlElementFactory=new BasicHtmlFactory();
        this.documentname=documentName;
        // 初始化四个必定有的标签
        this.root=htmlElementFactory.createHtmlTop();
        HeadElement head=htmlElementFactory.createHead();
        TitleElement docu_title=htmlElementFactory.createTitle(title);
        head.addChild(docu_title);
        this.root.addChild(head);
        this.root.addChild(
            htmlElementFactory.createBody()
        );
        
    }

    public HtmlElement getRoot(){
        return this.root;
    }

    public void setDocumentName(String documentName){
        this.documentname=documentName;
    }

    public String getDocumentName(){
        return this.documentname;
    }


    // 设置根节点
    public void setRoot(HtmlElement root) {
        this.root = root;
    }

    // 生成整个文档的 HTML 表示
    public String toHtmlString(int indent) {
        return root.toStringRepresentation(indent);
    }
}
