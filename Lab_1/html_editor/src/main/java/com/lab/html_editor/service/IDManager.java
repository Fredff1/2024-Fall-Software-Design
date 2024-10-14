package com.lab.html_editor.service;

import java.util.HashMap;
import java.util.Map;

import com.lab.html_editor.model.htmlElement.HtmlElement;

public class IDManager {
    private Map<String, HtmlElement> elementMap;

    public IDManager(){
        this.elementMap=new HashMap<>();
    }


    public boolean registerElement(HtmlElement element) {
        String id = element.getId();
        if (elementMap.containsKey(id)) {
            return false; // ID已存在
        }
        elementMap.put(id, element);
        return true;
    }

    // 通过ID查找元素
    public HtmlElement getElementById(String id) {
        return elementMap.get(id);
    }

    public boolean updateElementId(String oldId, String newId, HtmlElement element) {
        if (!elementMap.containsKey(oldId) || elementMap.containsKey(newId)) {
            return false; // 如果旧ID不存在或新ID已存在
        }
        elementMap.remove(oldId);
        element.setId(newId);
        elementMap.put(newId, element);
        return true;
    }

    public boolean removeElement(String id) {
        if (!elementMap.containsKey(id)) {
            return false; // 元素不存在
        }
        elementMap.remove(id);
        return true;
    }

    // 检查ID是否存在
    public boolean idExists(String id) {
        return elementMap.containsKey(id);
    }
}
