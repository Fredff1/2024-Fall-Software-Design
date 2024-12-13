package com.lab.html_editor.model.htmlElement;

import java.util.ArrayList;

import java.util.List;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.exceptions.HtmlChildOperationFailException;

import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.service.spellcheck.SpellCheckError;
import com.lab.html_editor.utils.strategy.HtmlIndentedRepresentation;
import com.lab.html_editor.utils.strategy.HtmlRepresentationStrategy;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlVisitor;


/**
 * Html的composite
 */
public class HtmlComposite extends HtmlElement implements TreeComposite {
    protected final List<TreeNode> children = new ArrayList<>();

    public HtmlComposite(String id, String tagName) {
        super(id, tagName);
        setRepresentationStrategy(new HtmlIndentedRepresentation());
    }

    public HtmlComposite(String id, String tagName, HtmlText textElement) {
        super(id, tagName);
        this.children.add(0, textElement);
        setRepresentationStrategy(new HtmlIndentedRepresentation());

    }

    public List<TreeNode> getChildren(){
        return children;
    }

    public int getChildrenSize() {
        return this.children.size();
    }

    /**
     * 得到指定index的child 
     * */
    public TreeNode getChild(int child_index) throws IllegalArgumentException {
        if (child_index >= children.size()) {
            throw new IndexOutOfBoundsException("Child index out of range for Node " + this.getId());
        }
        return (TreeNode) children.get(child_index);
    }

    /**
     * 将child添加到最后 
     * */
    public boolean addChild(TreeNode node) throws HtmlChildOperationFailException {
        var flag = this.addChild(node, children.size());
        return flag;
    }

    /**
     * 将child添加到指定的位置 
     * */
    public boolean addChild(TreeNode node, int index) throws HtmlChildOperationFailException {
        try {
            this.children.add(index, (HtmlElement) node);
            node.setFather(this);
            return true;
        } catch (Exception e) {
            // System.out.println("Failed to add a child node for Node "+this.getId()+"
            // because of exception "+e.getMessage());
            throw new HtmlChildOperationFailException(
                    "Failed to add child", (HtmlElement) node);
        }
    }

    /**
     * 移除一个child 
     * */
    public boolean removeChild(TreeNode node) throws HtmlChildOperationFailException {
        try {
            this.children.remove(node);
            node.setFather(null);
            return true;
        } catch (Exception e) {
            // System.out.println("Filed to remove child node "+node.getId()+" for father
            // node "+this.getId());
            throw new HtmlChildOperationFailException(
                    "Failed to remove child", (HtmlElement) node);

        }
    }

    /**
     * 根据id移除一个child 
     * */
    public boolean removeChild(String child_id) throws HtmlChildOperationFailException {
        if (child_id == null) {
            throw new IllegalArgumentException("Child id to remove cannot be null");
        }
        HtmlElement child_to_remove = null;
        for (var cld : this.children) {
            HtmlElement child=(HtmlElement)cld;
            var target_id = child.getId();
            if (target_id == null) {
                continue;
            }
            if (target_id.equals(child_id)) {
                child_to_remove = child;
                break;
            }
        }
        if (child_to_remove != null) {
            this.children.remove(child_to_remove);
            return true;
        } else {
            String text = "Failed to remove child node " + child_id + " for father node " + this.getId()
                    + "because father node has no child with this id";
            throw new HtmlChildOperationFailException(
                    text, null);

        }
    }

    /**
     * 得到text节点，如果没有，返回null
     * 文本节点一定是第一个child
     * @return
     */
    public HtmlText getTextElement()  {
        if (children.isEmpty()) {
            return null;
        }

        TreeNode first_child = getChild(0);
        if (first_child instanceof HtmlText) {
            return ((HtmlText) first_child);
        }
        return null;
    }

    

    /**
     * 得到文本，如果没有文本节点，什么都不返回
     */
    @Override
    public String getText()  {
        var textElement = getTextElement();
        if(textElement==null){
            return "";
        }
        return textElement.getText();
    }

    public void setText(String text) {
        var textElement = getTextElement();
        if(textElement==null){
            setTextElement(new HtmlText(text));
            return;
        }
        textElement.setText(text);
    }

    public void setTextElement(HtmlText textElement) {
        if (children.isEmpty() == false && getChild(0) instanceof HtmlText) {
            this.children.set(0, textElement);
        } else {
            this.children.add(0, textElement);
        }
    }

    /**
     * 得到对应子对象在列表中的index方便insert操作，若不是子对象，返回-1
     * 
     * @param target
     * @return 子对象的索引 不存在则跑出异常
     */
    public int getChildIndex(TreeNode target) throws HtmlChildOperationFailException {
        for (int i = 0; i < children.size(); i++) {
            var child = getChild(i);
            if (child == target) {
                return i;
            }
        }
        throw new HtmlChildOperationFailException("The child does not exist in node " + this.getId(),
                null);
    }

     /**
      * 根据id得到child节点
      * @param id
      * @return
      * @throws HtmlChildOperationFailException
      */
    public TreeNode findChild(String id) throws HtmlChildOperationFailException {
        // 遍历子元素
        for (var cld : children) {
            HtmlElement child=(HtmlElement)cld;
            // 跳过文本节点
            if (cld instanceof HtmlText) {
                continue;
            }
            var target_id = child.getId();
            boolean is_id_same = target_id.equals(id);
            if (child instanceof HtmlComposite) {
                if (is_id_same) {
                    return (HtmlComposite) child;
                }
                HtmlComposite compositeChild = (HtmlComposite) child;
                TreeNode found = compositeChild.findChild(id);
                if (found instanceof HtmlComposite || found instanceof HtmlLeaf) {
                    return found;
                }
            } else if (child instanceof HtmlLeaf) {
                if (is_id_same) {
                    return (HtmlLeaf) child;
                }
            }
        }
        return null;
    }

    /**
     * 将节点以及其所有的child的表示方式设置为strategy
     * @param strategy
     */
    public void convertAllRepresentationStrategies(HtmlRepresentationStrategy strategy){
        
        setRepresentationStrategy(strategy);
        
        for(var cld:children){
            HtmlElement child=(HtmlElement)cld;
            if(child instanceof HtmlComposite){
                ((HtmlComposite)child).convertAllRepresentationStrategies(strategy);
            }else if(child instanceof HtmlLeaf){
                child.setRepresentationStrategy(strategy);
            }
        }
    }

   

    /**
     * 支持visitor
     */
    @Override
    public void accept(HtmlVisitor visitor) {
        visitor.visit(this);
    }

}
