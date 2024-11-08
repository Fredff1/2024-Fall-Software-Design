package com.lab.html_editor.utils.visitor.html_visitor;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;




/**
 * 更新文本的访问者
 */
public class HtmlElementTextUpdateVisitor implements HtmlVisitor {

    private HtmlText newContent;


    public HtmlElementTextUpdateVisitor(HtmlText newContent) {
        this.newContent = newContent;
    }

    @Override
    public void visit(HtmlComposite composite) {
        composite.setTextElement(newContent);
    }

    @Override
    public void visit(HtmlLeaf leaf) {
        leaf.setText(newContent.getText());
    }

    

    
}
