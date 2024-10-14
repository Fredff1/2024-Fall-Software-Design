package com.lab.html_editor.model.htmlElement;

import com.lab.html_editor.factory.html_factory.BasicHtmlFactory;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlBody;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlHead;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlTitle;
import com.lab.html_editor.service.HtmlElementService;


public class HtmlDocument {
    private HtmlComposite root;
    private String documentname;
    private HtmlElementService htmlElementService;

    public HtmlDocument(String documentName,String title){
        this.htmlElementService=new HtmlElementService();
        this.documentname=documentName;
        // 初始化四个必定有的标签
        this.root=(HtmlComposite)htmlElementService.createElement("html","html");
        
        HtmlHead head=(HtmlHead)htmlElementService.createElement("head", "head");
        HtmlTitle docu_title=(HtmlTitle)htmlElementService.createElement("title", "title",title);
        HtmlBody body=(HtmlBody)htmlElementService.createElement("body", "vody");
        head.addChild(docu_title);
        this.root.addChild(head);
        this.root.addChild(body);

        
    }

    public HtmlComposite getRoot(){
        return this.root;
    }

    public void setDocumentName(String documentName){
        this.documentname=documentName;
    }

    public String getDocumentName(){
        return this.documentname;
    }

    public boolean append(String sourcetagNameString,String sourceId,String parentId,String sourceTextContent){
        TreeNode sourceHtmlElement=null;
        try{
            sourceHtmlElement=(TreeNode)htmlElementService.createElement(sourcetagNameString,sourceId,sourceTextContent);
        }catch(IllegalArgumentException e){
            return false;
        }
        var parentNode=root.findChild(parentId);
        if(parentNode==null||!(parentNode instanceof HtmlComposite)){
            return false;
        }
        var parentCpmposite=(HtmlComposite)parentNode;
        parentCpmposite.addChild(sourceHtmlElement);
        return true;
    }   

    public boolean insert(String sourcetagNameString,String sourceId,String brotherId,String sourceTextContent){
        TreeNode sourceHtmlElement=null;
        try{
            sourceHtmlElement=(TreeNode)htmlElementService.createElement(sourcetagNameString,sourceId,sourceTextContent);
        }catch(IllegalArgumentException e){
            return false;
        }
        var brotherNode=root.findChild(brotherId);
        if(brotherNode==null||!(brotherNode instanceof HtmlComposite)){
            return false;
        }
        var parentNode=brotherNode.getFather();
        int index_of_brother=((HtmlComposite)parentNode).getChildIndex(brotherNode);
        try{
            ((HtmlComposite)parentNode).addChild(sourceHtmlElement,index_of_brother);
            return true;
        }catch(Exception e){
            return false;
        }
    }


    // 设置根节点
    public void setRoot(HtmlComposite root) {
        this.root = root;
    }

    // 生成整个文档的 HTML 表示
    public String toHtmlString(int indent) {
        return root.toStringRepresentation(indent);
    }
}
