package com.lab.html_editor.factory;




import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlTagName;


public interface TreeNodeFactory {
    TreeNode createComponent(String id,HtmlTagName tagName,String... args);
    
}
