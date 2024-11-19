package com.lab.html_editor.utils.visitor.html_visitor;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.service.HtmlService;

/**
 * 更新id
 */
public class HtmlElementIdUpdateVisitor implements HtmlVisitor{
    private String newId;
    private final HtmlService service;

    public HtmlElementIdUpdateVisitor(String newId,HtmlService service) {
        this.newId = newId;
        this.service=service;
    }



    @Override
    public void visit(HtmlComposite composite) {
        service.getElementMap().remove(composite.getId());
        composite.setId(newId);
        service.getElementMap().put(newId,composite);
    
    }

    @Override
    public void visit(HtmlLeaf leaf) {
        service.getElementMap().remove(leaf.getId());
        leaf.setId(newId);
        service.getElementMap().put(newId,leaf);
        
        
    }
}
