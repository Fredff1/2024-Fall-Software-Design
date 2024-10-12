package com.lab.html_editor.model;

import java.util.HashMap;
import java.util.Map;

import com.lab.html_editor.strategy.HtmlRepresentationStrategy;

//import java.util.List;

public abstract class HtmlElement extends TreeNode{
    private String tagName;
    private Map<String,String> attributes;
    private HtmlRepresentationStrategy representationStrategy;


    

    public HtmlElement(String id,String name){
        super(name, id);
        this.attributes=new HashMap<>();
    }

    // 获取所有属性
    public Map<String, String> getAttributes() {
        return attributes;
    }

    // 设置某个属性
    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    // 获取某个属性
    public String getAllAttribute(String key) {
        return attributes.get(key);
    }

    // 移除某个属性
    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public void setRepresentationStrategy(HtmlRepresentationStrategy strategy){
        this.representationStrategy=strategy;
    }

    public HtmlRepresentationStrategy getRepresentationStrategy(){
        return this.representationStrategy;
    }

    public String getTagName(){
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

     /**
     * 生成元素的 HTML 字符串表示，递归子节点，使用策略模式切换不同的表示策略
     */
    @Override
    public String toStringRepresentation() {
       return this.representationStrategy.toStringRepresentation(this, 4);
    }


}
