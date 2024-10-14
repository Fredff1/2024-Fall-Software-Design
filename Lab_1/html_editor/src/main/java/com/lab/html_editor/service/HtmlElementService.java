package com.lab.html_editor.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.lab.html_editor.factory.html_factory.BasicHtmlFactory;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlTagName;

public class HtmlElementService {
    private Map<String,HtmlElement> elementMap;
    private BasicHtmlFactory htmlFactory;
    private IDManager idmanager;

    public HtmlElementService(){
        this.elementMap=new HashMap<>();
        this.idmanager=new IDManager();
        this.htmlFactory=new BasicHtmlFactory();
    }

    // 创建元素并存储
    public HtmlElement createElement(String tagName, String id, String... content) {
        if (idmanager.idExists(id)) {
            return null; // ID已存在，返回失败
        }
        // 使用工厂创建元素
        HtmlTagName htmlTag = HtmlTagName.fromString(tagName);
        HtmlElement element = (HtmlElement)htmlFactory.createComponent(id, htmlTag, content);
        
        // 将元素注册到IDManager并存储到elementMap
        if (idmanager.registerElement(element)) {
            elementMap.put(id, element);
            return element;
        }
        return null; // 注册失败
    }

    // 查找元素
    public HtmlElement getElementById(String id) {
        return elementMap.get(id); // 从elementMap中查找元素
    }

    // 更新元素ID
    public boolean updateElementId(String oldId, String newId) {
        if (!elementMap.containsKey(oldId) || idmanager.idExists(newId)) {
            return false; // 旧ID不存在或新ID已被占用
        }
        HtmlElement element = elementMap.get(oldId);

        // 更新ID
        if (idmanager.updateElementId(oldId, newId, element)) {
            elementMap.remove(oldId);
            elementMap.put(newId, element); // 更新elementMap中的元素
            return true;
        }
        return false;
    }

    // 更新元素的内容
    public boolean updateElementContent(String id, String newContent) {
        HtmlElement element = elementMap.get(id);
        if (element == null) {
            return false; // 元素不存在
        }
        if(element instanceof HtmlComposite){
            ((HtmlComposite)element).setTextElement(htmlFactory.createText(newContent));
            return true;
        }else if(element instanceof HtmlLeaf){
            ((HtmlLeaf)element).setText(newContent);
            return true;
        }else{
            return false;
        }
    }

    // 删除元素
    public boolean removeElement(String id) {
        if (!elementMap.containsKey(id)) {
            return false; // 元素不存在
        }
        HtmlElement element = elementMap.remove(id); // 从Map中移除
        return idmanager.removeElement(id); // 从IDManager中移除
    }

    // 获取所有元素
    public Collection<HtmlElement> getAllElements() {
        return elementMap.values(); // 返回所有存储的元素
    }

    

    
}
