package com.lab.html_editor.utils.visitor.html_visitor;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;


/**
 * 计算一个composite共有多少元素
 */
public class HtmlElementCountVisitor implements HtmlVisitor{
    private int compositeCount = 0;
    private int leafCount = 0;

    @Override
    public void visit(HtmlComposite composite) {
        compositeCount++;
        for (int i=0;i<composite.getChildrenSize();i++) {
            var child=composite.getChild(i);
            ((HtmlElement)child).accept(this);  // 递归统计子元素
        }
    }

    @Override
    public void visit(HtmlLeaf leaf) {
        leafCount++;
    }

    public int getCompositeCount() {
        return compositeCount;
    }

    public int getLeafCount() {
        return leafCount;
    }

    public int getTotalCount(){
        return leafCount+compositeCount;
    }
}
