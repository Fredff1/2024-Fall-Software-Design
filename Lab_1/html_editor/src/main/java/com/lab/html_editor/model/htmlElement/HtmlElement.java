package com.lab.html_editor.model.htmlElement;

import java.util.List;

import com.lab.html_editor.factory.html_factory.HtmlAttributeFactory;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlAttribute;
import com.lab.html_editor.strategy.HtmlRepresentationStrategy;

public interface HtmlElement {
    HtmlTagName getTagName();
    public List<HtmlAttribute> getAttributes();

    // 设置某个属性
    public void addAttribute(String name,String value,String... properties);

    // 获取某个属性
    public HtmlAttribute getAttribute(String name);

    // 移除某个属性
    public boolean removeAttribute(String name);

    public void setAttributeFactory(HtmlAttributeFactory factory);

    public void setRepresentationStrategy(HtmlRepresentationStrategy strategy);

    public void setId(String id);

    public String getId();

    public HtmlRepresentationStrategy getRepresentationStrategy();
}
